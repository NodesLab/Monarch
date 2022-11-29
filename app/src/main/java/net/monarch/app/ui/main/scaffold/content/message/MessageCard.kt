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

package net.monarch.app.ui.main.scaffold.content.message

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import net.monarch.app.core.message.model.Message
import net.monarch.app.core.message.model.MessageAuthor
import net.monarch.app.ui.main.scaffold.content.message.card.AuthorText
import net.monarch.app.ui.main.scaffold.content.message.card.MessageBodyText
import net.monarch.app.ui.main.scaffold.content.message.card.MessageDateText
import net.monarch.app.ui.main.scaffold.content.message.card.buttons.CopyIconButton
import net.monarch.app.ui.main.scaffold.content.message.card.buttons.RemoveIconButton
import net.monarch.app.ui.main.scaffold.content.message.payload.PayloadProcessor

/**
 * Карточка сообщения.
 *
 * @param message Объект сообщения.
 *
 * @author hepller
 */
@Composable
fun MessageCard(message: Message) {
  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    val color: Color =
      if (message.author == MessageAuthor.USER) MaterialTheme.colors.secondary
      else MaterialTheme.colors.secondaryVariant

    val alignment: Alignment =
      if (message.author == MessageAuthor.USER) Alignment.TopEnd
      else Alignment.TopStart

    val paddings: PaddingValues =
      if (message.author == MessageAuthor.USER) PaddingValues(start = 65.dp, end = 2.dp)
      else PaddingValues(start = 2.dp, end = 65.dp)

    Surface(
      shape = MaterialTheme.shapes.large,
      color = color,
      elevation = 1.dp,
      modifier = Modifier
        .align(alignment)
        .padding(paddings)
        // TODO: Минимальная ширина исходя из длины имени автора.
        .widthIn(min = 170.dp, max = 500.dp) // Устанавливает лимиты ширины карточки сообщения.
        .heightIn(min = 80.dp) // Устанавливает лимиты высоты карточки сообщения.
    ) {
      Box(
        contentAlignment = Alignment.TopStart
      ) {
        Row(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 5.dp, top = 5.dp)
        ) {
          CopyIconButton(
            clipboardManager = LocalClipboardManager.current,
            annotatedString = buildAnnotatedString { append(text = message.text) },
            context = LocalContext.current
          )

          RemoveIconButton(
            message = message
          )
        }

        Column(
          modifier = Modifier.padding(start = 12.dp, end = 10.dp, top = 8.dp)
        ) {
          AuthorText(
            author = message.author
          )
          
          MessageBodyText(
            text = message.text
          )

          PayloadProcessor(
            payloadList = message.meta.payloadList,
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
        }

        MessageDateText(
          date = message.date,
          modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
      }
    }
  }
}
