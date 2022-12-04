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

package net.monarch.commands.social

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.monarch.core.command.Command
import net.monarch.core.command.properties.CommandProperties
import net.monarch.core.command.properties.CommandPropertiesImpl
import net.monarch.core.command.session.CommandSession
import net.monarch.core.message.manager.MessageManagerImpl
import net.monarch.core.message.model.payload.buttons.LinkButtonPayload
import net.monarch.core.utility.MathUtility
import net.monarch.core.utility.NetworkUtility

/**
 * Команда для получения курса валют (относительно рубля).
 *
 * @author hepller
 */
object CurrencyCommand : Command {
  override val triggers: List<String> = listOf(
    "currency",
    "курс",
    "курс валют",
    "покажи курс",
    "покажи курс валют"
  )

  override val description: String = "Курс валют относительно рубля (ЦБР)"

  override val properties: CommandProperties = CommandPropertiesImpl(
    isInBeta = false,
    isRequireNetwork = true,
    isAnonymous = true
  )

  override suspend fun execute(session: CommandSession) {
    val response: CbrApiAdapter = NetworkUtility.readJsonHttp(url = "https://www.cbr-xml-daily.ru/daily_json.js", CbrApiAdapter::class.java)
      ?: return MessageManagerImpl.appMessage(text = "⚠️️ Не удалось получить информацию о курсе валют")

    // TODO: Брать напрямую с https://www.cbr.ru/scripts/XML_daily.asp (возможно при необходимости парсить в JSON).

    val usdCurrent: Float = MathUtility.roundFloat(response.currencies.usd.value)
    val usdPrevious: Float = MathUtility.roundFloat(response.currencies.usd.previous)

    val eurCurrent: Float = MathUtility.roundFloat(response.currencies.eur.value)
    val eurPrevious: Float = MathUtility.roundFloat(response.currencies.eur.previous)

    val messageScheme: MutableList<String> = mutableListOf()

    messageScheme.add(element = "\uD83D\uDCB8 Курс валют (на ${response.date}:")
    messageScheme.add(element = "")
    messageScheme.add(element = "– USD: $usdCurrent₽ (вчера: $usdPrevious₽)")
    messageScheme.add(element = "– EUR: $eurCurrent₽ (вчера: $eurPrevious₽)")

    MessageManagerImpl.appMessage(
      text = messageScheme.joinToString(separator = "\n"),
      payloadList = listOf(
        LinkButtonPayload(
          linkLabel = "Источник информации",
          linkSource = "https://www.cbr.ru"
        )
      )
    )
  }
}

/**
 * Адаптер для CBR API.
 *
 * Указаны только необходимые поля.
 *
 * @author hepller
 */
@JsonClass(generateAdapter = true)
class CbrApiAdapter(

  /**
   * Дата.
   */
  @Json(name = "Date")
  val date: String,

  /**
   * Массив валют.
   */
  @Json(name = "Valute")
  val currencies: Currencies
) {

  // Классы без @Keep удаляются ProGuard.

  /**
   * Класс, представляющий массив валют.
   */
  @Keep
  class Currencies(

    /**
     * Валюта USD (доллар).
     */
    @Json(name = "USD")
    val usd: CurrencyUnit,

    /**
     * Валюта EUR (евро).
     */
    @Json(name = "EUR")
    val eur: CurrencyUnit
  ) {

    /**
     * Класс, представляющий валюту.
     */
    @Keep
    class CurrencyUnit(

      /**
       * Текущее значение.
       */
      @Json(name = "Value")
      val value: Float,

      /**
       * Прошлое значение.
       */
      @Json(name = "Previous")
      val previous: Float
    )
  }
}
