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

package net.helio.app.ui.scaffold.structure.content.message.payload

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.helio.app.core.message.model.Message
import net.helio.app.core.message.model.payload.DropdownMessagePayload
import net.helio.app.core.message.model.payload.LinkMessagePayload
import net.helio.app.ui.scaffold.structure.content.message.payload.types.DropdownMessageButton
import net.helio.app.ui.scaffold.structure.content.message.payload.types.LinkMessageButton

/**
 * Обработчик полезной нагрузки.
 *
 * @param message Объект сообщения.
 * @param modifier Модификатор (для корректного отображения некоторых типов ПН).
 *
 * @author hepller
 */
@Composable
fun PayloadProcessor(message: Message, modifier: Modifier) {
  if (message.payload is DropdownMessagePayload) {
    DropdownMessageButton(message = message, modifier = modifier)
  }

  if (message.payload is LinkMessagePayload) {
    LinkMessageButton(message = message, modifier = modifier)
  }
}