/*
 * Copyright © 2022 The Monarch Contributors.
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

package net.monarch.app.core.command.list.network

import net.monarch.app.core.command.Command
import net.monarch.app.core.command.session.CommandSession
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.payload.CommandButtonPayload
import net.monarch.app.core.utility.NetworkUtility

/**
 * Команда для проверки порта на доступность.
 *
 * @author hepller
 */
object PortCommand : Command {
  override val aliases: List<String> = listOf("port", "порт")
  override val description: String = "Проверка порта на доступность"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = true
  override val isAnonymous: Boolean = false

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.size < 2) {
      return MessageManagerImpl.appMessage(text = "⛔ Использование: /${aliases[0]} <хост> <порт>")
    }

    if (session.arguments.size < 3) {
      return MessageManagerImpl.appMessage(text = "⛔ Использование: /${aliases[0]} <хост> <порт>")
    }

    val host: String = NetworkUtility.clearUrl(url = session.arguments[1])
    val port: Int = Integer.parseInt(session.arguments[2])

    if (port > 65535) {
      return MessageManagerImpl.appMessage(text = "⚠️️ Порт должен быть числом не больше чем 65535")
    }

    MessageManagerImpl.appMessage(text = "⚙️ Проверка порта на доступность ...")

    val replyMessage: String =
      if (NetworkUtility.isPortAvailable(host = host, port = port)) "✅ Порт $port ($host) открыт"
      else "❌ Порт $port ($host) закрыт, или недоступен"

    MessageManagerImpl.appMessage(
      text = replyMessage,
      payloadList = listOf(
        CommandButtonPayload(
          buttonLabel = "Информация об IP",
          buttonCommand = "/ip $host"
        )
      )
    )
  }
}