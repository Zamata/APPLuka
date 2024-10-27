package com.puyodev.luka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.puyodev.luka.navigation.AppNavigation
import com.puyodev.luka.ui.theme.LukaTheme

//para el NFC
import android.nfc.NfcAdapter
import androidx.compose.material3.Surface

//para firebase
import com.google.firebase.firestore.FirebaseFirestore


//llamando a vistas

class MainActivity : ComponentActivity() {
    private lateinit var firestore: FirebaseFirestore

    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        enableEdgeToEdge()
        setContent {
            LukaTheme {
                AppNavigation()
            }
            /*
            MyApp {
                nfcAdapter?.let {
                    NFCActivationScreen(activity = this, nfcAdapter = it)
                } ?: run {
                    Text(text = "NFC is not available on this device.")
                }
            }
             */
        }
    }
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