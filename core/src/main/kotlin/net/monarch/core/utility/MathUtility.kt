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

package net.monarch.core.utility

import kotlin.math.roundToInt

/**
 * Утилита для работы с числами и вычислениями.
 *
 * @author hepller
 */
object MathUtility {

  /**
   * Проверяет, является ли строка числом.
   *
   * @param string Строка для проверки.
   *
   * @return `false` если строка не является числом, `true` если является.
   */
  fun isNumber(string: String): Boolean {
    return try {
      string.toLong()

      true
    } catch (_: NumberFormatException) {
      false
    }
  }

  /**
   * Округляет число с плавающей точкой.
   *
   * @param number Число с плавающей точкой.
   * @param decimals Десятки (количество цифр после точки).
   */
  // TODO: Сделать в виде расширения класса Float.
  fun roundFloat(number: Float, decimals: Int = 2): Float {
    require(decimals > 0) { "The \"numbersAfterDot\" parameter must not be less than zero" }

    var multiplier = 1.0F

    repeat(decimals) { multiplier *= 10 }

    return (number * multiplier).roundToInt() / multiplier
  }
}
