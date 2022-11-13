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

package net.monarch.app.ui.main.scaffold.structure.content.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.monarch.app.core.message.model.Message

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
fun MessageColumn(messages: List<Message>) {
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
