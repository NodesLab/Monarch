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

package net.monarch.app.core.utility

import com.linkedin.urls.Url
import com.linkedin.urls.detection.UrlDetector
import com.linkedin.urls.detection.UrlDetectorOptions
import kotlin.math.max
import kotlin.math.min

/**
 * Утилита для работы с текстом.
 *
 * @author hepller
 */
object TextUtility {

  /**
   * Получает расстояние Левенштейна.
   *
   * @param firstString Первая строка.
   * @param secondString Вторая строка.
   *
   * @return Расстояние Левенштейна.
   */
  private fun getLevenshteinDistance(firstString: String, secondString: String): Int {
    val firstStringLength: Int = firstString.length
    val secondStringLength: Int = secondString.length

    val intArraysArray: Array<IntArray> = Array(size = firstStringLength + 1) {
      IntArray(size = secondStringLength + 1)
    }

    for (item in 1..firstStringLength) {
      intArraysArray[item][0] = item
    }

    for (item in 1..secondStringLength) {
      intArraysArray[0][item] = item
    }

    var cost: Int

    for (item in 1..firstStringLength) {
      for (j in 1..secondStringLength) {
        cost = if (firstString[item - 1] == secondString[j - 1]) 0 else 1

        intArraysArray[item][j] = min(min(a = intArraysArray[item - 1][j] + 1, intArraysArray[item][j - 1] + 1), b = intArraysArray[item - 1][j - 1] + cost)
      }
    }

    return intArraysArray[firstStringLength][secondStringLength]
  }

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

  /**
   * Проверяет, является ли строка числом.
   *
   * @param string Строка для проверки.
   *
   * @return `false` если строка не является числом, `true` если является.
   */
  fun isNumber(string: String): Boolean {
    return try {
      string.toInt()

      true
    } catch (exception: NumberFormatException) {
      false
    }
  }

  /**
   * Получает сходство между двумя строками (расстояние Левенштейна).
   *
   * @param firstString Первая строка.
   * @param secondString Вторая строка.
   *
   * @return Расстояние Левенштейна.
   */
  fun getStringSimilarity(firstString: String, secondString: String): Double {
    val maxLength = max(firstString.length, secondString.length)

    return if (maxLength > 0) { (maxLength * 1.0 - getLevenshteinDistance(firstString, secondString)) / maxLength * 1.0 } else 1.0
  }

  /**
   * Получает из текста URl-адреса.
   *
   * @param text Текст, из которого будут получаться URL-адреса.
   *
   * @return Список URl-адресов и их очищенных версий в виде строк.
   *
   * TODO: Переписать на собственный метод вместо библиотеки.
   */
  fun getUrlsList(text: String?): MutableList<String> {
    val urlDetector = UrlDetector(text, UrlDetectorOptions.Default)
    val urlList: List<Url> = urlDetector.detect()

    val mutableUrlList: MutableList<String> = mutableListOf()

    for (url: Url in urlList) {
      val stringUrl = url.toString()

      mutableUrlList.add(stringUrl)
      mutableUrlList.add(NetworkUtility.clearUrl(stringUrl))
    }

    return mutableUrlList
  }
}