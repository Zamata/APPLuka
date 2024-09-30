package com.puyodev.luka.ui

import androidx.compose.foundation.Image//para Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.puyodev.luka.R

@Composable
fun AnimationLoadScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo que ocupa toda la pantalla
        Image(
            painter = painterResource(id = R.drawable.ciudad02),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Opción para ajustar la imagen al tamaño de la pantalla
            modifier = Modifier.fillMaxSize()
        )

        // Imagen centrada encima del fondo
        Image(
            painter = painterResource(id = R.drawable.logo_luka),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp) // Tamaño personalizado para la imagen centrada
        )
    }
}