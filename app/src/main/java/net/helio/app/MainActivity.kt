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

package net.helio.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import net.helio.app.core.command.list.TestCommand
import net.helio.app.core.command.manager.CommandManagerImpl
import net.helio.app.ui.message.MessageManagerImpl
import net.helio.app.ui.scaffold.AppScaffold
import net.helio.app.ui.theme.Accent
import net.helio.app.ui.theme.HelioTheme

/**
 * Главное активити.
 *
 * @author hepller
 */
class MainActivity : ComponentActivity() {

  @SuppressLint("RememberReturnType")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // region Регистрация команд.

    CommandManagerImpl.registerCommand(TestCommand)

    // endregion

    setContent {
      HelioTheme {
        val textSelectionColors = TextSelectionColors(
          handleColor = Accent,
          backgroundColor = Accent.copy(alpha = 0.4f)
        )

        // Обёртка для установки цветов выделения текста.
        CompositionLocalProvider(LocalTextSelectionColors provides textSelectionColors) {
          AppScaffold()
        }

        // region Сообщение об обновлении активити.

        // remember для исправления рекомпозиции, при которой сообщение дублируется.
        remember {
          MessageManagerImpl.botMessage("Активити обновлено")
        }

        // endregion
      }
    }
  }
}