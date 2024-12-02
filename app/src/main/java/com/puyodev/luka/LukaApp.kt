package com.puyodev.luka

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.puyodev.luka.common.snackbar.SnackbarManager
import com.puyodev.luka.screens.PaymentGateway.PaymentGatewayScreen
import com.puyodev.luka.screens.pay.PayScreen
import com.puyodev.luka.screens.profile.ProfileScreen
import com.puyodev.luka.screens.login.LoginScreen
import com.puyodev.luka.screens.operation.OperationsScreen
import com.puyodev.luka.screens.sign_up.SignUpScreen
import com.puyodev.luka.screens.splash.SplashScreen
import com.puyodev.luka.screens.ticket.TicketScreen
import com.puyodev.luka.ui.theme.LukaTheme
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.MutableState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LukaApp(locationText: MutableState<String>) {
    LukaTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackbarState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                        }
                    )
                },
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    lukaGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    snackbarState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarState, navController, snackbarManager, resources, coroutineScope) {
    LukaAppState(snackbarState, navController, snackbarManager, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.lukaGraph(appState: LukaAppState) {
    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(
        route = "$TICKET_SCREEN/{valor}/{direccion}",
        arguments = listOf(
            navArgument("valor") { type = NavType.IntType },
            navArgument("direccion") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val valor = backStackEntry.arguments?.getInt("valor")
        val direccion = backStackEntry.arguments?.getString("direccion")

        TicketScreen(
            valor = valor,
            direccion = direccion,
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGNUP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(PAY_SCREEN) {
        PayScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(PAYMENT_SCREEN) {
        PaymentGatewayScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(PROFILE_SCREEN) {
        ProfileScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(OPERATIONS_SCREEN) {
        OperationsScreen(openScreen = { route -> appState.navigate(route) })
    }
}