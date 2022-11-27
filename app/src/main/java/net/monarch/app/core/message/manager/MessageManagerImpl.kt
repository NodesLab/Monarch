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

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import net.monarch.app.core.message.model.Message
import net.monarch.app.core.message.model.MessageAuthor
import net.monarch.app.core.message.model.MessageImpl
import net.monarch.app.core.message.model.meta.MessageMeta
import net.monarch.app.core.message.model.meta.MessageMetaImpl
import net.monarch.app.core.message.model.payload.MessagePayload
import java.util.*

enum class MessageType {
  INFO,
  WARN,
  ERROR
}

/**
 * Реализация менеджера сообщений.
 *
 * Управляет сообщениями и используется для их отправки.
 *
 * @author hepller
 */
object MessageManagerImpl : MessageManager {

  override var messageList: SnapshotStateList<Message> = mutableStateListOf()

  /**
   * Добавляет сообщение в список сообщений.
   *
   * @param author Автор сообщения.
   * @param text Текст сообщения.
   * @param payloadList Полезная нагрузка.
   */
  private fun addMessage(author: MessageAuthor, text: String, payloadList: List<MessagePayload> = listOf()) {
    val messageMeta: MessageMeta = MessageMetaImpl(
      payloadList = payloadList
    )

    val message: Message = MessageImpl(
      author = author,
      text = text,
      date = Date(),
      meta = messageMeta
    )

    messageList.add(message)
  }

  override fun appMessage(text: String, payloadList: List<MessagePayload>) {
    addMessage(author = MessageAuthor.APP, text = text, payloadList = payloadList)
  }

  override fun userMessage(text: String) {
    addMessage(author = MessageAuthor.USER, text = text)
  }

  override fun removeMessage(message: Message) {
    messageList.remove(element = message)
  }
}
