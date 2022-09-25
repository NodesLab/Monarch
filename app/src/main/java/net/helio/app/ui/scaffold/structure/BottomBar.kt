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

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.helio.app.R
import net.helio.app.core.testHandle
import net.helio.app.utility.ToastUtility.makeShortToast

/**
 * Отрисовывает кнопку-иконку.
 *
 * @param onClick Функция, которая будет выполнена при нажатии.
 */
@Composable
private fun SendButton(onClick: () -> Unit) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.Default.Send,
      contentDescription = null,
      tint = MaterialTheme.colors.onPrimary,
      modifier = Modifier.size(30.dp)
    )
  }
}

/**
 * Отрисовывает нижнюю панель.
 */
@Composable
fun BottomBar() {
  var input: String by rememberSaveable { mutableStateOf("") }

  // Контекст для отображения тостов.
  val context: Context = LocalContext.current

  val emptyInputString: String = stringResource(R.string.empty_input_toast)

  Surface(
    color = MaterialTheme.colors.primary,
    elevation = 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .height(75.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxSize()
        .padding(
          start = 0.dp,
          end = 15.dp,
          top = 10.dp,
          bottom = 10.dp
        )
    ) {
      SelectionContainer {
        TextField(
          value = input,
          onValueChange = { newText ->
            input = newText.trimStart { it == ' ' }
          },
          placeholder = {
            Text(text = stringResource(R.string.input_placeholder))
          },
          colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            backgroundColor = Color.Transparent,
            placeholderColor = MaterialTheme.colors.onSecondary,
            cursorColor = MaterialTheme.colors.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
          ),
          shape = RoundedCornerShape(5.dp),
          modifier = Modifier
            .fillMaxHeight()
            .width(270.dp)
            .padding(start = 10.dp)
        )
      }

      Spacer(Modifier.weight(1f))

      SendButton {
        if (input.trim().isEmpty()) {
          makeShortToast(context, emptyInputString)

          return@SendButton
        }

        testHandle(input)

        input = ""
      }
    }
  }
}