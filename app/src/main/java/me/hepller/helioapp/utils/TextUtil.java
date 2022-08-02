package me.hepller.helioapp.utils;

import lombok.experimental.UtilityClass;
import me.hepller.helioapp.utils.config.ConfigWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Утилита для работы с текстом.
 *
 * @author hepller
 */
@UtilityClass
public class TextUtil {

  /**
   * Заполнитель для пустых строк.
   */
  public final String NULL_PLACEHOLDER = "<пусто>";

  /**
   * Получает emoji флаг страны.
   *
   * @param countryCode Код страны (2 символа).
   *
   * @return Emoji флаг страны.
   */
  public String getCountryFlagEmoji(@NotNull String countryCode) throws Exception {
    if (countryCode.length() > 2) throw new Exception("The \"countryCode\" parameter must not exceed a length of 2 characters");

    final int flagOffset = 0x1F1E6;
    final int asciiOffset = 0x41;

    final int firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset;
    final int secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset;

    return new String(Character.toChars(firstChar)) + new String(Character.toChars(secondChar));
  }

  /**
   * Заменяет пустые строки на заполнитель.
   *
   * @param string Строка.
   *
   * @return Заполнитель {@link TextUtil#NULL_PLACEHOLDER}, если строка пустая или равна "null", если нет - начальная строка.
   */
  public String replaceNullPlaceholder(@NotNull String string) {
    if (string.equals("") || string.equals("null")) return NULL_PLACEHOLDER;

    return string;
  }

  /**
   * Удаляет из массива строки содержащие заполнитель {@link TextUtil#NULL_PLACEHOLDER}.
   *
   * @param strings Массив строк.
   *
   * @return Массив строк не содержащих заполнитель.
   */
  public String[] filterStringsWithNullPlaceholder(String[] strings) {
    return Stream.of(strings).filter(string -> !string.contains(NULL_PLACEHOLDER)).toArray(String[]::new);
  }
}