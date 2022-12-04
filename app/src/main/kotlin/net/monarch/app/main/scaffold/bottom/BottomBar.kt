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

package net.monarch.app.main.scaffold.bottom

import android.content.Context
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.monarch.app.main.scaffold.bottom.buttons.SendButton
import net.monarch.app.main.scaffold.bottom.buttons.VoiceButton
import net.monarch.core.command.manager.CommandManagerImpl

/**
 * Нижняя панель.
 *
 * @author hepller
 */
@Composable
fun BottomBar() {
  var input: String by rememberSaveable { mutableStateOf(value = "") }

  val context: Context = LocalContext.current

  BottomAppBar(
    backgroundColor = MaterialTheme.colors.primary,
  ) {
    VoiceButton(enabled = false) {
      /* TODO */
    }

    TextField(
      value = input,
      onValueChange = { newText ->
        input = newText.trimStart { it == '\n' }
      },
      placeholder = {
        Text(text = "Введите команду")
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
      modifier = Modifier
        .fillMaxHeight()
        .width(width = 260.dp)
        .weight(weight = 1f, fill = true)
    )

    SendButton(enabled = input.trim().isNotEmpty()) {
      CommandManagerImpl.handleInput(input = input.trim(), context = context)

      input = ""
    }
  }
}
