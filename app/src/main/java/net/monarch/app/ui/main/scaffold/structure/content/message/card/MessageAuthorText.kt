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

package net.monarch.app.ui.main.scaffold.structure.content.message.card

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import net.monarch.app.core.message.model.MessageAuthor

/**
 * Текст с автором сообщения.
 *
 * @param author Автор сообщения.
 *
 * @author hepller
 */
@Composable
fun AuthorText(author: MessageAuthor) {
  val authorText: String =
    if (author == MessageAuthor.USER) "Пользователь"
    else "«Monarch»"

  Text(
    text = authorText,
    color = MaterialTheme.colors.onPrimary,
    style = MaterialTheme.typography.subtitle2,
    fontWeight = FontWeight.SemiBold
  )
}
