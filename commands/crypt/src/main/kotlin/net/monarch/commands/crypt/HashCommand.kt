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

package net.monarch.commands.crypt

import net.monarch.core.command.Command
import net.monarch.core.command.properties.CommandProperties
import net.monarch.core.command.properties.CommandPropertiesImpl
import net.monarch.core.command.session.CommandSession
import net.monarch.core.message.manager.MessageManagerImpl
import net.monarch.core.message.model.payload.MessagePayload
import net.monarch.core.message.model.payload.buttons.CommandButtonPayload
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

/**
 * Команда для хеширования строки.
 *
 * @author hepller
 */
object HashCommand : Command {
  override val triggers: List<String> = listOf(
    "hash",
    "хеширование",
    "хешировать",
    "хешируй",
    "хеш"
  )

//  override val triggers: List<String> = TextUtility.generateStringsFromRegEx(pattern = "(hash|хеш(ир(уй|ова(ние|ть)))?)")

  override val description: String = "Хеширование строки"

  override val properties: CommandProperties = CommandPropertiesImpl(
    isInBeta = false,
    isRequireNetwork = false,
    isAnonymous = true
  )

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.isEmpty()) {
      return MessageManagerImpl.appMessage(text = "⛔ Вы не указали текст для хеширования")
    }

    val algorithms: List<String> = listOf("SHA-512", "SHA-384", "SHA-256", "SHA-1", "MD5")

    if (!algorithms.contains(session.arguments[0])) {
      val buttonList: List<MessagePayload> = getButtons(
        algorithms = algorithms,
        arguments = session.arguments
      )

      return MessageManagerImpl.appMessage(
        text = "⛔ Выберите алгоритм хеширования",
        payloadList = buttonList
      )
    }

    val hashedString: String = hashString(
      algorithm = session.arguments[0],
      text = session.arguments.drop(n = 1).joinToString(" ") // drop 1, чтобы в хеш не попадало название алгоритма.
    )

    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "⚙️ Хешированный текст (${session.arguments[0]}):")
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
        buttonCommand = "Хеширование $algorithm ${arguments.joinToString(separator = " ")}"
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
