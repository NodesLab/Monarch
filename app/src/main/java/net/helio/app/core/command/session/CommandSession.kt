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

package net.helio.app.core.command.session

import net.helio.app.core.message.manager.MessageManager
import net.helio.app.core.message.payload.MessagePayload

/**
 * Интерфейс сессии команды.
 *
 * @author hepller
 */
interface CommandSession {

  /**
   * Список аргументов.
   * Первый аргумент - имя алиаса, через который была вызвана команда.
   */
  val arguments: List<String>

  /**
   * Создает сообщение (с возможностью прикрепить полезную нагрузку).
   *
   * @param text Текст сообщения.
   * @param payload Полезная нагрузка.
   *
   * @see MessageManager.appMessage
   * @see MessagePayload
   */
  fun reply(text: String, payload: MessagePayload? = null)
}