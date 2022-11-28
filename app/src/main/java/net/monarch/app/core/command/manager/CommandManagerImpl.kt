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

package net.monarch.app.core.command.manager

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.monarch.app.core.command.Command
import net.monarch.app.core.command.session.CommandSession
import net.monarch.app.core.command.session.CommandSessionImpl
import net.monarch.app.core.command.session.properties.CommandSessionProperties
import net.monarch.app.core.command.session.properties.CommandSessionPropertiesImpl
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.payload.MessagePayload
import net.monarch.app.core.message.model.payload.buttons.CommandButtonPayload
import net.monarch.app.core.message.model.payload.buttons.DropdownButtonPayload
import net.monarch.app.core.message.model.payload.buttons.color.ButtonColor
import net.monarch.app.core.utility.NetworkUtility
import net.monarch.app.core.utility.TextUtility
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

    val trigger: String =
      if (formattedInput.startsWith("/")) "/"
      else getNlAlias(formattedInput)

    val command: Command? =
      if (trigger == "/") getCommand(alias = formattedInput.substring(startIndex = 1).lowercase().split(" ")[0])
      else getCommandFromNL(trigger.lowercase())

    if (command == null) {
      return unknownCommandMessage(input = formattedInput)
    }

    val isNetworkAvailable: Boolean = NetworkUtility.hasNetworkConnection(context = context)

    if (command.isRequireNetwork && !isNetworkAvailable) {
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
      arguments = formattedInput.replaceFirst(trigger, "").split(" "), // formattedInput.substring(startIndex = 1).split(" "),
      properties = sessionProperties,
      context = context
    )

    GlobalScope.launch {
      val coroutineContext: CoroutineContext = getDispatcherFromCurrentThread(scope = this)

      executeCommand(command = command, session = commandSession, context = coroutineContext)
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
   * @param input Ввод пользователя.
   */
  private fun unknownCommandMessage(input: String) {
    val messageScheme = StringJoiner("\n")

    messageScheme.add("⚠️ Команда не распознана")
    messageScheme.add("")

    val inputArgs: List<String> = input.substring(startIndex = 1).split(" ")

    val buttonsList: MutableList<MessagePayload> = mutableListOf(
      CommandButtonPayload(
        buttonLabel = "Список команд",
        buttonCommand = "/commands"
      )
    )

    val similarAliases: List<List<String>> = getSimilarCommandAliases(commandList = commandList, input = "/${inputArgs[0]}", distance = 0.4)

    if (similarAliases.isNotEmpty()) {
      val mutableSimilarList: MutableList<String> = mutableListOf()

      for (similarItem: List<String> in similarAliases) {
        mutableSimilarList.add("– /${similarItem[0]} (${similarItem[1]})")
      }

      messageScheme.add("Возможно вы хотели ввести один из следующих алиасов:")
      messageScheme.add("")
      messageScheme.add(mutableSimilarList.joinToString("\n"))
      messageScheme.add("")

      buttonsList.add(
        CommandButtonPayload(
          buttonLabel = "Выполнить команду №1",
          buttonCommand = "/${similarAliases[0][0]} ${inputArgs.drop(n = 1).joinToString(separator = " ")}",
          buttonColor = ButtonColor.SECONDARY
        )
      )
    }

    MessageManagerImpl.appMessage(
      text = messageScheme.toString(),
      payloadList = buttonsList
    )
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
   * Получает алиас на натуральном языке из текста.
   *
   * @param text Текст, из которого будет получатся алиас.
   */
  private fun getNlAlias(text: String): String {
    for (command in commandList) {
      for (nlAlias in command.nlAliases) {
        if (text.startsWith(nlAlias)) return text.replaceAfter(nlAlias, "")

        if (TextUtility.getStringSimilarity(nlAlias, text.replaceAfter(nlAlias, "")) > 0.6F) return text.replaceAfter(nlAlias, "")
      }
    }

    return ""
  }

  /**
   * Получает команду из списка по алиасу на натуральном языке.
   *
   * @param alias Алиас на натуральном языке.
   */
  private fun getCommandFromNL(alias: String): Command? {
    for (command in commandList) {
      for (commandAlias in command.nlAliases) {
        if (TextUtility.getStringSimilarity(commandAlias, alias) > 0.8F) return command
      }
    }

    return null
  }

  /**
   * Получает команду по алиасу из списка.
   *
   * @param alias Алиас команды.
   */
  private fun getCommand(alias: String): Command? {
    return commandList.find {
      it.aliases.contains(element = alias)
    }
  }
}
