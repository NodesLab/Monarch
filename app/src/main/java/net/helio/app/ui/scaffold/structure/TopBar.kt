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

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.helio.app.BuildConfig
import net.helio.app.R
import net.helio.app.core.message.manager.MessageManagerImpl

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
      contentDescription = "Меню",
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
    contentDescription = "Логотип",
    modifier = Modifier
      .size(35.dp)
      .clip(CircleShape)
  )

  Column(
    modifier = Modifier.padding(start = 10.dp)
  ) {
    val appName: String = remember { "Helio" }

    val version: List<String> = remember { BuildConfig.VERSION_NAME.split("-") }
    val versionField: String = remember { "v${version[0]} ${version[1]} ${version[2]}" }

    Text(text = appName, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)
    Text(text = versionField, color = MaterialTheme.colors.onSecondary, fontSize = 14.sp)
  }
}

/**
 * Кнопка очистки истории сообщений.
 *
 * @param enabled Доступна ли кнопка для нажатия.
 * @param enabledColor Цвет доступной для нажатия кнопки.
 * @param disabledColor Цвет недоступной для нажатия кнопки.
 *
 * @author hepller
 */
@Composable
private fun ClearHistoryIcon(
  enabled: Boolean,
  enabledColor: Color = MaterialTheme.colors.onPrimary,
  disabledColor: Color = MaterialTheme.colors.onSecondary,
  onClick: () -> Unit
) {
  val iconColor = remember { Animatable(initialValue = disabledColor) }

  if (enabled) {
    LaunchedEffect(key1 = Unit) {
      iconColor.animateTo(
        targetValue = enabledColor,
        animationSpec = tween(durationMillis = 300)
      )
    }
  } else {
    LaunchedEffect(Unit) {
      iconColor.animateTo(
        targetValue = disabledColor,
        animationSpec = tween(durationMillis = 300)
      )
    }
  }

  IconButton(
    onClick = { onClick() },
    enabled = enabled
  ) {
    Icon(
      imageVector = Icons.Rounded.Clear,
      contentDescription = "Очистить историю сообщений",
      tint = iconColor.value
    )
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
  val clearHistoryButtonEnable: Boolean = MessageManagerImpl.messageList.isNotEmpty()

  TopAppBar(
    navigationIcon = { NavigationIcon(onNavigationIconClick) },
    title = { Title() },
    actions = { ClearHistoryIcon(clearHistoryButtonEnable) { MessageManagerImpl.messageList.clear() } },
    backgroundColor = MaterialTheme.colors.primary
  )
}