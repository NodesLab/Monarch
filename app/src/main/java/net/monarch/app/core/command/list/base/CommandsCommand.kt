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
import net.monarch.app.core.command.properties.CommandProperties
import net.monarch.app.core.command.properties.CommandPropertiesImpl
import net.monarch.app.core.command.session.CommandSession
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.payload.buttons.CommandButtonPayload

/**
 * Команда для просмотра доступных команд.
 *
 * @author hepller
 */
object CommandsCommand : Command {
  override val triggers: List<String> = listOf(
    "commands",
    "cmds",
    "help",
    "команды",
    "помощь",
    "список команд",
    "покажи команды"
  )

//  override val triggers: List<String> = TextUtility.generateStringsFromRegEx(pattern = "((список|покажи) (команды|команд)|помощь|c(om)?m(an)?ds|help|команды)")

  override val description: String = "Информация о доступных командах"

  override val properties: CommandProperties = CommandPropertiesImpl(
    isInBeta = false,
    isRequireNetwork = false,
    isAnonymous = true
  )

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

//    // TEST
//    val intent = Intent(Intent.ACTION_MAIN)
//    intent.component = ComponentName("net.monarch.app", "net.monarch.app.ui.main.MainActivity")
//    startActivity(session.context, intent, null)

    MessageManagerImpl.appMessage(
      text = messageScheme.joinToString(separator = "\n"),
      payloadList = listOf(
        CommandButtonPayload(
          buttonLabel = "Триггеры команд",
          buttonCommand = "Триггеры команд"
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

    for (command: Command in CommandManagerImpl.commandList) {
      val betaStatus: String = if (command.properties.isInBeta) "ᵇᵉᵗᵃ" else ""
      val nonAnonymous: String = if (!command.properties.isAnonymous) "*" else ""
      val requireInternet: String = if (command.properties.isRequireNetwork) "^" else ""

      output.add(element = "${command.triggers[0]}$requireInternet$nonAnonymous — ${command.description} $betaStatus".trim())
    }

    return output
  }
}
