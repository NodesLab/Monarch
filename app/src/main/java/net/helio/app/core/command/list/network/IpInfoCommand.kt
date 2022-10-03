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

package net.helio.app.core.command.list.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.core.utility.NetworkUtility
import net.helio.app.core.utility.TextUtility
import java.net.IDN
import java.net.InetAddress
import java.util.*

/**
 * Команда для получения информации об IP.
 *
 * @author hepller
 */
@Suppress("BlockingMethodInNonBlockingContext")
object IpInfoCommand : Command {
  override val aliases: List<String> = listOf("ipinfo", "ip", "ипинфо", "ип")
  override val description: String = "Получение информации об IP"

  override val isInBeta: Boolean = false
  override val isRequireNetwork: Boolean = true
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.size < 2) {
      session.reply(text = "⛔ Укажите IP-адрес, о котором необходимо найти информацию")

      return
    }

    var cleanedIp = NetworkUtility.clearUrl(url = session.arguments[1])

    // Удаление порта у доменов и IPv4
    if (!NetworkUtility.isValidIPv6(ip = cleanedIp)) cleanedIp = cleanedIp.split(":")[0]

    // Конвертация IP из десятеричного формата в IPv4 (+ удаление порта)
    if (TextUtility.isNumber(string = cleanedIp)) cleanedIp = NetworkUtility.longToIPv4(ip = cleanedIp.split(":")[0].toLong())

    if (!NetworkUtility.isValidDomain(domain = cleanedIp) && !NetworkUtility.isValidIPv4(ip = cleanedIp) && !NetworkUtility.isValidIPv6(ip = cleanedIp) && !NetworkUtility.isValidDomain(IDN.toUnicode(cleanedIp))) {
      session.reply(text = "⚠️️ Вы указали / переслали некорректный IP")

      return
    }

    session.reply(text = "⚙️ Получение информации об IP ...")

    val response: IpApiAdapter? = NetworkUtility.readJsonHttp(url = "http://ip-api.com/json/${IDN.toASCII(cleanedIp)}?lang=ru&fields=4259583", IpApiAdapter::class.java)

    if (response?.status != "success") {
      session.reply(text = "⚠️️ Не удалось получить информацию об этом IP (отсутствие информации со стороны API)")

      return
    }

    val ptr: String? =
      if (response.reverse == "") InetAddress.getByName(response.ip).canonicalHostName
      else response.reverse

    val messageScheme = StringJoiner("\n")

    messageScheme.add("⚙️ Информация о $cleanedIp:")
    messageScheme.add("")
    messageScheme.add("– Локация: ${response.country}, ${response.regionName}, ${response.city} ${response.countryCode.let { TextUtility.getCountryFlagEmoji(it) }}")
    messageScheme.add("– Организация: ${response.org}")
    messageScheme.add("– Провайдер: ${response.org}")

    if (response.asCode.isNotEmpty()) messageScheme.add("– AS: ${response.asCode}")
    if (response.asName.isNotEmpty()) messageScheme.add("– ASNAME: ${response.asName}")
    if (response.zip.isNotEmpty()) messageScheme.add("– ZIP: ${response.zip}")

    messageScheme.add("– IP: ${response.ip}")

    if (response.ip != ptr) messageScheme.add("– PTR: $ptr")

    session.reply(text = messageScheme.toString())
  }
}

/**
 * Адаптер для ip-api.
 *
 * @author hepller
 */
@JsonClass(generateAdapter = true)
data class IpApiAdapter(

  /**
   * Статус полученных данных (успех/ошибка).
   */
  val status: String,

  /**
   * Страна.
   */
  val country: String,

  /**
   * Код страны.
   */
  val countryCode: String,

  /**
   * Имя региона.
   */
  val regionName: String,

  /**
   * Город.
   */
  val city: String,

  /**
   * ZIP-код.
   */
  val zip: String,

  /**
   * Местоположение (latitude).
   */
  val lat: Float,

  /**
   * Местоположение (longitude).
   */
  val lon: Float,

  /**
   * Провайдер.
   */
  val isp: String,

  /**
   * Организация.
   */
  val org: String,

  /**
   * AS-код организации.
   */
  @Json(name = "as") val asCode: String,

  /**
   * AS-имя организации.
   */
  @Json(name = "asname") val asName: String,

  /**
   * PTR.
   */
  val reverse: String,

  /**
   * IP который был запрошен.
   */
  @Json(name = "query") val ip: String
)