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

package net.monarch.app.ui.main.scaffold.structure.content.message

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.Message
import net.monarch.app.ui.main.scaffold.structure.content.message.payload.PayloadProcessor
import net.monarch.app.ui.theme.Accent
import net.monarch.app.ui.utility.StringUtility
import net.monarch.app.ui.utility.ToastUtility
import java.text.SimpleDateFormat
import java.util.*

/**
 * Карточка сообщения.
 *
 * @param message Объект сообщения.
 *
 * @author hepller
 */
// TODO: Рассортировать на подкомпоненты.
@Composable
fun MessageCard(message: Message) {
  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    val color: Color =
      if (message.meta.rightPosition) MaterialTheme.colors.secondary
      else MaterialTheme.colors.secondaryVariant

    val alignment: Alignment =
      if (message.meta.rightPosition) Alignment.TopEnd
      else Alignment.TopStart

    val paddings: PaddingValues =
      if (message.meta.rightPosition) PaddingValues(start = 65.dp, end = 2.dp)
      else PaddingValues(start = 2.dp, end = 65.dp)

    Surface(
      shape = MaterialTheme.shapes.large,
      color = color,
      elevation = 1.dp,
      modifier = Modifier
        .align(alignment)
        .padding(paddings)
        .widthIn(min = 100.dp, max = 500.dp) // Устанавливает лимиты ширины сообщения.
        .heightIn(min = 80.dp) // Устанавливает лимиты высоты сообщения.
    ) {
      Box(
        contentAlignment = Alignment.TopStart
      ) {
        Row(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 3.dp, top = 3.dp)
        ) {
          val context: Context = LocalContext.current
          val clipboardManager: ClipboardManager = LocalClipboardManager.current

          val messageAnnotatedString: AnnotatedString = buildAnnotatedString {
            append(text = message.text)
          }

          IconButton(
            onClick = {
              clipboardManager.setText(annotatedString = messageAnnotatedString)

              ToastUtility.makeShortToast(text = "Скопировано в буфер обмена", context = context)
            },
            modifier = Modifier
              .size(size = 25.dp)
              .padding(end = 5.dp)
          ) {
            Icon(
              imageVector = Icons.Rounded.ContentCopy,
              contentDescription = "Копировать сообщение",
              tint = MaterialTheme.colors.onSecondary
            )
          }

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

        Column(
          modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)
        ) {
          Text(
            text = message.author,
            color = Accent,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.SemiBold
          )

          SelectionContainer {
            val annotatedString: AnnotatedString = StringUtility.parseLinks(text = message.text)

            Text(
              text = annotatedString,
              color = MaterialTheme.colors.onPrimary,
              style = MaterialTheme.typography.body2,
              fontSize = 15.sp, // TODO: перенести типографию в тему.
              modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
            )
          }

          PayloadProcessor(
            messageMeta = message.meta,
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
        }

        Text(
          text = SimpleDateFormat("HH:mm:ss", Locale.US).format(message.date),
          style = MaterialTheme.typography.body2,
          fontSize = 12.sp, // TODO: перенести типографию в тему.
          color = MaterialTheme.colors.onSecondary,
          modifier = Modifier
            .align(alignment = Alignment.BottomEnd)
            .padding(end = 10.dp, bottom = 5.dp)
        )
      }
    }
  }
}
