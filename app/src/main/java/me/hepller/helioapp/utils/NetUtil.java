package me.hepller.helioapp.utils;

import com.google.gson.JsonObject;
import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Optional;

/**
 * Утилита для работы с сетью.
 *
 * @author hepller
 */
@UtilityClass
public class NetUtil {

  /**
   * Получает JSON объект с указанного адреса.
   *
   * @param address Строка адреса.
   *
   * @return {@link Optional<JsonObject>}
   *
   * @author _Novit_ (novitpw)
   */
  public @NotNull Optional<JsonObject> readJsonObject(final String address) {
    try {
      final URL url = new URL(address);
      final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

      httpURLConnection.setRequestMethod("GET");
      httpURLConnection.setRequestProperty("User-Agent", "helio-bot");

      try (final InputStream inputStream = httpURLConnection.getInputStream(); final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
        return Optional.of(GsonUtil.fromJson(bufferedReader, JsonObject.class).getAsJsonObject());
      }

    } catch (final Throwable throwable) {
      return Optional.empty();
    }
  }

  /**
   * Получает объект с указанного адреса.
   *
   * @param address Строка адреса.
   * @param clazz Объект, который вернется.
   *
   * @return {@link Object}
   *
   *
   * @author _Novit_ (novitpw)
   */
  public @Nullable <T> T readObject(final String address, final Class<@NotNull T> clazz) {
    final Optional<JsonObject> readResponse = readJsonObject(address);

    if (readResponse.isEmpty()) {
      return null;
    }

    final JsonObject jsonObject = readResponse.get();

    return GsonUtil.getGson().fromJson(jsonObject, clazz);
  }

  /**
   * Очищает URL от протокола и разделов.
   *
   * @param url URl адрес.
   *
   * @return Очищенный URl адрес.
   */
  public String clearUrl(final @NotNull String url) {
    return url.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "").split("/")[0];
  }

  /**
   * Получает из текста первый URl адрес и очищает его от протокола и разделов.
   *
   * @param text Тест для получения URL.
   *
   * @return Очищенный URl адрес.
   *
   * TODO: Переписать на собственный метод вместо библиотеки.
   */
  public static String extractCleanUrl(final String text) {
    final UrlDetector urlDetector = new UrlDetector(text, UrlDetectorOptions.Default);
    final List<Url> urlList = urlDetector.detect();

    if (urlList.size() < 1) return "";

    return clearUrl(urlList.get(0).toString());
  }

  /**
   * Проверяет порт на доступность.
   *
   * @param host Хост.
   * @param port Порт.
   * @param timeout Таймаут.
   *
   * @return {@code true} если порт открыт, {@code false} если не открыт.
   */
  public boolean isPortAvailable(String host, int port, int timeout) {
    final SocketAddress socketAddress = new InetSocketAddress(host, port);
    final Socket socket = new Socket();

    try {
      socket.connect(socketAddress, timeout);
      socket.close();

      return true;
    } catch (IOException exception) {
      return false;
    }
  }

  /**
   * Проверяет порт на доступность (с таймаутом 2000мс).
   *
   * @see NetUtil#isPortAvailable(String, int, int)
   */
  public boolean isPortAvailable(String host, int port) {
    return isPortAvailable(host, port, 2000);
  }

  /**
   * Конвертирует IPv4 в десятеричный формат.
   *
   * @param ipv4 IPv4 адрес.
   *
   * @return IP в десятеричном формате.
   */
  public long IPv4ToLong(@NotNull String ipv4) {
    final String[] ipAddressInArray = ipv4.split("\\.");

    long decimalIp = 0;

    for (int i = 0; i < ipAddressInArray.length; i++) {
      final int power = 3 - i;
      final int intIp = Integer.parseInt(ipAddressInArray[i]);

      decimalIp += intIp * Math.pow(256, power);
    }

    return decimalIp;
  }

  /**
   * Конвертирует десятеричный IP в IPv4.
   *
   * @param ip IP в десятеричном формате.
   *
   * @return IPv4 адрес.
   */
  public @NotNull String longToIPv4(long ip) {
    final StringBuilder ipv4 = new StringBuilder(15);

    for (int i = 0; i < 4; i++) {
      ipv4.insert(0, ip & 0xff);

      if (i < 3) {
        ipv4.insert(0, '.');
      }

      ip = ip >> 8;
    }
    return ipv4.toString();
  }
}