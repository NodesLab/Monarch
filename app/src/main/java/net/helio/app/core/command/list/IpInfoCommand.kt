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

package net.helio.app.core.command.list

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.ui.message.manager.MessageManagerImpl
import net.helio.app.utility.NetworkUtility
import net.helio.app.utility.TextUtility
import net.helio.app.utility.ValidateUtility
import java.net.IDN
import java.net.InetAddress
import java.util.*

/**
 * Команда для получения информации об IP.
 *
 * @author hepller
 */
object IpInfoCommand : Command {
  override val aliases: List<String> = listOf("ipinfo", "ip", "ипинфо", "ип")
  override val description: String = "Получение информации об IP"

  override val isInBeta: Boolean = true
  override val isRequireNetwork: Boolean = true
  override val isAnonymous: Boolean = true

  override suspend fun execute(session: CommandSession) {
    if (session.arguments.size < 2) {
      MessageManagerImpl.botMessage("⛔ Укажите IP-адрес, о котором необходимо найти информацию")

      return
    }

    var cleanedIp = NetworkUtility.clearUrl(session.arguments[1])

    // Удаление порта у доменов и IPv4
    if (!NetworkUtility.isValidIPv6(cleanedIp)) cleanedIp = cleanedIp.split(":")[0]

    // Конвертация IP из десятеричного формата в IPv4 (+ удаление порта)
    if (ValidateUtility.isNumber(cleanedIp)) cleanedIp = NetworkUtility.longToIPv4(cleanedIp.split(":")[0].toLong())

    if (!NetworkUtility.isValidDomain(cleanedIp) && !NetworkUtility.isValidIPv4(cleanedIp) && !NetworkUtility.isValidIPv6(cleanedIp) && !NetworkUtility.isValidDomain(IDN.toUnicode(cleanedIp))) {
      MessageManagerImpl.botMessage("⚠️️ Вы указали / переслали некорректный IP")

      return
    }

    MessageManagerImpl.botMessage("⚙️ Получаю информацию о данном IP ...")

    val response: IpApiAdapter? = NetworkUtility.readJsonHttp("http://ip-api.com/json/${IDN.toASCII(cleanedIp)}?lang=ru&fields=4259583", IpApiAdapter::class.java)

    val ptr: String? =
      if (response?.reverse.equals("")) InetAddress.getByName(response?.ip).canonicalHostName
      else response?.reverse

    val messageScheme = StringJoiner("\n")

    messageScheme.add("⚙️ Информация о $cleanedIp:")
    messageScheme.add("")
    messageScheme.add("– Локация: ${response?.country}, ${response?.regionName}, ${response?.city} ${response?.countryCode?.let { TextUtility.getCountryFlagEmoji(it) }}")
    messageScheme.add("– Организация: ${response?.org}")
    messageScheme.add("– Провайдер: ${response?.org}")

    if (response?.asCode?.isNotEmpty() == true) messageScheme.add("– AS: ${response.asCode}")
    if (response?.asName?.isNotEmpty() == true) messageScheme.add("– ASNAME: ${response.asName}")
    if (response?.zip?.isNotEmpty() == true) messageScheme.add("– ZIP: ${response.zip}")

    messageScheme.add("– IP: ${response?.ip}")

    if (!response?.ip.equals(ptr)) messageScheme.add("– PTR: $ptr")

    MessageManagerImpl.botMessage(messageScheme.toString())
  }
}

@JsonClass(generateAdapter = true)
data class IpApiAdapter(
  val status: String,
  val country: String,
  val countryCode: String,
  val regionName: String,
  val city: String,
  val zip: String,
  val lat: Float,
  val lon: Float,
  val isp: String,
  val org: String,
  @Json(name = "as") val asCode: String,
  @Json(name = "asname") val asName: String,
  val reverse: String,
  @Json(name = "query") val ip: String
)