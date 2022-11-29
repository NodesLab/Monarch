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

package net.monarch.app.ui.main.scaffold.content.message.payload.types

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.monarch.app.core.command.manager.CommandManagerImpl
import net.monarch.app.core.message.model.payload.buttons.CommandButtonPayload
import net.monarch.app.ui.utility.ColorUtility

/**
 * Кнопка-ссылка.
 *
 * @param payload Объект полезной нагруки.
 * @param modifier Модификатор (для корректного отображения).
 *
 * @see CommandButtonPayload
 *
 * @author hepller
 */
@Composable
fun CommandButton(payload: CommandButtonPayload, modifier: Modifier) {
  val context: Context = LocalContext.current

  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.padding(top = 5.dp)
  ) {
    val buttonColor: Color = ColorUtility.resolveButtonColor(buttonColor = payload.buttonColor, materialColor = MaterialTheme.colors)
    val buttonContentColor: Color = ColorUtility.resolveButtonContentColor(buttonColor = payload.buttonColor, materialColor = MaterialTheme.colors)

    TextButton(
      onClick = { CommandManagerImpl.handleInput(input = payload.buttonCommand, context = context) },
      colors = ButtonDefaults.buttonColors(
        backgroundColor = buttonColor
      ),
      modifier = Modifier.fillMaxWidth(),
      shape = MaterialTheme.shapes.medium
    ) {
      Text(
        text = payload.buttonLabel,
        color = buttonContentColor
      )

      Spacer(
        modifier = Modifier.width(5.dp)
      )

      // TODO: Пофиксить уменьшение иконки при слишком длинном тексте.
      Icon(
        imageVector = Icons.Rounded.Code,
        contentDescription = "Отправить команду",
        tint = buttonContentColor
      )
    }
  }
}
