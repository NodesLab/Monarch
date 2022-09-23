package me.hepller.helioapp.utility

import me.hepller.helioapp.data.Message
import me.hepller.helioapp.data.MessagesData.messageList
import java.text.SimpleDateFormat
import java.util.*

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