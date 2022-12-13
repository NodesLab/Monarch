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

package net.monarch.core.message.model.payload.buttons

import net.monarch.core.message.model.payload.MessagePayload
import net.monarch.core.message.model.payload.buttons.color.ButtonColor

/**
 * Полезная нагрузка в виде кнопки содержащей команду.
 */
data class CommandButtonPayload(

  /**
   * Текст на кнопке.
   */
  val buttonLabel: String,

  /**
   * Команда, которая будет выполнена при нажатии на кнопку..
   */
  val buttonCommand: String,

  /**
   * Цвет кнопки.
   */
  val buttonColor: ButtonColor = ButtonColor.PRIMARY
) : MessagePayload
