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

package net.helio.app.ui.scaffold.structure.content.message

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.message.model.Message
import net.helio.app.ui.scaffold.structure.content.message.payload.PayloadProcessor
import net.helio.app.ui.theme.Accent
import net.helio.app.ui.utility.StringUtility
import java.text.SimpleDateFormat
import java.util.*

/**
 * Карточка сообщения.
 *
 * @param message Объект сообщения.
 *
 * @author hepller
 */
@Composable
fun MessageCard(message: Message) {
  val color: Color = if (message.isFromApp()) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.secondary
  val alignment: Alignment = if (message.isFromApp()) Alignment.TopStart else Alignment.TopEnd
  val author: String = if (message.isFromApp()) "Helio" else "User"

  val annotatedString: AnnotatedString = StringUtility.parseLinks(text = message.text)

  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Surface(
      shape = MaterialTheme.shapes.large,
      color = color,
      elevation = 1.dp,
      modifier = Modifier
        .align(alignment)
        .widthIn(min = 80.dp, max = 280.dp) // Устанавливает лимиты ширины сообщения.
        .heightIn(min = 80.dp) // Устанавливает лимиты высоты сообщения.
    ) {
      Box(
        contentAlignment = Alignment.TopStart
      ) {
        IconButton(
          onClick = { MessageManagerImpl.removeMessage(message = message) },
          modifier = Modifier
            .size(size = 25.dp)
            .padding(end = 5.dp, top = 5.dp)
            .align(Alignment.TopEnd)
        ) {
          Icon(
            imageVector = Icons.Rounded.Cancel,
            contentDescription = "Удалить сообщение",
            tint = MaterialTheme.colors.onSecondary
          )
        }

        Column(
          modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)
        ) {
          Text(
            text = author,
            color = Accent,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.SemiBold
          )

          SelectionContainer {
            Text(
              text = annotatedString,
              color = MaterialTheme.colors.onPrimary,
              style = MaterialTheme.typography.body2,
              fontSize = 15.sp, // TODO: перенести типографию в тему
              modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
          }

          PayloadProcessor(message = message, modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Text(
          text = SimpleDateFormat("HH:mm:ss", Locale.US).format(message.date),
          style = MaterialTheme.typography.body2,
          fontSize = 12.sp, // TODO: перенести типографию в тему
          color = MaterialTheme.colors.onSecondary,
          modifier = Modifier
            .align(alignment = Alignment.BottomEnd)
            .padding(end = 10.dp, bottom = 5.dp)
        )
      }
    }
  }
}