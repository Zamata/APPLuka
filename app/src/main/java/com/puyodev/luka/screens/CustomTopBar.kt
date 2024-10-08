package com.puyodev.luka.screens

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.puyodev.luka.navigation.AppScreens
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(navController: NavController, name: String, onMenuClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation = 5.dp)
            .background(Color.Gray),
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
            }
        },
        title = { Text(text = "Hola $name") },
        actions = {
            IconButton(onClick = { /* TODO: Search action */ }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Search")
            }
            IconButton(onClick = { navController.navigate(AppScreens.ProfileScreen.route) }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Go to User Profile")
            }
        },
    )
}
