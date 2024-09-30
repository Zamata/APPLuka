package com.puyodev.luka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image//para Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.puyodev.luka.ui.theme.LukaTheme

//llamando a vistas
import com.puyodev.luka.ui.AnimationLoadScreen
import com.puyodev.luka.ui.AppContent
import com.puyodev.luka.ui.PaymentScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LukaTheme {
                PaymentScreen(num_bus = 123, address = "Av. Villa Ugarriza", luka = 5, fecha = "02/05/2024 - 21:04")

                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //AnimationLoadScreen()
                    //AppContent()
                    /*Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }*/
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Column {
        val image = painterResource(id = R.drawable.logo_luka)
        Image(
            painter = image,
            contentDescription = "Load Background Image"
        )
        val logo = painterResource(id = R.drawable.ic_launcher_background)
        Image(
            painter = logo,
            contentDescription = "Logo Image"
        )
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LukaTheme {
        AnimationLoadScreen()
        //AppContent()
    }
}


