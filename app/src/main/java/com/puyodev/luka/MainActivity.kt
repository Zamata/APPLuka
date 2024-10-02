package com.puyodev.luka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image//para Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.puyodev.luka.navigation.AppNavigation
import com.puyodev.luka.ui.theme.LukaTheme

//llamando a vistas
import com.puyodev.luka.screens.AnimationLoadScreen
import com.puyodev.luka.screens.AppContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LukaTheme {
                //PaymentScreen(num_bus = 123, address = "Av. Villa Ugarriza", luka = 5, fecha = "02/05/2024 - 21:04")
                AppNavigation()
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //AnimationLoadScreen()
                    /*Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }*/
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LukaTheme {
        AnimationLoadScreen()
        //AppContent()
    }
}


