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

package com.puyodev.luka.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicToolbar(@StringRes title: Int) {
  TopAppBar(title = { Text(stringResource(title)) }, /*backgroundColor = toolbarColor()*/)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionToolbar(
  @StringRes title: Int,
  //@DrawableRes endActionIcon: Int,
  modifier: Modifier,
  endAction: () -> Unit,
  onMenuClick: () -> Unit
) {
  TopAppBar(
    modifier = Modifier
      .shadow(elevation = 5.dp)
      .background(Color.Gray),
    navigationIcon = {
      IconButton(onClick = onMenuClick) {
        Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
      }
    },
    title = { Text(stringResource(title)) },
    actions = {
      IconButton(onClick = { /* TODO: Search action */ }) {
        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Search")
      }
      IconButton(onClick = endAction) {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Go to User Profile")
      }
    },
  )
}


@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): Color {
  return if (darkTheme) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.inversePrimary
}
