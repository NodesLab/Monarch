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

package net.helio.app.data

import java.util.*

/**
 * Объект сообщения.
 *
 * @param author Автор сообщения (bot | user).
 * @param text Текст сообщения.
 * @param date Время создания сообщения.
 * @param isFromBot Является ли сообщение созданным ботом.
 * @param isFromUser Является ли сообщение созданным пользователем.
 *
 * @author hepller
 *
 * todo: жесткое ограничение на имя автора (bot | user)
 */
data class Message(
  private val author: String,
  val text: String,
  val date: Date,
  val isFromBot: Boolean = author == "bot",
  val isFromUser: Boolean = author == "user"
)