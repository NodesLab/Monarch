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
import net.helio.app.core.message.payload.CommandButtonPayload
import java.util.*

/**
 * Команда для работы с Base64.
 *
 * @author hepller
 */
object Base64Command : Command {
  override val aliases: List<String> = listOf("base64", "бейс64")
  override val description: String = "Конвертирует текст в base64 и обратно."

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.size < 2) {
      return MessageManagerImpl.appMessage(text = "⛔ Укажите текст для конвертации")
    }

    if (session.arguments[1] != "encode" && session.arguments[1] != "decode") {
      return MessageManagerImpl.appMessage(
        text = "⛔ Укажите действие, для текста",
        payloadList = listOf(
          CommandButtonPayload(
            buttonLabel = "Кодировать",
            buttonCommand = "/base64 encode ${session.arguments.drop(n = 1).joinToString(separator = " ")}"
          ),
          CommandButtonPayload(
            buttonLabel = "Декодировать",
            buttonCommand = "/base64 decode ${session.arguments.drop(n = 1).joinToString(separator = " ")}"
          )
        )
      )
    }

    val text: String = session.arguments.drop(n = 2).joinToString(" ")

    val convertingType: String
    val byteArray: ByteArray

    when (session.arguments[1]) {
      "encode" -> {
        convertingType = "Закодированный"

        byteArray = Base64.getEncoder().encode(text.toByteArray())
      }

      "decode" -> {
        convertingType = "Декодированный"

        byteArray = Base64.getDecoder().decode(text.toByteArray())
      }

      else -> {
        return MessageManagerImpl.appMessage(
          text = "⚠️️ Такого действия не существует",
          payloadList = listOf(
            CommandButtonPayload(
              buttonLabel = "Кодировать",
              buttonCommand = "/base64 encode $text"
            ),
            CommandButtonPayload(
              buttonLabel = "Декодировать",
              buttonCommand = "/base64 decode $text"
            )
          )
        )
      }
    }

    val messageScheme = StringJoiner("\n")

    messageScheme.add("\uD83D\uDD11 $convertingType текст:")
    messageScheme.add("")
    messageScheme.add(String(byteArray))

    MessageManagerImpl.appMessage(text = messageScheme.toString())
  }
}