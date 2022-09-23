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

package net.helio.app.ui.scaffold.inner

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.helio.app.R
import net.helio.app.data.MessagesData
import net.helio.app.utility.MessageUtility
import net.helio.app.utility.ToastUtility

/**
 * Отрисовывает нижнюю панель.
 *
 * @param scope Область видимости.
 * @param listState Состояние листа сообщений.
 */
@Composable
fun BottomBar(scope: CoroutineScope, listState: LazyListState) {
  var input: String by rememberSaveable { mutableStateOf("") }
  val isValid: Boolean = input.isNotEmpty()

  // Контекст для отображения тостов.
  val context: Context = LocalContext.current

  val emptyInputString: String = stringResource(R.string.empty_input_toast)

  Surface(
    color = MaterialTheme.colors.primary,
    modifier = Modifier
      .fillMaxWidth()
      .height(60.dp),
    elevation = 40.dp
  ) {
    Row(
      modifier = Modifier.fillMaxSize()
    ) {
      TextField(
        value = input,
        onValueChange = { newText ->
          input = newText.trimStart { it == '0' }
        },
        placeholder = {
          Text(text = stringResource(R.string.input_placeholder))
        },
        isError = !isValid,
        colors = TextFieldDefaults.textFieldColors(
          textColor = Color.White,
          backgroundColor = Color.Transparent,
          placeholderColor = Color.Gray,
          cursorColor = Color.White,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
          disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
          .height(60.dp)
          .width(270.dp)
          .padding(start = 10.dp)
      )

      Button(
        onClick = {
          if (input.isEmpty()) {
            ToastUtility.makeShortToast(context, emptyInputString)

            return@Button
          }

          MessageUtility.addUserMessage(input)

          // todo: Обработка команд.

          input = ""

          scope.launch {
            listState.animateScrollToItem(index = MessagesData.messageList.size - 1)
          }
        },
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevation(
          defaultElevation = 0.dp,
          pressedElevation = 15.dp,
          disabledElevation = 0.dp
        ),
        modifier = Modifier.padding(start = 12.dp, top = 6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Send,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(30.dp)
        )
      }
    }
  }
}