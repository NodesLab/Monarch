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

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.monarch.app.core.message.model.payload.buttons.DropdownButtonPayload
import net.monarch.app.ui.utility.ColorUtility

/**
 * Раскрывающийся текст в кнопке.
 *
 * @param payload Объект полезной нагрузки.
 * @param modifier Модификатор (для корректного отображения).
 *
 * @see DropdownButtonPayload
 *
 * @author hepller
 */
@Composable
fun DropdownButton(payload: DropdownButtonPayload, modifier: Modifier) {
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.padding(top = 5.dp)
  ) {
    var isExpanded: Boolean by remember { mutableStateOf(false) }
    var expandText: String by remember { mutableStateOf(payload.dropdownLabel) }

    val buttonColor: Color = ColorUtility.resolveButtonColor(buttonColor = payload.buttonColor, materialColor = MaterialTheme.colors)
    val buttonContentColor: Color = ColorUtility.resolveButtonContentColor(buttonColor = payload.buttonColor, materialColor = MaterialTheme.colors)

    TextButton(
      onClick = {
        isExpanded = !isExpanded
        expandText = if (expandText == payload.dropdownLabel) payload.dropdownText else payload.dropdownLabel
      },
      colors = ButtonDefaults.buttonColors(
        backgroundColor = buttonColor
      ),
      modifier = Modifier.fillMaxWidth(),
      shape = MaterialTheme.shapes.medium
    ) {
      Text(
        text = expandText,
        color = buttonContentColor
      )

      Spacer(
        modifier = Modifier.width(5.dp)
      )

      // TODO: Пофиксить уменьшение иконки при слишком длинном тексте.
      Icon(
        imageVector = Icons.Rounded.ExpandMore,
        contentDescription = "Раскрыть текст",
        tint = buttonContentColor
      )
    }
  }
}
