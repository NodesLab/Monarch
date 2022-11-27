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

package net.monarch.app.ui.main.scaffold.structure.content.message.payload

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.monarch.app.core.message.model.payload.MessagePayload
import net.monarch.app.core.message.model.payload.buttons.CommandButtonPayload
import net.monarch.app.core.message.model.payload.buttons.DropdownButtonPayload
import net.monarch.app.core.message.model.payload.buttons.LinkButtonPayload
import net.monarch.app.ui.main.scaffold.structure.content.message.payload.types.CommandButton
import net.monarch.app.ui.main.scaffold.structure.content.message.payload.types.DropdownButton
import net.monarch.app.ui.main.scaffold.structure.content.message.payload.types.LinkButton

/**
 * Обработчик полезной нагрузки.
 *
 * @param payloadList Список полезной нагрузки.
 * @param modifier Модификатор (для корректного отображения некоторых типов ПН).
 *
 * @author hepller
 */
@Composable
fun PayloadProcessor(payloadList: List<MessagePayload>, modifier: Modifier) {
  if (payloadList.isNotEmpty()) {
    Column(
      modifier = modifier.padding(bottom = 25.dp)
    ) {
      for (payloadItem in payloadList) {
        if (payloadItem is DropdownButtonPayload) {
          DropdownButton(payload = payloadItem, modifier = modifier)
        }

        if (payloadItem is LinkButtonPayload) {
          LinkButton(payload = payloadItem, modifier = modifier)
        }

        if (payloadItem is CommandButtonPayload) {
          CommandButton(payload = payloadItem, modifier = modifier)
        }
      }
    }
  } else {
    Spacer(modifier = Modifier.height(height = 20.dp))
  }
}
