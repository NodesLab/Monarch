/*
 * Copyright © 2022 The Helio contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import java.text.SimpleDateFormat
import java.util.*

/**
 * Утилита для работы с сообщениями.
 *
 * @author hepller
 */
class MessageUtility {
  companion object {

    /**
     * Получает время в формате `HH:mm`.
     *
     * @return Время в формате `HH:mm`.
     */
    private fun getTime(): String {
      return SimpleDateFormat("HH:mm", Locale.US).format(Date())
    }

    /**
     * Добавляет сообщение бота в лист сообщений.
     *
     * @param messageText Текст сообщения.
     */
    fun addBotMessage(messageText: String) {
      messageList.add(Message("bot", messageText, getTime()))
    }

    /**
     * Добавляет сообщение пользователя в лист сообщений.
     *
     * @param messageText Текст сообщения.
     */
    fun addUserMessage(messageText: String) {
      messageList.add(Message("user", messageText, getTime()))
    }
  }
}