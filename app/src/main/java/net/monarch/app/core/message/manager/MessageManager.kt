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

package net.monarch.app.core.message.manager

import androidx.compose.runtime.snapshots.SnapshotStateList
import net.monarch.app.core.message.model.Message
import net.monarch.app.core.message.model.payload.MessagePayload

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
   * Добавляет сообщение ассистента в список (с возможностью прикрепить полезную нагрузку).
   *
   * @param text Текст сообщения.
   * @param payloadList Список полезной нагрузки (по умолчанию пустой).
   */
  fun appMessage(text: String, payloadList: List<MessagePayload> = listOf())

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
