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
 * Команда для просмотра триггеров команд.
 *
 * @author hepller
 */
object TriggersCommand : Command {
  override val triggers: List<String> = listOf(
    "triggers",
    "триггеры",
    "список триггеров",
    "покажи триггеры"
  )

  override val description: String = "Информация о триггерах команд"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "\uD83D\uDCAC Триггеры команд:")
    messageScheme.add(element = "")
    messageScheme.add(element = getTriggersList().joinToString(separator = "\n"))

    MessageManagerImpl.appMessage(text = messageScheme.joinToString(separator = "\n"))
  }

  /**
   * Получает список алиасов команд в виде массива строк.
   *
   * @return Список алиасов команд.
   */
  private fun getTriggersList(): MutableList<String> {
    val output: MutableList<String> = mutableListOf()

    for (command: Command in CommandManagerImpl.commandList) {
      val triggersString = command.triggers.joinToString(separator = " | ")

      output.add(element = "${command.triggers[0]}: [$triggersString]")
    }

    return output
  }
}
