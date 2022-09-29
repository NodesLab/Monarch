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

package net.helio.app.utility

/**
 * Утилита для работы с текстом.
 *
 * @author hepller
 */
object TextUtility {

  /**
   * Получает emoji флаг страны.
   *
   * @param countryCode Код страны (2 символа).
   *
   * @return Emoji флаг страны.
   */
  @Throws(IllegalArgumentException::class)
  fun getCountryFlagEmoji(countryCode: String): String {
    require(countryCode.length <= 2) { "The \"countryCode\" parameter must not exceed a length of 2 characters" }

    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41

    val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
    val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset

    return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
  }
}