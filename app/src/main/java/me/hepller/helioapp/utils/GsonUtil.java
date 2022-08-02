package me.hepller.helioapp.utils;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;

/**
 * Утилита для работы с GSON.
 *
 * @author _Novit_ (novitpw)
 *
 * TODO: Задокументировать.
 */
@UtilityClass
public class GsonUtil {
  private final Gson GSON;

  public <T>T fromJson(@NotNull String json, @NotNull Class<T> classOfT) {
    return GSON.fromJson(json, classOfT);
  }

  public <T>T fromJson(@NotNull Reader reader, @NotNull Class<T> classOfT) {
    return GSON.fromJson(reader, classOfT);
  }

  public @NotNull Gson getGson() {
    return GSON;
  }

  static {
    GSON = new Gson();
  }
}