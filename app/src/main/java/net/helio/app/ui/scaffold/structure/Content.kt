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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.helio.app.R
import net.helio.app.data.Message
import net.helio.app.data.messageMutableList
import net.helio.app.ui.manager.message.MessageManagerImpl
import net.helio.app.ui.theme.Accent
import java.text.SimpleDateFormat
import java.util.*

/**
 * Отрисовывает карточку сообщения.
 *
 * @param message Объект сообщения.
 */
@Composable
private fun MessageCard(message: Message) {
  val color: Color = if (message.isFromBot()) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.secondary
  val alignment: Alignment = if (message.isFromBot()) Alignment.TopStart else Alignment.TopEnd
  val author: String = if (message.isFromBot()) stringResource(R.string.message_bot_name) else stringResource(R.string.message_user_name)

  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = color,
      elevation = 1.dp,
      modifier = Modifier
        .align(alignment)
        .defaultMinSize(minWidth = 100.dp) // Устанавливает минимальную ширину сообщения.
        .widthIn(min = 0.dp, max = 250.dp) // Устанавливает максимальную ширину сообщения.
    ) {
      Column(
        modifier = Modifier,
      ) {
        Text(
          text = author,
          color = Accent,
          style = MaterialTheme.typography.subtitle2,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.padding(start = 10.dp, top = 4.dp)
        )

        SelectionContainer {
          Text(
            text = message.text,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 10.dp),
          )
        }

        Text(
          text = SimpleDateFormat("HH:mm", Locale.US).format(message.date),
          style = MaterialTheme.typography.body2,
          color = MaterialTheme.colors.onSecondary,
          modifier = Modifier
            .align(Alignment.End)
            .padding(end = 8.dp, bottom = 4.dp, top = 4.dp)
            .clickable {
              MessageManagerImpl.removeMessage(message)
            }
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
    MessageList(messages = messageMutableList)
  }
}