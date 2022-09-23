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

import net.helio.app.data.Message
import net.helio.app.data.MessagesData.messageList
import java.util.*

/**
 * Утилита для работы с сообщениями.
 *
 * @author hepller
 */
class MessageUtility {
  companion object {

    /**
     * Добавляет сообщение в список сообщений.
     *
     * @param author Автор сообщения (bot | user).
     * @param text Текст сообщения.
     */
    private fun addMessage(author: String, text: String) {
      messageList.add(Message(author, text, Date()))
    }

    /**
     * Добавляет сообщение бота в лист сообщений.
     *
     * @param text Текст сообщения.
     */
    fun addBotMessage(text: String) {
      addMessage("bot", text)
    }

    /**
     * Добавляет сообщение пользователя в лист сообщений.
     *
     * @param text Текст сообщения.
     */
    fun addUserMessage(text: String) {
      addMessage("user", text)
    }

    /**
     * Удаляет сообщение из списка.
     *
     * @param message Сообщение, которое будет удалено.
     */
    fun removeMessage(message: Message) {
      messageList.remove(message)
    }
  }
}