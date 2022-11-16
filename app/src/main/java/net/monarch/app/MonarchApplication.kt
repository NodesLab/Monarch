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
import net.monarch.app.core.command.list.base.AliasesCommand
import net.monarch.app.core.command.list.base.CommandsCommand
import net.monarch.app.core.command.list.crypt.Base64Command
import net.monarch.app.core.command.list.network.IpInfoCommand
import net.monarch.app.core.command.list.network.PortCommand
import net.monarch.app.core.command.list.text.GenStrCommand
import net.monarch.app.core.command.list.text.HashCommand
import net.monarch.app.core.command.manager.CommandManagerImpl
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.message.model.payload.buttons.CommandButtonPayload

/**
 * Входная точка приложения.
 *
 * @author hepller
 */
class MonarchApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    // BASE.
    CommandManagerImpl.registerCommand(command = AliasesCommand)
    CommandManagerImpl.registerCommand(command = CommandsCommand)

    // CRYPT.
    CommandManagerImpl.registerCommand(command = Base64Command)

    // NETWORK.
    CommandManagerImpl.registerCommand(command = IpInfoCommand)
    CommandManagerImpl.registerCommand(command = PortCommand)

    // TEXT.
    CommandManagerImpl.registerCommand(command = GenStrCommand)
    CommandManagerImpl.registerCommand(command = HashCommand)

    MessageManagerImpl.appMessage(
      text = "⚙️ Monarch запущен, введите команду",
      payloadList = listOf(
        CommandButtonPayload(
          buttonLabel = "Список команд",
          buttonCommand = "/commands"
        )
      )
    )
  }
}
