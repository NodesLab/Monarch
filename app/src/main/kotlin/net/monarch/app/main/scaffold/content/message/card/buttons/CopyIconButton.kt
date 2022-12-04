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

package net.monarch.app.main.scaffold.content.message.card.buttons

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import net.monarch.app.utility.ToastUtility

/**
 * Кнопка-иконка для копирования сообщения.
 *
 * @param clipboardManager Менеджер буфера обмена.
 * @param annotatedString Аннотированная строка.
 * @param context Контекст.
 *
 * @author hepller
 */
@Composable
fun CopyIconButton(clipboardManager: ClipboardManager, annotatedString: AnnotatedString, context: Context) {
  IconButton(
    onClick = {
      clipboardManager.setText(annotatedString = annotatedString)

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
}
