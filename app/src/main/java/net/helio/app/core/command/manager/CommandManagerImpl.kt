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

package net.helio.app.core.command.manager

import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.core.command.session.CommandSessionImpl
import net.helio.app.ui.message.manager.MessageManagerImpl

/**
 * Реализация менеджера команд.
 *
 * @author hepller
 */
object CommandManagerImpl : CommandManager {
  override var commandList: MutableList<Command> = mutableListOf()

  override fun registerCommand(command: Command) {
    commandList.add(command)
  }

  override fun getCommand(command: String): Command? {
    return commandList.find {
      it.aliases.contains(command)
    }
  }

  override fun handleInput(command: String) {
    val commandObject: Command? = getCommand(command.substring(1).lowercase().split(" ")[0])
    val commandSession: CommandSession = CommandSessionImpl(command.substring(1).split(" "))

    if (commandObject == null) {
      MessageManagerImpl.botMessage("⚠️ Неизвестная команда, для просмотра списка команд введите \"/help\"")

      return
    }

    try {
      commandObject.execute(commandSession)
    } catch (exception: Exception) {
      println(exception)

      MessageManagerImpl.botMessage("⚠️ При выполнении команды произошла ошибка")
    }
  }
}