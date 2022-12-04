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

package net.monarch.app.main.scaffold.content.message.card

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.monarch.app.utility.StringUtility

/**
 * Основной текст сообщения.
 *
 * @param text Текст сообщения.
 *
 * @author hepller
 */
@Composable
fun MessageBodyText(text: String) {
  SelectionContainer {
    val annotatedString: AnnotatedString = StringUtility.parseLinks(text = text)

    Text(
      text = annotatedString,
      color = MaterialTheme.colors.onPrimary,
      style = MaterialTheme.typography.body2,
      fontSize = 15.sp, // TODO: перенести типографию в тему.
      modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
    )
  }
}