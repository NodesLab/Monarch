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

package net.helio.app.core.message.model

import net.helio.app.core.message.payload.MessagePayload
import java.util.*

/**
 * Интерфейс модели сообщения.
 *
 * @author hepller
 */
interface Message {

  /**
   * Текст сообщения.
   */
  val text: String

  /**
   * Дата создания сообщения.
   */
  val date: Date

  /**
   * Список полезной нагрузки сообщения.
   */
  val payloadList: List<MessagePayload>

  /**
   * Проверяет, является ли сообщение созданным приложением.
   *
   * @return `true`, если автор сообщения - приложение.
   */
  fun isFromApp(): Boolean

  /**
   * Проверяет, является ли сообщение созданным пользователем.
   *
   * @return `true`, если автор сообщения - пользователь.
   */
  fun isFromUser(): Boolean
}