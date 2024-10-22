package com.puyodev.luka.screens

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.puyodev.luka.R

@Composable
fun ProfileScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
                .fillMaxWidth(),
horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.neko),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp)
                    .clip(CircleShape),
            )
            Text(text = "PuyoDEV")
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),

                ){
                Button(
                    shape = RectangleShape, // Hace que el botón sea rectangular
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        //isFormValid = name.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()
                    },
                ) {
                    Text("Monto")
                }
                Button(
                    shape = RectangleShape, // Hace que el botón sea rectangular
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        //isFormValid = name.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()
                    },
                ) {
                    Text("Subir Imagen")
                }
            }
        }
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = "name",
                onValueChange = { "name" },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = "jeefry753@gmail.com",
                onValueChange = { "name" },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = "902887796",
                onValueChange = { "name" },
                label = { Text("Número de celular") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = "- - - - - - - -",
                onValueChange = { "name" },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Botón para enviar el formulario
            Button(
                onClick = {
                    //isFormValid = name.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Modificar")
            }
        }
    }

}

@Preview
@Composable
fun PreviewProfile(){
    //ProfileScreen()
}