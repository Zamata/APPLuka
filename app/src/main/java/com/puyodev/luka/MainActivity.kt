package com.puyodev.luka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.puyodev.luka.ui.theme.LukaTheme
import dagger.hilt.android.AndroidEntryPoint
import android.app.AlertDialog
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.puyodev.luka.screens.PaymentGateway.PayPalConfig
import com.puyodev.luka.screens.pay.PayViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var payPalConfig: PayPalConfig

    private val paymentLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            payPalConfig.initialize()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            payPalConfig.cleanup()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(paymentLifecycleObserver)
        enableEdgeToEdge()

        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val locationText = remember { mutableStateOf("Ubicación no disponible") }

            DisposableEffect(lifecycleOwner) {
                onDispose {
                    payPalConfig.cleanup()
                }
            }

            LukaApp(locationText = locationText)
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(paymentLifecycleObserver)
        super.onDestroy()
    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        LukaTheme {
            Surface {
                content()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp {
            Text("Hello NFC!")
        }
    }
}