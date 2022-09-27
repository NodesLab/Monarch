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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.helio.app.core.command.manager.CommandManagerImpl
import net.helio.app.ui.message.MessageManagerImpl

/**
 * Кнопка-иконка отправки.
 *
 * @param enabled Доступна ли кнопка для нажатия.
 * @param enabledColor Цвет доступной для нажатия кнопки.
 * @param disabledColor Цвет недоступной для нажатия кнопки.
 * @param onClick Функция, которая будет выполнена при нажатии.
 */
@Composable
private fun SendButton(
  enabled: Boolean,
  enabledColor: Color = MaterialTheme.colors.onPrimary,
  disabledColor: Color = MaterialTheme.colors.onSecondary,
  onClick: () -> Unit
) {
  val iconColor = remember { Animatable(disabledColor) }

  if (enabled) {
    LaunchedEffect(Unit) {
      iconColor.animateTo(enabledColor, animationSpec = tween(200))
    }
  } else {
    LaunchedEffect(Unit) {
      iconColor.animateTo(disabledColor, animationSpec = tween(200))
    }
  }

  IconButton(
    onClick = { onClick() },
    enabled = enabled
  ) {
    Icon(
      imageVector = Icons.Rounded.Send,
      contentDescription = null,
      tint = iconColor.value,
      modifier = Modifier.size(30.dp)
    )
  }
}

/**
 * Нижняя панель.
 */
@Composable
fun BottomBar() {
  var input: String by rememberSaveable { mutableStateOf("") }

  Surface(
    color = MaterialTheme.colors.primary,
    elevation = 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(60.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxSize()
        .padding(
          start = 0.dp,
          end = 6.dp,
          top = 0.dp,
          bottom = 0.dp
        )
    ) {
      SelectionContainer {
        TextField(
          value = input,
          onValueChange = { newText ->
            input = newText.trimStart { it == ' ' }
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
          shape = RoundedCornerShape(5.dp),
          modifier = Modifier
            .fillMaxHeight()
            .width(270.dp)
            .padding(start = 10.dp)
        )
      }

      Spacer(Modifier.weight(1f))

      SendButton(enabled = input.trim().isNotEmpty()) {
        MessageManagerImpl.userMessage(input.trim())

        CommandManagerImpl.handleInput(input.trim())

        input = ""
      }
    }
  }
}