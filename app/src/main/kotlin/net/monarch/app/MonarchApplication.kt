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

package net.monarch.app

import android.app.Application
import net.monarch.commands.base.CommandsCommand
import net.monarch.commands.base.TriggersCommand
import net.monarch.commands.crypt.Base64Command
import net.monarch.commands.crypt.HashCommand
import net.monarch.commands.network.IpInfoCommand
import net.monarch.commands.network.PortCommand
import net.monarch.commands.social.CurrencyCommand
import net.monarch.commands.text.GenStrCommand
import net.monarch.core.command.manager.CommandManagerImpl
import net.monarch.core.message.manager.MessageManagerImpl
import net.monarch.core.message.model.payload.buttons.CommandButtonPayload

/**
 * Входная точка приложения.
 *
 * @author hepller
 */
class MonarchApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    // region BASE.

    CommandManagerImpl.registerCommand(command = CommandsCommand)
    CommandManagerImpl.registerCommand(command = TriggersCommand)

    // endregion

    // region CRYPT.

    CommandManagerImpl.registerCommand(command = Base64Command)
    CommandManagerImpl.registerCommand(command = HashCommand)

    // endregion

    // region NETWORK.

    CommandManagerImpl.registerCommand(command = IpInfoCommand)
    CommandManagerImpl.registerCommand(command = PortCommand)

    // endregion

    // region SOCIAL.

    CommandManagerImpl.registerCommand(command = CurrencyCommand)

    // endregion

    // region TEXT.

    CommandManagerImpl.registerCommand(command = GenStrCommand)

    // endregion

    MessageManagerImpl.appMessage(
      text = "⚙️ Monarch запущен, введите команду",
      payloadList = listOf(
        CommandButtonPayload(
          buttonLabel = "Список команд",
          buttonCommand = "Список команд"
        )
      )
    )

    // TODO: Проверка на наличие обновлений.

//    MessageManagerImpl.appMessage(
//      text = "default | <format:accent> accent <format:reset> | <format:gray> gray <format:reset> | <format:accent> accent2 <format:reset> "
//    )
  }
}
