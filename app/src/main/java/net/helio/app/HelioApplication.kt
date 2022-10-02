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

package net.helio.app

import android.app.Application
import net.helio.app.core.command.list.HelpCommand
import net.helio.app.core.command.list.IpInfoCommand
import net.helio.app.core.command.manager.CommandManagerImpl
import net.helio.app.ui.message.manager.MessageManagerImpl

/**
 * Входная точка приложения.
 *
 * @author hepller
 */
class HelioApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    CommandManagerImpl.registerCommand(HelpCommand)
    CommandManagerImpl.registerCommand(IpInfoCommand)

    MessageManagerImpl.appMessage("⚙️ Helio запущен, введите команду")
  }
}