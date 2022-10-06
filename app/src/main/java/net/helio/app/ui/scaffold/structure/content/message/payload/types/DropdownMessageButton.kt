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

package net.helio.app.ui.scaffold.structure.content.message.payload.types

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.helio.app.core.message.model.Message
import net.helio.app.core.message.model.payload.DropdownMessagePayload

/**
 * Раскрывающийся текст в кнопке.
 *
 * @param message Объект сообщения.
 * @param modifier Модификатор (для корректного отображения).
 *
 * @author hepller
 */
@Composable
fun DropdownMessageButton(message: Message, modifier: Modifier) {
  val payload: DropdownMessagePayload = message.payload as DropdownMessagePayload

  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .padding(top = 5.dp, bottom = 30.dp)
  ) {
    var isExpanded: Boolean by remember { mutableStateOf(false) }
    var expandText: String by remember { mutableStateOf(payload.dropdownLabel) }

    TextButton(
      onClick = {
        isExpanded = !isExpanded
        expandText = if (expandText == payload.dropdownLabel) payload.dropdownText else payload.dropdownLabel
      },
      colors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.secondary
      ),
      modifier = Modifier.fillMaxWidth()
    ) {
      SelectionContainer {
        Text(
          text = expandText,
          color = MaterialTheme.colors.onPrimary
        )
      }
    }
  }
}