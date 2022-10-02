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

package net.helio.app.ui.model

import java.util.*

/**
 * Реализация сообщения.
 *
 * @param author Автор сообщения (app | user).
 * @param text Текст сообщения.
 * @param date Время создания сообщения.
 *
 * @author hepller
 */
data class MessageImpl(private val author: String, override val text: String, override val date: Date) : Message {

  override fun isFromApp(): Boolean {
    return author == "app"
  }

  override fun isFromUser(): Boolean {
    return author == "user"
  }
}