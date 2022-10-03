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

package net.helio.app.core.message.manager

import androidx.compose.runtime.snapshots.SnapshotStateList
import net.helio.app.core.message.model.Message

/**
 * Интерфейс менеджера сообщений.
 *
 * @author hepller
 */
interface MessageManager {

  /**
   * Изменяемый список сообщений.
   */
  val messageList: SnapshotStateList<Message>

  /**
   * Добавляет сообщение бота в список.
   *
   * @param text Текст сообщения.
   */
  fun appMessage(text: String)

  /**
   * Добавляет сообщение пользователя в список.
   *
   * @param text Текст сообщения.
   */
  fun userMessage(text: String)

  /**
   * Удаляет сообщение из списка.
   *
   * @param message Сообщение, которое будет удалено.
   */
  fun removeMessage(message: Message)
}