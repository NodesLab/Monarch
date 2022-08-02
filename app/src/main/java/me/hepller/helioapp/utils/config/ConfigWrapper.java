package me.hepller.helioapp.utils.config;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
//import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Обёртка для конфигурации.
 *
 * @author hepller
 */
@UtilityClass
public class ConfigWrapper {

  /**
   * Путь к файлу конфигурации.
   */
  private final String FILE_PATH = "config.yml";

  /**
   * YAML анализатор.
   */
//  private final YamlFile YAML = new YamlFile(FILE_PATH);

  /**
   * Сохраняет шаблон файла конфигурации (при его отсутствии) в текущую директорию.
   *
   * Также загружает конфигурацию из файла.
   */
  @SneakyThrows
  public void saveDefaultConfig() {
    final File file = new File(FILE_PATH);

    if (!file.exists() && !file.isDirectory()) {
      final InputStream inputStream = ConfigWrapper.class.getClassLoader().getResourceAsStream(FILE_PATH);
      final Path path = FileSystems.getDefault().getPath(FILE_PATH).toAbsolutePath();

      if (inputStream == null) return;

      Files.copy(inputStream, path);
    }

//    YAML.load();
  }

  /**
   * Получает токен доступа NumLookupAPI.
   *
   * @return Токен доступа NumLookupAPI.
   */
  public String getNumLookupAPIToken() {
//    return YAML.getString("apis.numlookupapi-token");
    return "";
  }
}