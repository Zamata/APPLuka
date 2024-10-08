package com.puyodev.luka.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

// variables que define Operacion
data class Operacion(val operacion: String, val fecha: String, val monto: String)

val historial = listOf(
    Operacion("Recarga: Yape", "01/10/2024", "S/ 50.00"),
    Operacion("Pago de Pasaje", "05/10/2024", "S/ 1.00"),
    Operacion("Pago de Pasaje", "07/10/2024", "S/ 1.00"),
    Operacion("Pago de Pasaje", "07/10/2024", "S/ 5.00"),
    Operacion("Pago de Pasaje", "07/10/2024", "S/ 3.00"),
    Operacion("Pago de Pasaje", "07/10/2024", "S/ 2.00"),
    Operacion("Pago de Pasaje", "07/10/2024", "S/ 2.00"),
    Operacion("Recarga: Yape", "10/10/2024", "S/ 20.00")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialView(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed) // Estaddsdso para abrir/cerrar el drawer
    val scope = rememberCoroutineScope() // Alcance de la corrutina pasdsdsra manejar el drawer

    Scaffold(
        topBar = {
            CustomTopBar(navController = navController, name = "Juan", onMenuClick = {
                scope.launch { drawerState.open() } // Abre el drawer al hacer click en el menú
            })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(historial) { operacion ->
                OperacionItem(operacion)
                Spacer(modifier = Modifier.height(10.dp)) // Espacio entre cada item
            }
        }
    }
}

@Composable
fun OperacionItem(operacion: Operacion) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Operación: ${operacion.operacion}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Fecha: ${operacion.fecha}", style = MaterialTheme.typography.bodyMedium)
            }
            Column(
                verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Monto: ${operacion.monto}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistorialViewPreview() {
    //HistorialView()
}
