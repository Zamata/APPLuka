package com.puyodev.luka.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.puyodev.luka.screens.sign_up.SignUpViewModel
import com.puyodev.luka.R.string as AppText
import com.puyodev.luka.common.composable.*
import com.puyodev.luka.common.ext.basicButton
import com.puyodev.luka.common.ext.fieldModifier
import com.puyodev.luka.ui.theme.LukaTheme
import android.util.Log
import com.puyodev.luka.common.ext.textButton

@Composable
fun SignUpScreen(
  openAndPopUp: (String, String) -> Unit,
  viewModel: SignUpViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  SignUpScreenContent(
    uiState = uiState,
    onUsernameChange = viewModel::onUsernameChange,
    onEmailChange = viewModel::onEmailChange,
    onPasswordChange = viewModel::onPasswordChange,
    onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
    onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
    onLoginAccountClick = { viewModel.onLoginAccountClick(openAndPopUp) },
  )
}

@Composable
fun SignUpScreenContent(
  modifier: Modifier = Modifier,
  uiState: SignUpUiState,
  onUsernameChange: (String) -> Unit,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onRepeatPasswordChange: (String) -> Unit,
  onSignUpClick: () -> Unit,
  onLoginAccountClick: () -> Unit
) {

  BasicToolbar(AppText.create_account)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    UsernameField(uiState.username, onUsernameChange, Modifier.fieldModifier("new_username_field"))
    EmailField(uiState.email, onEmailChange, Modifier.fieldModifier("new_email_field"))
    PasswordField(uiState.password, onPasswordChange, Modifier.fieldModifier("new_password_field"))
    RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, Modifier.fieldModifier("new_repassword_field"))

    BasicButton(AppText.create_account, Modifier.basicButton()) {
      onSignUpClick()
    }
    BasicTextButton(AppText.start_to_login_account, Modifier.textButton()) {
      onLoginAccountClick()
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
  val uiState = SignUpUiState(
    email = "email@test.com"
  )

  LukaTheme {
    SignUpScreenContent(
      uiState = uiState,
      onEmailChange = { },
      onPasswordChange = { },
      onRepeatPasswordChange = { },
      onSignUpClick = { },
      onUsernameChange = { },
      onLoginAccountClick = { }
    )
  }
}
