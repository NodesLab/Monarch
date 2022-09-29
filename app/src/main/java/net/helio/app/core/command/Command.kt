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

package net.helio.app.core.command

import net.helio.app.core.command.session.CommandSession

/**
 * Интерфейс команды.
 *
 * @author hepller
 */
interface Command {

  /**
   * Алиасы команды.
   */
  val aliases: List<String>

  /**
   * Описание команды.
   */
  val description: String

  /**
   * Бета-статус команды.
   */
  val isInBeta: Boolean

  /**
   * Требует ли команда доступ в сеть.
   */
  val isRequireNetwork: Boolean

  /**
   * Анонимна ли команда.
   * Под анонимностью подразумевается отсутствие прямых соединений с целевым сервером.
   */
  val isAnonymous: Boolean

  /**
   * Выполняет команду.
   *
   * @param session Сессия команды.
   *
   * @see CommandSession
   */
  suspend fun execute(session: CommandSession)
}