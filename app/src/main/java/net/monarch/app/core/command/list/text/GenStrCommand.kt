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

package net.monarch.app.core.command.list.text

import net.monarch.app.core.command.Command
import net.monarch.app.core.command.properties.CommandProperties
import net.monarch.app.core.command.properties.CommandPropertiesImpl
import net.monarch.app.core.command.session.CommandSession
import net.monarch.app.core.message.manager.MessageManagerImpl
import net.monarch.app.core.utility.TextUtility
import java.security.SecureRandom

/**
 * Команда для генерации строки.
 *
 * @author hepller
 */
object GenStrCommand : Command {
  override val triggers: List<String> = listOf(
    "genstr",
    "генстр",
    "генерация строки",
    "сгенерируй строку",
    "генерация текста",
    "сгенерируй текст"
  )

  override val description: String = "Генерация строки"

  override val properties: CommandProperties = CommandPropertiesImpl(
    isInBeta = false,
    isRequireNetwork = false,
    isAnonymous = true
  )

  // TODO: Добавить автоввод аргументов по аналогии с Base64Command.
  override suspend fun execute(session: CommandSession) {
    if (session.arguments.isEmpty()) {
      return MessageManagerImpl.appMessage(text = "⛔ Вы не указали длину строки")
    }

    if (!TextUtility.isNumber(session.arguments[0])) {
      return MessageManagerImpl.appMessage(text = "⚠️️ Длина генерируемой строки должна быть числом")
    }

    val length: Int = Integer.parseInt(session.arguments[0])

    if (length < 1 || length > 9999) {
      return MessageManagerImpl.appMessage(text = "⚠️️ Длина генерируемой строки должна быть больше нуля и меньше 10 000")
    }

    val generated: String = generateString(
      useLetters = true,
      useUppercaseLetters = true,
      useNumbers = true,
      useSpecialSymbols = true,
      length = length
    )

    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "\uD83D\uDCC4 Сгенерированная строка:")
    messageScheme.add(element = "")
    messageScheme.add(element = generated)

    MessageManagerImpl.appMessage(text = messageScheme.joinToString(separator = "\n"))
  }

  /**
   * Буквы.
   */
  private const val letters: String = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя"

  /**
   * Заглавные буквы.
   */
  private const val uppercaseLetters: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

  /**
   * Цифры.
   */
  private const val numbers: String = "0123456789"

  /**
   * Специальные символы.
   */
  private const val specialSymbols: String = "@#=+!$%&?-_*\"'.,/`~№;:^(){}[]"

  /**
   * Генерирует случайную строку.
   *
   * @param useLetters Использование в строке букв.
   * @param useUppercaseLetters Использование в строке заглавных букв.
   * @param useNumbers Использование в строке цифр.
   * @param useSpecialSymbols Использование в строке специальных символов.
   *
   * @return Случайная строка.
   */
  private fun generateString(useLetters: Boolean, useUppercaseLetters: Boolean, useNumbers: Boolean, useSpecialSymbols: Boolean, length: Int): String {
    var result = ""

    if (useLetters) { result += this.letters }
    if (useUppercaseLetters) { result += this.uppercaseLetters }
    if (useNumbers) { result += this.numbers }
    if (useSpecialSymbols) { result += this.specialSymbols }

    val secureRandom: SecureRandom = SecureRandom.getInstance("SHA1PRNG")

    val stringBuilder = StringBuilder(length)

    var i = 0

    while (i < length) {
      val randomInt : Int = secureRandom.nextInt(result.length)

      stringBuilder.append(result[randomInt])

      i++
    }

    return stringBuilder.toString()
  }
}
