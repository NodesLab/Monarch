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
import com.squareup.moshi.Moshi
import net.helio.app.core.command.Command
import net.helio.app.core.command.session.CommandSession
import net.helio.app.ui.message.manager.MessageManagerImpl
import java.util.*

/**
 * @author hepller
 */
object IpInfoCommand : Command {
  override val aliases: List<String> = listOf("ipinfo", "ip", "ипинфо", "ип")
  override val description: String = "Получение информации об IP"

  override val isInBeta: Boolean = true
  override val isRequireNetwork: Boolean = true

  override fun execute(session: CommandSession) {
    val cleanedIp = "null"

    val testJson = "{\"status\":\"success\",\"country\":\"Австралия\",\"countryCode\":\"AU\",\"region\":\"QLD\",\"regionName\":\"Штат Квинсленд\",\"city\":\"South Brisbane\",\"zip\":\"4101\",\"lat\":-27.4766,\"lon\":153.0166,\"isp\":\"Cloudflare, Inc\",\"org\":\"APNIC and Cloudflare DNS Resolver project\",\"as\":\"AS13335 Cloudflare, Inc.\",\"asname\":\"CLOUDFLARENET\",\"reverse\":\"one.one.one.one\",\"query\":\"1.1.1.1\"}"

    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter<IpInfoApiAdapter>(IpInfoApiAdapter::class.java)

    val result = jsonAdapter.fromJson(testJson)

    val messageScheme = StringJoiner("\n")

    messageScheme.add("⚙️ Информация о ${result?.ip}:")
    messageScheme.add("")
    messageScheme.add("– Локация: null")
    messageScheme.add("– Организация: null")
    messageScheme.add("– Провайдер: null")
    messageScheme.add("– AS: null")
    messageScheme.add("– ASNAME: null")
    messageScheme.add("– ZIP: null")
    messageScheme.add("– IP: null")
    messageScheme.add("– PTR: null")

    MessageManagerImpl.botMessage(messageScheme.toString())
  }
}

@JsonClass(generateAdapter = true)
data class IpInfoApiAdapter(
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