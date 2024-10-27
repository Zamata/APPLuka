package com.puyodev.luka.screens.splash

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.puyodev.luka.model.service.AccountService
import com.puyodev.luka.model.service.ConfigurationService
import com.puyodev.luka.model.service.LogService
import com.puyodev.luka.screens.LukaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    configurationService: ConfigurationService,
    private val accountService: AccountService,
    logService: LogService
) : LukaViewModel(logService) {
    val showError = mutableStateOf(false)

    init {
        launchCatching { configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountService.hasUser) openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                showError.value = true
                throw ex
            }
            openAndPopUp(TASKS_SCREEN, SPLASH_SCREEN)
        }
    }
}
