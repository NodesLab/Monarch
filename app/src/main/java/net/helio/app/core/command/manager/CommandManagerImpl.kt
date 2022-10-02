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

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.core.command.session.CommandSessionImpl
import net.helio.app.core.utility.NetworkUtility
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Реализация менеджера команд.
 *
 * @author hepller
 */
object CommandManagerImpl : CommandManager {
  override var commandList: MutableList<Command> = mutableListOf()

  /**
   * Получает контекст для корутины из области.
   *
   * @param scope Область корутины.
   *
   * @return Контекст для корутины.
   */
  private fun getDispatcherFromCurrentThread(scope: CoroutineScope): CoroutineContext {
    return scope.coroutineContext
  }

  override fun registerCommand(command: Command) {
    if (commandList.contains(command)) return

    commandList.add(command)
  }

  override fun getCommand(alias: String): Command? {
    return commandList.find {
      it.aliases.contains(alias)
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun handleInput(input: String, context: Context) {
    val command: Command? = getCommand(input.substring(1).lowercase().split(" ")[0])
    val session: CommandSession = CommandSessionImpl(input.substring(1).split(" "))

    if (command == null) {
      session.reply("⚠️ Неизвестная команда, для просмотра списка команд введите \"/help\"")

      return
    }

    if (command.isRequireNetwork && !NetworkUtility.hasNetworkConnection(context)) {
      session.reply("⚠️ Для использования этой команды необходим доступ к сети")

      return
    }

    GlobalScope.launch {
      val dispatcher: CoroutineContext = getDispatcherFromCurrentThread(this)

      CoroutineScope(dispatcher).launch {
        try {
          command.execute(session)
        } catch (exception: Exception) {
          val messageScheme = StringJoiner("\n")

          messageScheme.add("⚠️ При выполнении команды произошла ошибка:")
          messageScheme.add("")
          messageScheme.add(exception.stackTrace.joinToString("\n"))

          session.reply(messageScheme.toString())
        }
      }
    }
  }
}