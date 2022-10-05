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

import android.content.Context
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.helio.app.core.command.manager.CommandManagerImpl

/**
 * Кнопка голосового ввода.
 *
 * @author hepller
 */
@Composable
private fun VoiceButton(
  enabled: Boolean,
  enabledColor: Color = MaterialTheme.colors.onPrimary,
  disabledColor: Color = MaterialTheme.colors.onSecondary,
  onClick: () -> Unit
) {
  val iconColor = remember { Animatable(disabledColor) }

  if (enabled) {
    LaunchedEffect(Unit) {
      iconColor.animateTo(
        targetValue = enabledColor,
        animationSpec = tween(200)
      )
    }
  } else {
    LaunchedEffect(Unit) {
      iconColor.animateTo(
        targetValue = disabledColor,
        animationSpec = tween(200)
      )
    }
  }

  IconButton(
    onClick = { onClick() },
    enabled = enabled
  ) {
    Icon(
      imageVector = Icons.Rounded.Mic,
      contentDescription = "Голосовой ввод команды",
      tint = iconColor.value,
      modifier = Modifier.size(size = 30.dp)
    )
  }
}

/**
 * Кнопка-иконка отправки.
 *
 * @param enabled Доступна ли кнопка для нажатия.
 * @param enabledColor Цвет доступной для нажатия кнопки.
 * @param disabledColor Цвет недоступной для нажатия кнопки.
 * @param onClick Функция, которая будет выполнена при нажатии.
 *
 * @author hepller
 */
@Composable
private fun SendButton(
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
      imageVector = Icons.Rounded.Send,
      contentDescription = "Отправить команду",
      tint = iconColor.value,
      modifier = Modifier.size(size = 30.dp)
    )
  }
}

/**
 * Нижняя панель.
 *
 * @author hepller
 */
@Composable
fun BottomBar() {
  var input: String by rememberSaveable { mutableStateOf(value = "") }

  val context: Context = LocalContext.current

  BottomAppBar(
    backgroundColor = MaterialTheme.colors.primary,
  ) {
    VoiceButton(enabled = false) {
      /* TODO */
    }

    TextField(
      value = input,
      onValueChange = { newText ->
        input = newText.trimStart { it == '\n' }
      },
      placeholder = {
        Text(text = "Введите команду")
      },
      colors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colors.onPrimary,
        backgroundColor = Color.Transparent,
        placeholderColor = MaterialTheme.colors.onSecondary,
        cursorColor = MaterialTheme.colors.onPrimary,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
      ),
      modifier = Modifier
        .fillMaxHeight()
        .width(width = 260.dp)
    )

    Spacer(Modifier.weight(weight = 1f, fill = false))

    SendButton(enabled = input.trim().isNotEmpty()) {
      CommandManagerImpl.handleInput(input = input.trim(), context = context)

      input = ""
    }
  }
}