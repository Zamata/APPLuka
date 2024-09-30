package com.puyodev.luka.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.puyodev.luka.R

@Composable
fun PaymentScreen(num_bus: Int,address: String,luka:Int,fecha:String){
    Column(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    // use Brush.verticalGradeint to change the angle
                    colors = listOf(
                        Color(0xFF64B5F6), // Start color
                        Color(0xFF0D47A1)  // End color
                    )
                )
            )
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,80.dp,0.dp,0.dp),
            horizontalArrangement = Arrangement.Center // Centra los elementos dentro de Row
        ){
            Image(
                painter = painterResource(id = R.drawable.logo_luka),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp), // Tamaño personalizado para la imagen centrada
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center // Centra el Box en la pantalla
        ){
            Box (
                modifier= Modifier
                    .background(Color.White)
                    .padding(25.dp)
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center // Centra el Box en la pantalla
                ){
                    Text(text = fecha)
                }
                Row (
                    modifier = Modifier.padding(0.dp,40.dp).fillMaxWidth(),
                ){
                    Column (
                        modifier=Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.bus),
                            contentDescription = null,
                            modifier = Modifier
                                .size(160.dp) // Tamaño personalizado para la imagen centrada
                        )
                        Text(text = "Bus $num_bus")
                    }
                    Column (
                        modifier=Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Row (
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center // Centra el contenido del Row horizontalmente

                        ){
                            Text(text = "Pago:\n S/$luka")
                        }
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                        Row (
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center // Centra el contenido del Row horizontalmente

                        ){
                            Text(text = "Paradero:\n $luka")
                        }
                    }
                }

            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,80.dp), // Ocupa todo el ancho de la pantalla
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los elementos de forma uniforme
        ) {
            SmallFloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    Icons.Outlined.Share,
                    modifier = Modifier.size(60.dp).padding(10.dp),
                    contentDescription = "" // Add a valid content description
                )
            }
            SmallFloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    Icons.Filled.Menu,
                    modifier = Modifier.size(60.dp).padding(10.dp),
                    contentDescription = "" // Add a valid content description
                )
            }
            SmallFloatingActionButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    modifier = Modifier.size(60.dp).padding(10.dp),
                    contentDescription = "" // Add a valid content description
                )
            }
        }
    }
}