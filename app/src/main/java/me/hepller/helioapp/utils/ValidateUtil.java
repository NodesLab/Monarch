package me.hepller.helioapp.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Утилита для валидации различных типов.
 *
 * @author _Novit_ (novitpw)
 */
@UtilityClass
public final class ValidateUtil {

  /**
   * Проверяет, является ли строка числом.
   *
   *          No Number.isNaN() ?
   * ⠀⣞⢽⢪⢣⢣⢣⢫⡺⡵⣝⡮⣗⢷⢽⢽⢽⣮⡷⡽⣜⣜⢮⢺⣜⢷⢽⢝⡽⣝
   * ⠸⡸⠜⠕⠕⠁⢁⢇⢏⢽⢺⣪⡳⡝⣎⣏⢯⢞⡿⣟⣷⣳⢯⡷⣽⢽⢯⣳⣫⠇
   * ⠀⠀⢀⢀⢄⢬⢪⡪⡎⣆⡈⠚⠜⠕⠇⠗⠝⢕⢯⢫⣞⣯⣿⣻⡽⣏⢗⣗⠏
   * ⠀⠪⡪⡪⣪⢪⢺⢸⢢⢓⢆⢤⢀⠀⠀⠀⠀⠈⢊⢞⡾⣿⡯⣏⢮⠷⠁
   * ⠀⠀⠀⠈⠊⠆⡃⠕⢕⢇⢇⢇⢇⢇⢏⢎⢎⢆⢄⠀⢑⣽⣿⢝⠲⠉
   * ⠀⠀⠀⠀⠀⡿⠂⠠⠀⡇⢇⠕⢈⣀⠀⠁⠡⠣⡣⡫⣂⣿⠯⢪⠰⠂
   * ⠀⠀⠀⠀⡦⡙⡂⢀⢤⢣⠣⡈⣾⡃⠠⠄⠀⡄⢱⣌⣶⢏⢊⠂
   * ⠀⠀⠀⠀⢝⡲⣜⡮⡏⢎⢌⢂⠙⠢⠐⢀⢘⢵⣽⣿⡿⠁⠁
   * ⠀⠀⠀⠀⠨⣺⡺⡕⡕⡱⡑⡆⡕⡅⡕⡜⡼⢽⡻⠏
   * ⠀⠀⠀⠀⣼⣳⣫⣾⣵⣗⡵⡱⡡⢣⢑⢕⢜⢕⡝
   * ⠀⠀⠀⣴⣿⣾⣿⣿⣿⡿⡽⡑⢌⠪⡢⡣⣣⡟
   * ⠀⠀ ⡟⡾⣿⢿⢿⢵⣽⣾⣼⣘⢸⢸⣞⡟
   * ⠀⠀⠀⠁⠇⠡⠩⡫⢿⣝⡻⡮⣒⢽⠋
   *
   * @param string Строка для проверки.
   *
   * @return {@code false} если строка не является числом, {@code true} если является.
   */
  public boolean isNumber(final String string) {
    try {
      Integer.parseInt(string);

      return true;
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  /**
   * Проверяет, является ли строка Float.
   *
   * @param string Строка для проверки.
   *
   * @return {@code false} если строка не является числом, {@code true} если является.
   */
  public boolean isFloat(final String string) {
    try {
      Float.parseFloat(string);

      return true;
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  /**
   * Проверяет домен на корректность.
   *
   * @param domain Домен для проверки.
   *
   * @return {@code true}, если домен валидный и {@code false}, если нет.
   *
   * @see ValidateUtil#isValidAddress(String, String)
   *
   * @author hepller
   */
  public boolean isValidDomain(String domain) {
    return isValidAddress(domain, "^((?!-)[A-Za-zА-Яа-я0-9-]{1,63}(?<!-)\\.)+[A-Za-zА-Яа-я]{2,6}$");
  }

  /**
   * Проверяет IPv4-адрес на корректность.
   *
   * @param ip IPv4-адрес для проверки.
   *
   * @return {@code true}, если IPv4 валидный и {@code false}, если нет.
   *
   * @see ValidateUtil#isValidAddress(String, String)
   *
   * @author hepller
   */
  public boolean isValidIPv4(String ip) {
    return isValidAddress(ip, "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
  }

  /**
   * Проверяет IPv6-адрес на корректность.
   *
   * @param ip IPv6-адрес для проверки.
   *
   * @return {@code true}, если IP валидный и {@code false}, если нет.
   *
   * @see ValidateUtil#isValidAddress(String, String)
   *
   * @author hepller
   */
  public boolean isValidIPv6(String ip) {
    return isValidAddress(ip, "(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))");
  }

  /**
   * Проверяет имя хоста на корректность.
   *
   * @param hostname Имя хоста.
   * @param regex Регулярное выражение для проверки.
   *
   * @return {@code true}, если имя хоста корректно и {@code false}, если нет.
   *
   * @author hepller
   */
  private boolean isValidAddress(String hostname, String regex) {
    final Pattern pattern = Pattern.compile(regex);

    if (hostname == null) return true;

    final Matcher matcher = pattern.matcher(hostname);

    return matcher.matches();
  }
}