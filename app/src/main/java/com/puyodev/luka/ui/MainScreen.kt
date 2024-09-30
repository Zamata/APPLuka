package com.puyodev.luka.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.puyodev.luka.R

@Composable
fun AppContent() {
    val navController = rememberNavController()
    var clickCount by remember { mutableStateOf(0) } // Estado del contador

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Scaffold(
                topBar = { CustomTopBar(navController = navController,name = "Juan") },
                bottomBar = { CustomBottomBar2(navController = navController) }, // Agregar la barra inferior aquí
            ) { innerPadding ->
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ){
               Row (
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(15.dp)
                       .align(Alignment.CenterHorizontally),
                   horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los elementos de forma uniforme

               ){
                   OutlinedButton(
                       modifier = Modifier.width(160.dp),
                       onClick = { /*TODO*/ }  // Modificación: Se pasa la función para cancelar.
                   ) {
                       Text("Monto\nS/35.00",)
                   }
                   OutlinedButton(
                       modifier = Modifier.width(160.dp),
                       onClick = { /*TODO*/ }  // Modificación: Se pasa la función para cancelar.
                   ) {
                       Text("Tarifa\nS/1.00"/*stringResource(R.string.app_name)*/)
                   }
               }
                Box(
                    modifier = Modifier
                        //.fillMaxSize()
                        .fillMaxWidth()
                        .padding(0.dp,20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically // Centra verticalmente el contenido del Row
                    ){
                        IconButton(onClick = { navController.navigate("testScreen") }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Minus")
                        }
                        Text(text = "02", fontSize = 100.sp)
                        Image(
                            painter = painterResource(id = R.drawable.persons),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(0.dp)
                                .size(360.dp) // Tamaño personalizado para la imagen centrada
                        )
                        IconButton(onClick = { navController.navigate("testScreen") }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Plus")
                        }
                    }
                }

                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center

                    ) {
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(0.dp,40.dp),
                            onClick = {},
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.secondary,
                            icon = {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Realizar pago" // Add a valid content description
                                )
                            },
                            text = { Text(text = "Pagar", fontSize = 20.sp) }
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(navController: NavController,name:String) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation = 5.dp)
            .background(Color.Gray),
        navigationIcon = {
            IconButton(onClick = { /* TODO: Side navigation action */ }) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
            }
        },
        title = { Text(text = "Hola $name") },
        actions = {
            IconButton(onClick = { /* TODO: Search action */ }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Search")
            }
            IconButton(onClick = { navController.navigate("userProfile") }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Go to User Profile")
            }
        },
    )
}

@Composable
fun CustomBottomBar(navController: NavController) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho de la pantalla
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los elementos de forma uniforme
        ) {
            IconButton(onClick = { navController.navigate("testScreen") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home description")
            }
            IconButton(onClick = { navController.navigate("testScreen") }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite description")
            }
            IconButton(onClick = { navController.navigate("testScreen") }) {
                Icon(Icons.Filled.Person, contentDescription = "User description")
            }
            IconButton(onClick = { navController.navigate("testScreen") }) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notification description")
            }
        }
    }
}

@Composable
fun CustomBottomBar2(navController: NavController) {
    BottomAppBar(
        modifier = Modifier
            .shadow(elevation = 5.dp)
            .height(150.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically, // Centra verticalmente el contenido del Row
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los elementos de forma uniforme
        ) {
            Text(text = "Ubicación Actual: Av.\n Mariscal Castilla #203", fontSize = 20.sp)
            Image(
                painter = painterResource(id = R.drawable.located),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp) // Tamaño personalizado para la imagen centrada
            )

        }
    }
}

