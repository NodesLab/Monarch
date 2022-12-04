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

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import net.monarch.app.theme.Green
import net.monarch.app.theme.Red
import net.monarch.core.message.model.payload.buttons.color.ButtonColor

/**
 * Утилита для работы с цветами.
 *
 * @author hepller
 */
object ColorUtility {

  /**
   * Получает цвет кнопки исходя из Enum-класса.
   *
   * @param buttonColor [ButtonColor].
   * @param materialColor Цвета темы ([Colors]).
   *
   * @return Цвет кнопки.
   */
  fun resolveButtonColor(buttonColor: ButtonColor, materialColor: Colors): Color {
    val color: Color = when (buttonColor) {
      ButtonColor.PRIMARY -> { materialColor.secondary }

      ButtonColor.SECONDARY -> { materialColor.onPrimary }

      ButtonColor.GREEN -> { Green }

      ButtonColor.RED -> { Red }
    }

    return color
  }

  /**
   * Получает цвет содержимого кнопки исходя из Enum-класса.
   *
   * @param buttonColor [ButtonColor].
   * @param materialColor Цвета темы ([Colors]).
   *
   * @return Цвет содержимого кнопки.
   */
  fun resolveButtonContentColor(buttonColor: ButtonColor, materialColor: Colors): Color {
    val color: Color = when (buttonColor) {
      ButtonColor.PRIMARY -> { materialColor.onPrimary }

      ButtonColor.SECONDARY -> { materialColor.primary }

      ButtonColor.GREEN -> { materialColor.onPrimary }

      ButtonColor.RED -> { materialColor.onPrimary }
    }

    return color
  }
}
