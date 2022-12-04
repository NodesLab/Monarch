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

package net.monarch.app.utility

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import net.monarch.app.theme.Accent
import net.monarch.core.utility.TextUtility

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

  fun parseFormatV2(text: String): AnnotatedString {
    val annotatedString: AnnotatedString = buildAnnotatedString {
    }

    if (annotatedString.isEmpty()) return AnnotatedString(text = text)

    return annotatedString
  }

  fun parseFormat(text: String): AnnotatedString {
    val annotatedString: AnnotatedString = buildAnnotatedString {

      val accentText: String = text.substringBefore(delimiter = "<format:accent>", missingDelimiterValue = "")

      val accentTargetText: String = text
        .substringAfter(delimiter = "<format:accent>", missingDelimiterValue = "")
        .substringBefore(delimiter = "<format:reset>", missingDelimiterValue = "")

      val accentResetText: String = text.substringAfter(delimiter = "<format:reset>", missingDelimiterValue = "")

      append(accentText)

      withStyle(
        style = SpanStyle(color = Accent)
      ) {
        append(accentTargetText)
      }

//      append(accentResetText)

      // -----

      val grayPreviousText = accentResetText
      val grayText: String = grayPreviousText
//        .substringAfter(delimiter = "<format:reset>", missingDelimiterValue = "")
        .substringBefore(delimiter = "<format:gray>", missingDelimiterValue = "")

      val grayTargetText: String = grayPreviousText
        .substringAfter(delimiter = "<format:gray>", missingDelimiterValue = "")
        .substringBefore(delimiter = "<format:reset>", missingDelimiterValue = "")

      val grayResetText: String = grayPreviousText.substringAfter(delimiter = "<format:reset>", missingDelimiterValue = "")

//      append(grayText)

      withStyle(
        style = SpanStyle(color = Color.Gray)
      ) {
        append(grayTargetText)
      }

      append(grayResetText)
    }

    if (annotatedString.isEmpty()) return AnnotatedString(text = text)

    return annotatedString
  }
}
