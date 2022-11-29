/*
 * Copyright © 2022 Node.
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

package net.monarch.app.ui.main.scaffold.bottom.buttons

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
fun SendButton(
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
