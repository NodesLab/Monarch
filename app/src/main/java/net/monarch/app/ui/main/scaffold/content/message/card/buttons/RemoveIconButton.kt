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

package net.monarch.app.ui.main.scaffold.content.message.card.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.Message

/**
 * Кнопка-иконка для удаления сообщения.
 *
 * @param message Объект сообщения.
 *
 * @author hepller
 */
@Composable
fun RemoveIconButton(message: Message) {
  IconButton(
    onClick = { MessageManagerImpl.removeMessage(message = message) },
    modifier = Modifier
      .size(size = 25.dp)
  ) {
    Icon(
      imageVector = Icons.Rounded.Clear,
      contentDescription = "Удалить сообщение",
      tint = MaterialTheme.colors.onSecondary
    )
  }
}
