/*
 * Copyright © 2022 The Monarch Contributors.
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

package net.monarch.app.core.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.IOException
import java.net.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.pow


/**
 * Утилита для работы с сетью.
 *
 * @author hepller
 */
object NetworkUtility {

  private val VALID_DOMAIN_PATTERN: Pattern = Pattern.compile("^((?!-)[A-Za-zА-Яа-я0-9-]{1,63}(?<!-)\\.)+[A-Za-zА-Яа-я]{2,16}$")
  private val VALID_IPV4_PATTERN: Pattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")
  private val VALID_IPV6_PATTERN: Pattern = Pattern.compile("(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))")


  /**
   * Ktor-клиент.
   */
  private val ktorClient = HttpClient(CIO) {
    install(UserAgent) {
      agent = "Helio"
    }
  }

  /**
   * Moshi-клиент.
   */
  private val moshiClient = Moshi.Builder().build()

  /**
   * Получает ответ с URL-адреса.
   *
   * @return Ответ сервера.
   */
  private suspend fun requestHttp(url: String): String {
    val httpResponse: HttpResponse = ktorClient.get(url)

    return httpResponse.body()
  }

  /**
   * Получает JSON-объект с URL-адреса.
   *
   * @param url URL-адрес.
   * @param adapter JSON-адаптер.
   *
   * @return Сериализованный JSON-объект.
   */
  // TODO: Переписать на неблокирующий метод.
  @Suppress("BlockingMethodInNonBlockingContext")
  suspend fun <T> readJsonHttp(url: String, adapter: Class<T>): T? {
    val response: String = requestHttp(url)

    val jsonAdapter: JsonAdapter<T> = moshiClient.adapter(adapter)

    return jsonAdapter.fromJson(response)
  }

  /**
   * Очищает URL от протокола и разделов.
   *
   * @param url URl адрес.
   *
   * @return Очищенный URl адрес.
   */
  fun clearUrl(url: String): String {
    return url.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)".toRegex(), "").split("/").toTypedArray()[0]
  }

  /**
   * Проверяет домен на корректность.
   *
   * @param domain Домен для проверки.
   *
   * @return `true`, если домен валидный и `false`, если нет.
   *
   * @see NetworkUtility.isValidAddress
   */
  fun isValidDomain(domain: String?): Boolean {
    return isValidAddress(domain, VALID_DOMAIN_PATTERN)
  }

  /**
   * Проверяет IPv4-адрес на корректность.
   *
   * @param ip IPv4-адрес для проверки.
   *
   * @return `true`, если IPv4 валидный и `false`, если нет.
   *
   * @see NetworkUtility.isValidAddress
   */
  fun isValidIPv4(ip: String?): Boolean {
    return isValidAddress(ip, VALID_IPV4_PATTERN)
  }

  /**
   * Проверяет IPv6-адрес на корректность.
   *
   * @param ip IPv6-адрес для проверки.
   *
   * @return `true`, если IP валидный и `false`, если нет.
   *
   * @see NetworkUtility.isValidAddress
   */
  fun isValidIPv6(ip: String?): Boolean {
    return isValidAddress(ip, VALID_IPV6_PATTERN)
  }

  /**
   * Проверяет имя хоста на корректность.
   *
   * @param hostname Имя хоста.
   * @param pattern Регулярное выражение для проверки.
   *
   * @return `true`, если имя хоста корректно и `false`, если нет.
   */
  private fun isValidAddress(hostname: String?, pattern: Pattern): Boolean {
    if (hostname == null) return true

    val matcher: Matcher = pattern.matcher(hostname)

    return matcher.matches()
  }

  /**
   * Проверяет ссылку на корректность.
   *
   * @param url Ссылка.
   *
   * @return `true`, если ссылка корректна и `false`, если нет.
   */
  fun isValidURL(url: String?): Boolean {
    try {
      URL(url).toURI()
    } catch (exception: MalformedURLException) {
      return false
    } catch (exception: URISyntaxException) {
      return false
    }

    return true
  }

  /**
   * Конвертирует IPv4 в десятеричный формат.
   *
   * @param ipv4 IPv4 адрес.
   *
   * @return IP в десятеричном формате.
   */
  fun ipV4toLong(ipv4: String): Long {
    val ipAddressInArray: Array<String> = ipv4.split("\\.").toTypedArray()
    var decimalIp: Long = 0

    for (i in ipAddressInArray.indices) {
      val power: Int = 3 - i
      val intIp: Int = ipAddressInArray[i].toInt()

      decimalIp += (intIp * 256.0.pow(power.toDouble())).toLong()
    }

    return decimalIp
  }

  /**
   * Конвертирует десятеричный IP в IPv4.
   *
   * @param ip IP в десятеричном формате.
   *
   * @return IPv4 адрес.
   */
  fun longToIPv4(ip: Long): String {
    var varIp: Long = ip

    val ipv4 = StringBuilder(15)

    for (i in 0..3) {
      ipv4.insert(0, varIp and 0xff)

      if (i < 3) {
        ipv4.insert(0, '.')
      }

      varIp = varIp shr 8
    }
    return ipv4.toString()
  }

  /**
   * Проверяет наличие у устройства доступа к сети.
   *
   * @param context Контекст.
   */
  fun hasNetworkConnection(context: Context): Boolean {
    val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
      if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
        return true
      } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
        return true
      } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
        return true
      }
    }

    return false
  }

  /**
   * Проверяет порт на доступность.
   *
   * @param host Хост.
   * @param port Порт.
   * @param timeout Таймаут (по умолчанию: 2000).
   *
   * @return `true` если порт открыт, `false` если не открыт.
   */
  fun isPortAvailable(host: String?, port: Int, timeout: Int = 2000): Boolean {
    val socketAddress: SocketAddress = InetSocketAddress(host, port)

    try {
      Socket().use { socket ->
        socket.connect(socketAddress, timeout)
        socket.close()

        return true
      }
    } catch (exception: IOException) {
      return false
    }
  }
}