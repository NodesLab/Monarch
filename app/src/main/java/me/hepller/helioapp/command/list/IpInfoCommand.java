package me.hepller.helioapp.command.list;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import me.hepller.helioapp.command.Command;
import me.hepller.helioapp.message.MessageModal;
import me.hepller.helioapp.message.MessageSender;
import me.hepller.helioapp.utils.NetUtil;
import me.hepller.helioapp.utils.TextUtil;
import me.hepller.helioapp.utils.ValidateUtil;

import org.jetbrains.annotations.NotNull;

import java.net.IDN;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author hepller
 */
public final class IpInfoCommand extends Command {

  public IpInfoCommand() {
    super(new String[] {"ipinfo", "ip", "ип", "ипинфо", "айпи"}, "Информация об IP");
  }

  @Override
  @SneakyThrows
  public void execute(@NotNull String message, MessageSender messageSender, String @NotNull [] arguments) throws UnknownHostException {
    if (arguments.length < 2) {
      messageSender.sendBotMessage("⛔ Укажите IP / домен, о котором необходимо получить информацию");

      return;
    }

    String cleanedIp = NetUtil.extractCleanUrl(message);

    // Удаление порта у доменов и IPv4
    if (!ValidateUtil.isValidIPv6(cleanedIp)) cleanedIp = cleanedIp.split(":")[0];

    // Конвертация IP из десятеричного формата в IPv4 (+ удаление порта)
    if (ValidateUtil.isNumber(cleanedIp)) cleanedIp = NetUtil.longToIPv4(Long.parseLong(cleanedIp.split(":")[0]));

    if (!ValidateUtil.isValidDomain(cleanedIp) && !ValidateUtil.isValidIPv4(cleanedIp) && !ValidateUtil.isValidIPv6(cleanedIp) && !ValidateUtil.isValidDomain(IDN.toUnicode(cleanedIp))) {
      messageSender.sendBotMessage("⚠ Вы указали некорректный IP");

      return;
    }

    messageSender.sendBotMessage("⚙ Получение информации об IP ...");

    final IpInfoWrapper wrapper = NetUtil.readObject("http://ip-api.com/json/" + cleanedIp + "?lang=ru&fields=4259583", IpInfoWrapper.class);

    if (wrapper == null || !wrapper.getStatus().equals("success")) {
      messageSender.sendBotMessage("⚠ Не удалось получить информацию о данном IP");

      return;
    }

    final String[] messageScheme = {
      "⚙ Информация о " + cleanedIp + ":",
      "",
      "– Локация: " + String.format("%s, %s, %s %s", wrapper.getCountry().replace("Украина", "Россия"), wrapper.getRegionName(), wrapper.getCity(), TextUtil.getCountryFlagEmoji(wrapper.getCountryCode().replace("UA", "RU"))),
      "– Организация: " + wrapper.getOrg(),
      "– Провайдер: " + wrapper.getIsp(),
      "– AS: " + wrapper.getAs(),
      "– ASNAME: " + wrapper.getAsname(),
      "– ZIP: " + wrapper.getZip(),
      "– IP: " + wrapper.getIp(),
      "– PTR: " + (wrapper.getReverse().equals("") ? InetAddress.getByName(wrapper.getIp()).getCanonicalHostName() : wrapper.getReverse())
    };

    messageSender.sendBotMessage(String.join("\n", TextUtil.filterStringsWithNullPlaceholder(messageScheme)));
  }
}

/**
 * @author _Novit_ (novitpw)
 *
 * TODO: Задокументировать.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
final class IpInfoWrapper {

  String status;
  String country;
  String countryCode;
  String regionName;
  String city;
  String zip;

  float lat;
  float lon;

  String isp;
  String org;
  String as;
  String asname;
  String reverse;

  @SerializedName("query")
  String ip;
}