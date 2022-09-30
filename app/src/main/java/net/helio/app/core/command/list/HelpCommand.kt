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

package net.helio.app.core.command.list

import net.helio.app.core.command.Command
import net.helio.app.core.command.manager.CommandManagerImpl
import net.helio.app.core.command.session.CommandSession
import java.util.*


/**
 * Команда для получения информации об IP.
 *
 * @author hepller
 */
object HelpCommand : Command {
  override val aliases: List<String> = listOf("help", "помощь", "commands", "cmds", "команды")
  override val description: String = "Информация о доступных командах"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    val messageScheme = StringJoiner("\n")

    messageScheme.add("\uD83C\uDF35 Доступные команды:")
    messageScheme.add("")
    messageScheme.add(getCommandList().joinToString("\n"))
    messageScheme.add("")
    messageScheme.add("⚙️ Команды помеченные \"*\" не являются анонимными")
    messageScheme.add("")
    messageScheme.add("\uD83D\uDCDD Префиксы команд: [/, !]")

    session.reply(messageScheme.toString())
  }

  /**
   * Получает список команд с описанием в виде списка строк.
   *
   * @return Список команд с описанием.
   */
  private fun getCommandList(): MutableList<String> {
    val output: MutableList<String> = mutableListOf()

    for (command in CommandManagerImpl.commandList) {
      val betaStatus = if (command.isInBeta) "ᵇᵉᵗᵃ" else ""
      val nonAnonymous = if (!command.isAnonymous) "*" else ""

      output.add("/${command.aliases[0]}$nonAnonymous — ${command.description} $betaStatus".trim())
    }

    return output
  }
}