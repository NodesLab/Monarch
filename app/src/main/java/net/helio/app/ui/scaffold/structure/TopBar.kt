/*
 * Copyright © 2022 The Helio contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.helio.app.ui.scaffold.structure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.helio.app.BuildConfig
import net.helio.app.R

/**
 * Иконка навигации (кнопка).
 *
 * @param onClick Функция, выполняемая при нажатии на кнопку.
 *
 * @author hepller
 */
@Composable
private fun NavigationIcon(onClick: () -> Unit) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.Rounded.Menu,
      contentDescription = null,
      tint = MaterialTheme.colors.onPrimary
    )
  }
}

/**
 * Заголовок приложения.
 *
 * @author hepller
 */
@Composable
private fun Title() {
  Image(
    painter = painterResource(R.mipmap.ic_launcher_round),
    contentDescription = null,
    modifier = Modifier
      .size(35.dp)
      .clip(CircleShape)
  )

  Column(
    modifier = Modifier.padding(start = 10.dp)
  ) {
    val appName = "Helio"
    val version: List<String> = BuildConfig.VERSION_NAME.split("-")
    val motdMessage = "v${version[0]} (${version[1]})"

    Text(text = appName, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)
    Text(text = motdMessage, color = MaterialTheme.colors.onSecondary, fontSize = 14.sp)
  }
}

/**
 * Верхняя панель.
 *
 * @param onNavigationIconClick Функция, выполняемая при нажатии на иконку навигации.
 *
 * @author hepller
 */
@Composable
fun TopBar(onNavigationIconClick: () -> Unit) {
  TopAppBar(
    navigationIcon = { NavigationIcon(onNavigationIconClick) },
    title = { Title() },
    backgroundColor = MaterialTheme.colors.primary,
    elevation = 4.dp
  )
}