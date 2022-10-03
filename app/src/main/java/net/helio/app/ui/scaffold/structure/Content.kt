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

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.message.manager.MessageManagerImpl.messageList
import net.helio.app.core.message.model.Message
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
private fun MessageCard(message: Message) {
  val color: Color = if (message.isFromApp()) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.secondary
  val alignment: Alignment = if (message.isFromApp()) Alignment.TopStart else Alignment.TopEnd
  val author: String = if (message.isFromApp()) "Helio" else "User"

  val annotatedString = StringUtility.parseLinks(text = message.text)

  var scale by remember { mutableStateOf(value = 1f) }
  var rotation by remember { mutableStateOf(value = 0f) }
  var offset by remember { mutableStateOf(value = Offset.Zero) }
  val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
    scale *= zoomChange
    rotation += rotationChange
    offset += offsetChange
  }

  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Surface(
      shape = RoundedCornerShape(size = 16.dp),
      color = color,
      elevation = 1.dp,
      modifier = Modifier
        .align(alignment)
        .widthIn(min = 80.dp, max = 280.dp) // Устанавливает лимиты ширины сообщения.
        .heightIn(min = 80.dp) // Устанавливает лимиты высоты сообщения.
        .graphicsLayer(
          scaleX = scale,
          scaleY = scale,
          rotationZ = rotation,
          translationX = offset.x,
          translationY = offset.y
        )
        .transformable(state = state)
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
            imageVector = Icons.Rounded.Clear,
            contentDescription = "Удалить сообщение",
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
              text = annotatedString,
              color = MaterialTheme.colors.onPrimary,
              style = MaterialTheme.typography.body2,
              fontSize = 15.sp, // todo: перенести типографию в тему
              modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 20.dp),
            )
          }
        }

        Text(
          text = SimpleDateFormat("HH:mm:ss", Locale.US).format(message.date),
          style = MaterialTheme.typography.body2,
          fontSize = 12.sp, // todo: перенести типографию в тему
          color = MaterialTheme.colors.onSecondary,
          modifier = Modifier
            .align(alignment = Alignment.BottomEnd)
            .padding(end = 8.dp, bottom = 4.dp)
        )
      }
    }
  }
}

/**
 * Добавляет автопрокрутку списка до последнего элемента.
 *
 * @param listState Состояние списка.
 *
 * @author hepller
 */
@Composable
private fun AutoScroll(listState: LazyListState) {
  LaunchedEffect(key1 = Unit) {
    if (!listState.isScrollInProgress) listState.animateScrollToItem(index = listState.layoutInfo.totalItemsCount)
  }
}

/**
 * Колонна сообщений с автопрокруткой.
 *
 * @param messages Сообщения.
 *
 * @author hepller
 */
@Composable
private fun MessageColumn(messages: List<Message>) {
  val listState: LazyListState = rememberLazyListState()

  LazyColumn(
    state = listState,
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(space = 10.dp),
    modifier = Modifier.background(color = MaterialTheme.colors.primary)
  ) {
    items(items = messages) { message ->
      MessageCard(message = message)

      AutoScroll(listState = listState)
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
      .padding(paddingValues = contentPadding)
  ) {
    MessageColumn(messages = messageList)
  }
}