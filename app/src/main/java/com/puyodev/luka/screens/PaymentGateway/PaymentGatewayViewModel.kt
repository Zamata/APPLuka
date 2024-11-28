package com.puyodev.luka.screens.PaymentGateway

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.Approval
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.error.ErrorInfo
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.CaptureOrderResult
import com.paypal.checkout.order.OnCaptureComplete
import com.paypal.checkout.order.OrderRequest
import com.paypal.checkout.order.PurchaseUnit
import com.puyodev.luka.PROFILE_SCREEN
import com.puyodev.luka.model.PaymentOperation
import com.puyodev.luka.model.User
import com.puyodev.luka.model.service.ConfigurationService
import com.puyodev.luka.model.service.LogService
import com.puyodev.luka.model.service.StorageService
import com.puyodev.luka.screens.LukaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class PaymentGatewayViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService,
    private val payPalConfig: PayPalConfig
) : LukaViewModel(logService) {

    // Nuevo: Job para rastrear operaciones en curso
    private var currentPaymentJob: Job? = null

    private var _currentUserId: String? = null


    // Observamos el usuario actual usando el Flow proporcionado por StorageService
    val user = storageService.currentUserData
        .onEach { user ->
            _currentUserId = user.id
            logService.logMessage("Usuario actual ID: ${user.id}")
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            User()
        )


//    // Estado del usuario actual
//    val user = storageService.currentUserData.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        User()
//    )
    // Estados de pago
private val _paymentStatus = MutableStateFlow<PaymentGatewayState.PaymentStatus>(
    PaymentGatewayState.PaymentStatus.Idle
)
    val paymentStatus = _paymentStatus.asStateFlow()

    // Monto seleccionado para la recarga
    private val _selectedAmount = MutableStateFlow(0)
    val selectedAmount = _selectedAmount.asStateFlow()

    init {
//        payPalConfig.initialize()
        setupPayPalCallbacks()
        viewModelScope.launch {
            logService.logMessage("Inicializando ViewModel - Usuario ID: ${storageService.currentUserId}")
        }
        initializePaymentSystem()
    }

    private fun initializePaymentSystem() {
        viewModelScope.launch {
            try {
                payPalConfig.initialize()
                logService.logMessage("Sistema de pago inicializado correctamente")
            } catch (e: Exception) {
                logService.logError("Error al inicializar sistema de pago", e)
            }
        }
    }


    private fun setupPayPalCallbacks() {
        PayPalCheckout.registerCallbacks(
            onApprove = OnApprove { approval ->
                handlePayPalApproval(approval)
            },
            onCancel = OnCancel {
                handlePayPalCancellation()
            },
            onError = OnError { errorInfo ->
                handlePayPalError(errorInfo)
            }
        )
    }

    private fun handlePayPalApproval(approval: Approval) {
        currentPaymentJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                // Update to use PaymentGatewayState
                _paymentStatus.value = PaymentGatewayState.PaymentStatus.Processing
                logService.logMessage("Iniciando procesamiento de pago aprobado")

                val orderId = approval.data.orderId ?: throw Exception("Order ID no encontrado")

                if (isPaymentAlreadyProcessed(orderId)) {
                    logService.logMessage("Pago ya procesado anteriormente: $orderId")
                    return@launch
                }

                approval.orderActions.capture { captureResult ->
                    viewModelScope.launch {
                        when (captureResult) {
                            is CaptureOrderResult.Success -> {
                                _paymentStatus.value = PaymentGatewayState.PaymentStatus.Verifying
                                processCaptureSuccess(orderId)
                            }
                            is CaptureOrderResult.Error -> {
                                handleCaptureError(captureResult)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                handlePaymentError(e)
            }
        }
    }
    fun processPayment(amount: Int) {
        viewModelScope.launch {
            try {
                // Validate the amount first
                if (amount <= 0) {
                    _paymentStatus.value = PaymentGatewayState.PaymentStatus.Error("El monto debe ser mayor a 0")
                    return@launch
                }

                // Store the selected amount and update status to Loading
                _selectedAmount.value = amount
                _paymentStatus.value = PaymentGatewayState.PaymentStatus.Loading

                // Log the payment attempt
                logService.logMessage("Iniciando proceso de pago por $amount lukitas")

                // Start the PayPal checkout process
                PayPalCheckout.startCheckout(
                    CreateOrder { createOrderActions ->
                        // Create the PayPal order request
                        val order = OrderRequest(
                            intent = OrderIntent.CAPTURE,
                            purchaseUnitList = listOf(
                                PurchaseUnit(
                                    amount = Amount(
                                        currencyCode = CurrencyCode.USD,
                                        value = amount.toString()
                                    )
                                )
                            )
                        )
                        Log.d("PayPal", "Creating order with PayPal")
                        // Create the order with PayPal
                        createOrderActions.create(order)



                    }
                )
            } catch (e: Exception) {
                // Handle any errors that occur during payment initialization
                logService.logError("Error al iniciar el pago", e)
                _paymentStatus.value = PaymentGatewayState.PaymentStatus.Error(
                    "Error al iniciar el pago: ${e.message}"
                )
            }
        }
    }

    private suspend fun processCaptureSuccess(orderId: String) {
        try {
            logService.logMessage("Procesando captura exitosa: $orderId")
            val lukitasAmount = _selectedAmount.value

            if (lukitasAmount <= 0) {
                throw Exception("Monto inválido")
            }

            val paymentOperation = PaymentOperation(
                userId = storageService.currentUserId,
                amount = lukitasAmount.toDouble(),
                lukitasAmount = lukitasAmount,
                paymentMethod = "PayPal",
                transactionId = orderId,
                status = "processing",
                timestamp = Timestamp.now()
            )

            withContext(Dispatchers.IO) {
                val operationId = storageService.savePaymentOperation(paymentOperation)
                logService.logMessage("Operación guardada exitosamente: $operationId")
                _paymentStatus.value = PaymentGatewayState.PaymentStatus.Success(orderId)
            }
        } catch (e: Exception) {
            handlePaymentError(e)
        }
    }


    private suspend fun isPaymentAlreadyProcessed(orderId: String): Boolean {
        return try {
            // Verificar en Firestore si el pago ya existe
            val existingPayments = storageService.getPaymentOperations(storageService.currentUserId)
            existingPayments.any { it.transactionId == orderId }
        } catch (e: Exception) {
            logService.logError("Error al verificar pago existente", e)
            false
        }
    }

    private fun handlePayPalCancellation() {
        viewModelScope.launch {
            logService.logMessage("Pago cancelado por el usuario")
            _paymentStatus.value = PaymentGatewayState.PaymentStatus.Cancelled
            resetPaymentStatus()
        }
    }

    private fun handlePayPalError(errorInfo: ErrorInfo) {
        viewModelScope.launch {
            val errorMessage = errorInfo.reason ?: "Error desconocido en el proceso de pago"
            logService.logError("Error PayPal", Exception(errorMessage))
            _paymentStatus.value = PaymentGatewayState.PaymentStatus.Error(errorMessage)
        }
    }

    private fun handleCaptureError(captureResult: CaptureOrderResult.Error) {
        viewModelScope.launch {
            val errorMessage = captureResult.message ?: "Error en la captura del pago"
            logService.logError("Error en captura", Exception(errorMessage))
            _paymentStatus.value = PaymentGatewayState.PaymentStatus.Error(errorMessage)
        }
    }

    private fun handlePaymentError(error: Exception) {
        viewModelScope.launch {
            logService.logError("Error en el proceso de pago", error)
            _paymentStatus.value = PaymentGatewayState.PaymentStatus.Error(
                error.message ?: "Error desconocido")
        }
    }
    fun resetPaymentStatus() {
        viewModelScope.launch {
            _paymentStatus.value = PaymentGatewayState.PaymentStatus.Idle
            _selectedAmount.value = 0
        }
    }
    override fun onCleared() {
        super.onCleared()
        payPalConfig.cleanup()
    }

    fun cancelPayment() {
        viewModelScope.launch {
            // Cancel any ongoing operations
            _paymentStatus.value = PaymentGatewayState.PaymentStatus.Idle
            // Add any cleanup logic needed
        }

    }

    companion object {
       const val LUKITA_TO_USD_RATE = 1.0 // 1 Lukita = 1 USD
    }

    fun onProfileClick(openScreen: (String) -> Unit) = openScreen(PROFILE_SCREEN)

}