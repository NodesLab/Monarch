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

package net.monarch.app.core.command.list.crypt

import net.monarch.app.core.command.Command
import net.monarch.app.core.command.session.CommandSession
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.payload.MessagePayload
import net.monarch.app.core.message.model.payload.buttons.CommandButtonPayload
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

/**
 * Команда для хеширования строки.
 *
 * @author hepller
 */
object HashCommand : Command {
  override val aliases: List<String> = listOf("hash", "хеш")
  override val description: String = "Хеширование строки"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.size < 2) {
      return MessageManagerImpl.appMessage(text = "⛔ Использование: /${aliases[0]} [<алгоритм>] <текст>")
    }

    val algorithms: List<String> = listOf("SHA-512", "SHA-384", "SHA-256", "SHA-1", "MD5")

    if (!algorithms.contains(session.arguments[1])) {
      val buttonList: List<MessagePayload> = getButtons(
        algorithms = algorithms,
        arguments = session.arguments
      )

      return MessageManagerImpl.appMessage(
        text = "⛔ Укажите алгоритм хеширования",
        payloadList = buttonList
      )
    }

    val hashedString: String = hashString(
      algorithm = session.arguments[1],
      text = session.arguments.drop(n = 2).joinToString(" ")
    )

    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "⚙️ Хешированный текст (${session.arguments[1]}):")
    messageScheme.add(element = "")
    messageScheme.add(element = hashedString)

    MessageManagerImpl.appMessage(text = messageScheme.joinToString(separator = "\n"))
  }

  /**
   * Получает список кнопок для каждого алгоритма.
   *
   * @param algorithms Список алгоритмов.
   * @param arguments Список аргументов ввода.
   *
   * @return Список кнопок.
   */
  private fun getButtons(algorithms: List<String>, arguments: List<String>): List<MessagePayload> {
    val buttonsList: MutableList<MessagePayload> = mutableListOf()

    for (algorithm: String in algorithms) {
      val button = CommandButtonPayload(
        buttonLabel = algorithm,
        buttonCommand = "/hash $algorithm ${arguments.drop(n = 1).joinToString(separator = " ")}"
      )

      buttonsList.add(button)
    }

    return buttonsList
  }

  /**
   * Хеширует строку указываемым алгоритмом.
   *
   * @param algorithm Алгоритм хеширования.
   * @param text Строка для хеширования.
   *
   * @return Хешированая строка.
   */
  private fun hashString(algorithm: String, text: String): String {
    val crypt: ByteArray = MessageDigest
      .getInstance(algorithm)
      .digest(text.toByteArray(UTF_8))

    return crypt.joinToString(separator = "") { byte -> "%02x".format(byte) }
  }
}
