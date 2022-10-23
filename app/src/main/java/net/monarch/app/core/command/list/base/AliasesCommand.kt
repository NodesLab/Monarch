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

package net.monarch.app.core.command.list.base

import net.monarch.app.core.command.Command
import net.monarch.app.core.command.manager.CommandManagerImpl
import net.monarch.app.core.command.session.CommandSession
import net.monarch.app.core.message.manager.MessageManagerImpl

/**
 * Команда для просмотра алиасов команд.
 *
 * @author hepller
 */
object AliasesCommand : Command {
  override val aliases: List<String> = listOf("aliases", "алиасы")
  override val description: String = "Информация об алиасах команд"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "\uD83D\uDCAC Алиасы команд:")
    messageScheme.add(element = "")
    messageScheme.add(element = getAliasesList().joinToString(separator = "\n"))

    MessageManagerImpl.appMessage(text = messageScheme.joinToString(separator = "\n"))
  }

  /**
   * Получает список алиасов команд в виде массива строк.
   *
   * @return Список алиасов команд.
   */
  private fun getAliasesList(): MutableList<String> {
    val output: MutableList<String> = mutableListOf()

    for (command in CommandManagerImpl.commandList) {
      val aliasesString = command.aliases.joinToString(separator = ", ")

      output.add(element = "/${command.aliases[0]}: [$aliasesString]")
    }

    return output
  }
}