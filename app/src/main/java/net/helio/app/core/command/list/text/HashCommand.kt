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

package net.helio.app.core.command.list.text

import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.message.model.payload.CommandButtonPayload
import net.helio.app.core.message.model.payload.MessagePayload
import java.security.MessageDigest
import java.util.*
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

    val algorithms: List<String> = listOf("sha-512", "sha-384", "sha-256", "sha-1", "md5")

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

    val messageScheme = StringJoiner("\n")

    messageScheme.add("⚙️ Хешированный текст:")
    messageScheme.add("")
    messageScheme.add(hashedString)

    MessageManagerImpl.appMessage(text = messageScheme.toString())
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