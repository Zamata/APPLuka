/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.puyodev.luka.screens.sign_up

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.puyodev.luka.PAY_SCREEN
import com.puyodev.luka.SIGNUP_SCREEN
import com.puyodev.luka.R.string as AppText
import com.puyodev.luka.common.ext.isValidEmail
import com.puyodev.luka.common.ext.isValidPassword
import com.puyodev.luka.common.ext.passwordMatches
import com.puyodev.luka.common.snackbar.SnackbarManager
import com.puyodev.luka.model.service.AccountService
import com.puyodev.luka.model.service.LogService
import com.puyodev.luka.navigation.AppScreens
import com.puyodev.luka.screens.LukaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService,
  logService: LogService
) : LukaViewModel(logService) {
  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }

  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }
    Log.d("SignUpViewModel", "All validations passed, attempting to link account")

    launchCatching {
      try {
        accountService.createAccount(email, password)
        Log.d("SignUpViewModel", "Account successfully created")
        openAndPopUp(PAY_SCREEN, SIGNUP_SCREEN)
      } catch (e: Exception) {
        Log.e("SignUpViewModel", "Account creation failed", e)
        SnackbarManager.showMessage(AppText.sign_in)//cambiar o crear valor sign_error
      }
    }
  }
}