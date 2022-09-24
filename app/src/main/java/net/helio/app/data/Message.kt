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

import androidx.compose.runtime.Immutable
import java.util.*

/**
 * Данные сообщения.
 *
 * @param author Автор сообщения (bot | user).
 * @param text Текст сообщения.
 * @param date Время создания сообщения.
 *
 * @author hepller
 */
@Immutable
data class Message(private val author: String, val text: String, val date: Date) {

  /**
   * Проверяет, является ли сообщение созданным ботом.
   *
   * @return `true`, если автор сообщения - бот.
   */
  fun isFromBot(): Boolean {
    return author == "bot"
  }

  /**
   * Проверяет, является ли сообщение созданным пользователем.
   *
   * @return `true`, если автор сообщения - пользователь.
   */
  fun isFromUser(): Boolean {
    return author == "user"
  }
}