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

package net.monarch.app.core.command.manager

import android.content.Context
import net.monarch.app.core.command.Command

/**
 * Интерфейс менеджера команд.
 *
 * @author hepller
 */
interface CommandManager {

  /**
   * Изменяемый список команд.
   */
  val commandList: MutableList<Command>

  /**
   * Регистрирует команду (добавляет в список команд).
   *
   * @param command Команда.
   */
  fun registerCommand(command: Command)

  /**
   * Получает команду по алиасу из списка.
   *
   * @param alias Алиас команды.
   */
  fun getCommand(alias: String): Command?

  /**
   * Обрабатывает ввод пользователя.
   *
   * @param input Введённый текст.
   * @param context Контекст.
   */
  fun handleInput(input: String, context: Context)
}