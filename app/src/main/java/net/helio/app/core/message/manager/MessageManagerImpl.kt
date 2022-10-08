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

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.helio.app.core.message.model.Message
import net.helio.app.core.message.model.MessageImpl
import net.helio.app.core.message.payload.MessagePayload
import java.util.*

/**
 * Стандартный менеджер сообщений.
 *
 * @author hepller
 */
object MessageManagerImpl : MessageManager {

  override var messageList: SnapshotStateList<Message> = mutableStateListOf()

  /**
   * Добавляет сообщение в список сообщений.
   *
   * @param author Автор сообщения (app | user).
   * @param text Текст сообщения.
   * @param payload Полезная нагрузка.
   */
  private fun addMessage(author: String, text: String, payload: MessagePayload? = null) {
    messageList.add(MessageImpl(author, text, Date(), payload))
  }

  override fun appMessage(text: String, payload: MessagePayload?) {
    addMessage(author = "app", text = text, payload = payload)
  }

  override fun userMessage(text: String) {
    addMessage(author = "user", text = text, payload = null)
  }

  override fun removeMessage(message: Message) {
    messageList.remove(element = message)
  }
}