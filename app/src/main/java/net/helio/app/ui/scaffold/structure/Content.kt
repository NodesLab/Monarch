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

package net.helio.app.ui.scaffold.structure

//import net.helio.app.ui.message.data.messageMutableList
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.helio.app.ui.message.MessageManagerImpl
import net.helio.app.ui.message.MessageManagerImpl.messageList
import net.helio.app.ui.message.data.Message
import net.helio.app.ui.theme.Accent
import java.text.SimpleDateFormat
import java.util.*

/**
 * Список всех сообщений.
 */
//var messageMutableList: SnapshotStateList<Message> = mutableStateListOf()

/**
 * Отрисовывает карточку сообщения.
 *
 * @param message Объект сообщения.
 */
@Composable
private fun MessageCard(message: Message) {
  val color: Color = if (message.isFromBot()) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.secondary
  val alignment: Alignment = if (message.isFromBot()) Alignment.TopStart else Alignment.TopEnd
  val author: String = if (message.isFromBot()) "Helio" else "User"

  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = color,
      elevation = 1.dp,
      modifier = Modifier
        .align(alignment)
        .widthIn(min = 100.dp, max = 250.dp) // Устанавливает лимиты ширины сообщения.
        .heightIn(min = 80.dp) // Устанавливает лимиты высоты сообщения.
    ) {
      Box(
        contentAlignment = Alignment.TopStart
      ) {
        IconButton(
          onClick = { MessageManagerImpl.removeMessage(message) },
          modifier = Modifier
            .size(25.dp)
            .padding(end = 5.dp, top = 5.dp)
            .align(Alignment.TopEnd)
        ) {
          Icon(
            imageVector = Icons.Rounded.Clear,
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary
          )
        }

        Column {
          Text(
            text = author,
            color = Accent,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp)
          )

          SelectionContainer {
            Text(
              text = message.text,
              color = MaterialTheme.colors.onPrimary,
              style = MaterialTheme.typography.body2,
              modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp),
            )
          }
        }

        Text(
          text = SimpleDateFormat("HH:mm", Locale.US).format(message.date),
          style = MaterialTheme.typography.body2,
          color = MaterialTheme.colors.onSecondary,
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(end = 8.dp, bottom = 4.dp)
        )
      }
    }
  }
}

/**
 * Добавляет автопрокрутку списка до последнего элемента.
 */
@Composable
private fun AutoScroll(listState: LazyListState) {
  LaunchedEffect(listState) {
    if (!listState.isScrollInProgress) listState.animateScrollToItem(index = listState.layoutInfo.totalItemsCount)
  }
}

/**
 * Отрисовывает список сообщений и устанавливает автопрокрутку.
 *
 * @param messages Сообщения.
 */
@Composable
private fun MessageList(messages: List<Message>) {
  val listState: LazyListState = rememberLazyListState()

  LazyColumn(
    state = listState,
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    items(items = messages) { message ->
      MessageCard(message)

      AutoScroll(listState)
    }
  }
}

/**
 * Отрисовывает содержимое основного поля.
 *
 * @param contentPadding Отспупы для содержимого.
 */
@Composable
fun Content(contentPadding: PaddingValues) {
  Surface(
    modifier = Modifier
      .fillMaxSize()
      .padding(contentPadding)
  ) {
    MessageList(messages = messageList)
  }
}