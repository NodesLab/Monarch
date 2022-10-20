/*
 * Copyright © 2022 The Monarch Contributors.
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
import net.monarch.app.core.command.list.network.IpInfoCommand
import net.monarch.app.core.command.list.network.PortCommand
import net.monarch.app.core.command.list.text.Base64Command
import net.monarch.app.core.command.list.text.GenStrCommand
import net.monarch.app.core.command.list.text.HashCommand
import net.monarch.app.core.command.manager.CommandManagerImpl
import net.monarch.app.core.message.manager.MessageManagerImpl

/**
 * Входная точка приложения.
 *
 * @author hepller
 */
class MonarchApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    // base
    CommandManagerImpl.registerCommand(AliasesCommand)
    CommandManagerImpl.registerCommand(CommandsCommand)

    // network
    CommandManagerImpl.registerCommand(IpInfoCommand)
    CommandManagerImpl.registerCommand(PortCommand)

    // text
    CommandManagerImpl.registerCommand(Base64Command)
    CommandManagerImpl.registerCommand(GenStrCommand)
    CommandManagerImpl.registerCommand(HashCommand)

    MessageManagerImpl.appMessage(text = "⚙️ Monarch запущен, введите команду")
  }
}