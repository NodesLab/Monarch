/*
 * Copyright © 2022 The Monarch Contributors.
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

package net.monarch.app.ui.utility

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import net.monarch.app.core.utility.TextUtility
import net.monarch.app.ui.theme.Accent

/**
 * Утилита для работы со строками интерфейса.
 *
 * @author hepller
 */
object StringUtility {

  /**
   * Меняет стиль для ссылок в тексте.
   *
   * @param text Текст.
   *
   * @return Строка с разными стилями.
   */
  fun parseLinks(text: String): AnnotatedString {
    val annotatedString: AnnotatedString = buildAnnotatedString {
      append(text)

      for (url in TextUtility.getUrlsList(text)) {
        val startIndex: Int = text.indexOf(url)
        val endIndex: Int = startIndex + url.length

        addStyle(
          style = SpanStyle(
            color = Accent,
          ), start = startIndex, end = endIndex
        )
      }
    }

    return annotatedString
  }
}