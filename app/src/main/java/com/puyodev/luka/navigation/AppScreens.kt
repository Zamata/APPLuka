package com.puyodev.luka.navigation

sealed class AppScreens(val route:String) {
    object MainScreen:AppScreens("main_screen")
    object PaymentScreen:AppScreens("payment_screen")
}