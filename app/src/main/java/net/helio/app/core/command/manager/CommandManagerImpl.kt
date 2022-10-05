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
import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.utility.NetworkUtility
import net.helio.app.core.utility.TextUtility
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Реализация менеджера команд.
 *
 * @author hepller
 */
object CommandManagerImpl : CommandManager {
  override var commandList: MutableList<Command> = mutableListOf()

  override fun registerCommand(command: Command) {
    if (commandList.contains(element = command)) return

    commandList.add(command)
  }

  override fun getCommand(alias: String): Command? {
    return commandList.find {
      it.aliases.contains(element = alias)
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun handleInput(input: String, context: Context?) {
    MessageManagerImpl.userMessage(text = input.trim())

    val inputArgs: List<String> = input.substring(startIndex = 1).split(" ")

    val command: Command? = getCommand(alias = input.substring(startIndex = 1).lowercase().split(" ")[0])
    val session: CommandSession = CommandSessionImpl(arguments = inputArgs)

    if (command == null) {
      unknownCommandMessage(session = session, input = input)

      return
    }

    if (context != null && command.isRequireNetwork && !NetworkUtility.hasNetworkConnection(context = context)) {
      session.reply(text = "⚠️ Для использования этой команды необходим доступ к сети")

      return
    }

    GlobalScope.launch {
      val coroutineContext: CoroutineContext = getDispatcherFromCurrentThread(scope = this)

      executeCommand(command = command, session = session, context = coroutineContext)
    }
  }

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

  /**
   * Получает похожие алиасы команд с заданной точностью.
   *
   * @param commandList Список команд.
   * @param input Входной текст.
   * @param distance Точность (расстояние Левенштейна).
   *
   * @return Список из списков похожих команд.
   */
  private fun getSimilarCommandAliases(commandList: List<Command>, input: String, distance: Double): List<List<String>> {
    val aliasesList: MutableList<List<String>> = mutableListOf()

    for (commandItem: Command in commandList) {
      for (aliasItem: String in commandItem.aliases) {
        if (TextUtility.getStringSimilarity(aliasItem, input) > distance) {
          aliasesList.add(listOf(aliasItem, commandItem.aliases[0]))
        }
      }
    }

    return aliasesList
  }

  /**
   * Производит сообщение о неизвестной команде.
   *
   * @param session Сессия команды.
   * @param input Ввод пользователя.
   */
  private fun unknownCommandMessage(session: CommandSession, input: String) {
    val messageScheme = StringJoiner("\n")

    messageScheme.add("⚠️ Команды «${input.split(" ")[0]}» не существует")
    messageScheme.add("")

    val inputArgs: List<String> = input.substring(startIndex = 1).split(" ")

    val similarAliases: List<List<String>> = getSimilarCommandAliases(commandList = commandList, input = inputArgs[0], distance = 0.4)

    if (similarAliases.isNotEmpty()) {
      val mutableSimilarList: MutableList<String> = mutableListOf()

      for (similarItem: List<String> in similarAliases) {
        mutableSimilarList.add("${similarItem[0]} (${similarItem[1]})")
      }

      messageScheme.add("Возможно вы хотели ввести один из следующих алиасов:")
      messageScheme.add("")

      for (aliasItem: String in mutableSimilarList) {
        messageScheme.add("– /$aliasItem")
      }

      messageScheme.add("")
    }

    messageScheme.add("Для просмотра полного списка команд введите «/commands»")

    session.reply(messageScheme.toString())
  }

  /**
   * Выполняет команду в корутине.
   *
   * @param command Объект команды.
   * @param session Сессия команды.
   * @param context Контекст корутины.
   */
  private fun executeCommand(command: Command, session: CommandSession, context: CoroutineContext) {
    CoroutineScope(context = context).launch {
      try {
        command.execute(session = session)
      } catch (exception: Exception) {
        val errorStackTrace: String = exception.stackTrace.joinToString(separator = "\n")

        session.dropdownMessage(
          text = "⚠️ При выполнении команды произошла ошибка",
          dropdownLabel = "Подробная информация",
          dropdownText = errorStackTrace
        )
      }
    }
  }
}