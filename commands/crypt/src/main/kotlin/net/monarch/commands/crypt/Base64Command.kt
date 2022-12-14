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
import net.monarch.core.message.model.payload.buttons.CommandButtonPayload
import java.util.*

/**
 * Команда для работы с Base64.
 *
 * @author hepller
 */
object Base64Command : Command {
  override val triggers: List<String> = listOf(
    "base64",
    "конвертируй base64",
    "конвертировать base64",
    "конвертация base64",
    "конвертируй бейс64",
    "конвертировать бейс64",
    "конвертация бейс64",
    "бейс64",
    "b64",
    "б64"
  )

//  override val triggers: List<String> = TextUtility.generateStringsFromRegEx(pattern = "((конверт(ир(уй|овать)|ация)) )?([bб](ase|ейс)?64)")

  override val description: String = "Конвертирование текста в Base64 и обратно"

  override val properties: CommandProperties = CommandPropertiesImpl(
    isInBeta = false,
    isRequireNetwork = false,
    isAnonymous = true
  )

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.isEmpty()) {
      return MessageManagerImpl.appMessage(text = "⛔ Вы не указали текст для конвертации")
    }

    if (!listOf("кодировка", "декодировка").contains(session.arguments[0])) {
      return MessageManagerImpl.appMessage(
        text = "⛔ Укажите действие для текста",
        payloadList = listOf(
          CommandButtonPayload(
            buttonLabel = "Кодировать",
            buttonCommand = "Конвертация Base64 кодировка ${session.arguments.joinToString(separator = " ")}"
          ),
          CommandButtonPayload(
            buttonLabel = "Декодировать",
            buttonCommand = "Конвертация Base64 декодировка ${session.arguments.joinToString(separator = " ")}"
          )
        )
      )
    }

    val text: String = session.arguments.drop(n = 1).joinToString(" ") // drop 1, чтобы в текст не попадало название алгоритма.

    val convertingType: String
    val byteArray: ByteArray

    when (session.arguments[0]) {
      "кодировка" -> {
        convertingType = "Закодированный"

        byteArray = Base64.getEncoder().encode(text.toByteArray())
      }

      "декодировка" -> {
        convertingType = "Декодированный"

        try {
          byteArray = Base64.getDecoder().decode(text.toByteArray())
        } catch (_: IllegalArgumentException) {
          return MessageManagerImpl.appMessage(text = "⚠️️ Указанная Base64-строка некорректна")
        }
      }

      else -> {
        return MessageManagerImpl.appMessage(
          text = "⚠️️ Такого действия не существует",
          payloadList = listOf(
            CommandButtonPayload(
              buttonLabel = "Кодировать",
              buttonCommand = "Конвертирование Base64 кодировка $text"
            ),
            CommandButtonPayload(
              buttonLabel = "Декодировать",
              buttonCommand = "Конвертирование Base64 декодировка $text"
            )
          )
        )
      }
    }

    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "\uD83D\uDD11 $convertingType текст:")
    messageScheme.add(element = "")
    messageScheme.add(element = String(byteArray))

    MessageManagerImpl.appMessage(text = messageScheme.joinToString(separator = "\n"))
  }
}
