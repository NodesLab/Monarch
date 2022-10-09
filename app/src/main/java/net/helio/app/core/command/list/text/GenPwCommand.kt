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

package net.helio.app.core.command.list.text

import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.core.message.manager.MessageManagerImpl
import net.helio.app.core.utility.TextUtility
import java.security.SecureRandom
import java.util.*

/**
 * Команда для генерации пароля.
 *
 * @author hepller
 */
object GenPwCommand : Command {
  override val aliases: List<String> = listOf("genpw", "генпв")
  override val description: String = "Генерация надёжного пароля"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = false
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.size < 2) {
      MessageManagerImpl.appMessage(text = "⛔ Укажите длину генерируемого пароля")

      return
    }

    if (!TextUtility.isNumber(session.arguments[1])) {
      MessageManagerImpl.appMessage(text = "⚠️️ Длина генерируемого пароля должна быть числом")

      return
    }

    val length: Int = Integer.parseInt(session.arguments[1])

    if (length < 1) {
      MessageManagerImpl.appMessage(text = "⚠️️ Длина генерируемого пароля должна быть больше нуля")

      return
    }

    val generated: String = generateString(
      useLetters = true,
      useUppercaseLetters = true,
      useNumbers = true,
      useSpecialSymbols = true,
      length = length
    )

    val messageScheme = StringJoiner("\n")

    messageScheme.add("\uD83D\uDD11 Сгенерированный пароль:")
    messageScheme.add("")
    messageScheme.add(generated)

    MessageManagerImpl.appMessage(text = messageScheme.toString())
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