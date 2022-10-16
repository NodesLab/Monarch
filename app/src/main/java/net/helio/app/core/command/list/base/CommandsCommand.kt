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

package net.helio.app.core.command.list.base

import net.helio.app.core.command.Command
import net.helio.app.core.command.manager.CommandManagerImpl
import net.helio.app.core.command.session.CommandSession
import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.message.model.payload.CommandButtonPayload

/**
 * Команда для просмотра доступных команд.
 *
 * @author hepller
 */
object CommandsCommand : Command {
  override val aliases: List<String> = listOf("commands", "cmds", "команды", "help", "помощь")
  override val description: String = "Информация о доступных командах"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "\uD83C\uDF35 Доступные команды:")
    messageScheme.add(element = "")
    messageScheme.add(element = getCommandList().joinToString(separator = "\n"))
    messageScheme.add(element = "")
    messageScheme.add(element = "\uD83D\uDCDD Обозначения:")
    messageScheme.add(element = "")
    messageScheme.add(element = "* – Не анонимная команда")
    messageScheme.add(element = "^ – Требует доступ к сети")

    MessageManagerImpl.appMessage(
      text = messageScheme.joinToString(separator = "\n"),
      payloadList = listOf(
        CommandButtonPayload(
          buttonLabel = "Алиасы команд",
          buttonCommand = "/aliases"
        )
      )
    )
  }

  /**
   * Получает список команд с описанием в виде списка строк.
   *
   * @return Список команд с описанием.
   */
  private fun getCommandList(): MutableList<String> {
    val output: MutableList<String> = mutableListOf()

    for (command in CommandManagerImpl.commandList) {
      val betaStatus: String = if (command.isInBeta) "ᵇᵉᵗᵃ" else ""
      val nonAnonymous: String = if (!command.isAnonymous) "*" else ""
      val requireInternet: String = if (command.isRequireNetwork) "^" else ""

      output.add(element = "/${command.aliases[0]}$requireInternet$nonAnonymous — ${command.description} $betaStatus".trim())
    }

    return output
  }
}