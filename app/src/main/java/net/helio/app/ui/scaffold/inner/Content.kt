/*
 * Copyright © 2022 The Helio contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.helio.app.ui.scaffold.inner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.helio.app.data.Message
import net.helio.app.data.MessagesData
import net.helio.app.ui.theme.BotMessageBackgroundDark
import net.helio.app.ui.theme.UserMessageBackgroundDark

/**
 * Карта сообщения.
 *
 * @param message Объект сообщения.
 */
@Composable
fun MessageCard(message: Message) {
  val color: Color = if (message.author == "bot") BotMessageBackgroundDark else UserMessageBackgroundDark
  val alignment: Alignment = if (message.author == "bot") Alignment.TopStart else Alignment.TopEnd
  val author: String = if (message.author == "bot") "Helio" else "User"

  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = color,
      modifier = Modifier.align(alignment)
    ) {
      Column(
        modifier = Modifier.defaultMinSize(100.dp),
      ) {
        Text(
          text = author,
          color = MaterialTheme.colors.secondary,
          style = MaterialTheme.typography.subtitle2,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.padding(start = 10.dp, top = 4.dp)
        )

        SelectionContainer {
          Text(
            text = message.text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 10.dp),
          )
        }

        Text(
          text = message.time,
          style = MaterialTheme.typography.body2,
          color = Color.Gray,
          modifier = Modifier
            .align(Alignment.End)
            .padding(end = 8.dp, bottom = 4.dp, top = 4.dp)
            .clickable {
              MessagesData.messageList.remove(message)
            }
        )
      }
    }
  }
}

/**
 * Лист из сообщений.
 *
 * @param messages Сообщения.
 */
@Composable
fun MessageList(messages: List<Message>) {
  val listState: LazyListState = rememberLazyListState()

  LazyColumn(
    state = listState,
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    items(messages) { message -> MessageCard(message) }
  }
}

/**
 * Содержимое основного поля.
 */
@Composable
fun Content() {
  Surface(
    modifier = Modifier
      .fillMaxSize()
      .padding(bottom = 60.dp),
    color = MaterialTheme.colors.background
  ) {
    MessageList(messages = MessagesData.messageList)
  }
}