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

package net.monarch.core.command.manager

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.monarch.core.command.Command
import net.monarch.core.command.session.CommandSession
import net.monarch.core.command.session.CommandSessionImpl
import net.monarch.core.command.session.properties.CommandSessionProperties
import net.monarch.core.command.session.properties.CommandSessionPropertiesImpl
import net.monarch.core.message.manager.MessageManagerImpl
import net.monarch.core.message.model.payload.buttons.CommandButtonPayload
import net.monarch.core.message.model.payload.buttons.DropdownButtonPayload
import net.monarch.core.utility.NetworkUtility
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Реализация менеджера команд.
 *
 * Управляет обработкой команд и их ошибок.
 *
 * @author hepller
 */
object CommandManagerImpl : CommandManager {
  override var commandList: MutableList<Command> = mutableListOf()

  override fun registerCommand(command: Command) {
    if (commandList.contains(element = command)) return

    commandList.add(command)
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun handleInput(input: String, context: Context) {
    val formattedInput: String = input.trim()

    MessageManagerImpl.userMessage(text = formattedInput)

    val trigger: String = resolveTrigger(formattedInput)
      ?: return MessageManagerImpl.appMessage(
        text = "⚠️ Команда не распознана",
        payloadList = listOf(
          CommandButtonPayload(
            buttonLabel = "Список команд",
            buttonCommand = "Список команд"
          )
        )
      )

    val command: Command = getCommand(trigger)
      ?: return

    val isNetworkAvailable: Boolean = NetworkUtility.hasNetworkConnection(context = context)

    if (command.properties.isRequireNetwork && !isNetworkAvailable) {
      return MessageManagerImpl.appMessage(
        text = "⚠️ Для использования этой команды необходим доступ к сети",
        payloadList = listOf(
          CommandButtonPayload(
            buttonLabel = "Повторить команду",
            buttonCommand = input
          )
        )
      )
    }

    val sessionProperties: CommandSessionProperties = CommandSessionPropertiesImpl(
      isNetworkAvailable = isNetworkAvailable
    )

    val commandSession: CommandSession = CommandSessionImpl(
      arguments = formattedInput.replaceFirst(oldValue = trigger, newValue = "", ignoreCase = true).split(" ").drop(n = 1),
      properties = sessionProperties,
      context = context
    )

    GlobalScope.launch {
      val coroutineContext: CoroutineContext = this.coroutineContext

      executeCommand(command = command, session = commandSession, context = coroutineContext)
    }
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
        MessageManagerImpl.appMessage(
          text = "⚠️ При выполнении команды произошла ошибка",
          payloadList = listOf(
            DropdownButtonPayload(
              dropdownLabel = "Подробная информация",
              dropdownText = exception.stackTrace.joinToString(separator = "\n")
            )
          )
        )
      }
    }
  }

  /**
   * Получает из текста триггер команды.
   *
   * @param text Текст, из которого будет получаться триггер.
   *
   * @return Триггер, если он содержится в начале текста, иначе `null`.
   */
  private fun resolveTrigger(text: String): String? {
    for (command: Command in commandList) {
      for (trigger: String in command.triggers) {

        // Не самое лучшее решение, т.к. если у какой-либо команды есть триггер, который начинается
        // так-же как триггер другой команды, то будет выполнена неподходящая команда.
        // TODO: Переписать.
        if (text.startsWith(trigger, ignoreCase = true)) return trigger
      }
    }

    return null
  }

  /**
   * Получает команду по её триггеру.
   *
   * @param trigger Триггер, по которому будет происходить поиск команды.
   *
   * @return Команда, если она обнаружена, иначе `null`.
   */
  private fun getCommand(trigger: String): Command? {
    for (command: Command in commandList) {
      for (commandAlias: String in command.triggers) {
        if (trigger == commandAlias) return command
      }
    }

    return null
  }
}
