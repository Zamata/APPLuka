package com.puyodev.luka.screens.PaymentGateway

import android.app.Application
import android.os.Environment
import android.util.Log
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayPalConfig @Inject constructor(
    private val application: Application
) {
    // Store our callback handlers
    private var approvalCallback: OnApprove? = null
    private var cancelCallback: OnCancel? = null
    private var errorCallback: OnError? = null


    companion object {
        const val CLIENT_ID = "ATsSK7UfAy4TVOhGf_5lEHkKwaUrT8k8fze7uZcdliN0qwJZGYbYDXAZCrIPANcx7nQCx6MpwWEg4e0a"
        const val RETURN_URL = "com.puyodev.luka://paypalpay"
        const val SECRET_KEY = "EEcuT_r7MLajWJzNYS72q-mSI3Yn_JzSfIXsuFEkuFXddjT8BQvbgVSrnNTuvIQcHmvjEZsuJJlIErAX"

    }

    fun initialize() {
        val config = CheckoutConfig(
            application = application,
            clientId = CLIENT_ID,
            returnUrl = RETURN_URL,
            environment = com.paypal.checkout.config.Environment.SANDBOX,
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
                    settingsConfig = SettingsConfig(
                    loggingEnabled = true,  // Habilitar logs para debugging
                    )
        )

        PayPalCheckout.setConfig(config)
    }
    // Method to register callbacks
    fun registerCallbacks(
        onApprove: OnApprove,
        onCancel: OnCancel,
        onError: OnError
    ) {
        try {
            // Store our callbacks
            this.approvalCallback = onApprove
            this.cancelCallback = onCancel
            this.errorCallback = onError

            // Register with PayPal SDK
            PayPalCheckout.registerCallbacks(
                onApprove = onApprove,
                onCancel = onCancel,
                onError = onError
            )

            Log.d("PayPalConfig", "PayPal callbacks registered successfully")
        } catch (e: Exception) {
            Log.e("PayPalConfig", "Error registering PayPal callbacks: ${e.message}")
            throw e
        }
    }

    fun unregisterCallbacks() {
        try {
            // Clear our stored callbacks
            approvalCallback = null
            cancelCallback = null
            errorCallback = null

            // Unregister from PayPal SDK
            PayPalCheckout.registerCallbacks(
                onApprove = OnApprove { },  // Empty callbacks
                onCancel = OnCancel { },
                onError = OnError { }
            )

            Log.d("PayPalConfig", "PayPal callbacks unregistered successfully")
        } catch (e: Exception) {
            Log.e("PayPalConfig", "Error unregistering PayPal callbacks: ${e.message}")
        }
    }

    fun cleanup() {
        try {
            // Unregister all PayPal callbacks to prevent memory leaks
            unregisterCallbacks()

            // You might want to log the cleanup for debugging
            Log.d("PayPalConfig", "PayPal cleanup completed successfully")
        } catch (e: Exception) {
            // Log any errors that occur during cleanup
            Log.e("PayPalConfig", "Error during PayPal cleanup: ${e.message}")
        }
    }



}