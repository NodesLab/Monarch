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

package net.helio.app.core.command.session

import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.message.model.payload.DropdownMessagePayload
import net.helio.app.core.message.model.payload.LinkMessagePayload

/**
 * Реализация сессии команды.
 *
 * @author hepller
 */
class CommandSessionImpl(override val arguments: List<String>) : CommandSession {

  override fun reply(text: String) {
    MessageManagerImpl.appMessage(text = text)
  }

  override fun dropdownMessage(text: String, dropdownLabel: String, dropdownText: String) {
    MessageManagerImpl.appMessage(text = text, payload = DropdownMessagePayload(dropdownLabel = dropdownLabel, dropdownText = dropdownText))
  }

  override fun replyWithLink(text: String, linkLabel: String, linkSource: String) {
    MessageManagerImpl.appMessage(text = text, payload = LinkMessagePayload(linkLabel = linkLabel, linkSource = linkSource))
  }
}