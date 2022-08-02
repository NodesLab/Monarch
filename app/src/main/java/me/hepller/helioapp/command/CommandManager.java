package me.hepller.helioapp.command;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.hepller.helioapp.command.list.*;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер команд.
 *
 * @author hepller
 */
@UtilityClass
public class CommandManager {

  /**
   * HashMap команд.
   */
  @Getter
  private final Map<String[], Command> commandMap = new ConcurrentHashMap<>();

  /**
   * Регистрирует команду.
   *
   * @param command Объект команды
   */
  public void registerCommand(final Command command) {
    commandMap.put(command.getAliases(), command);
  }

  /**
   * Регистрирует стандартные команды.
   */
  public void registerAllCommands() {
    registerCommand(new HelpCommand());
    registerCommand(new IpInfoCommand());
  }

  /**
   * Получает команду по алиасу.
   *
   * @param alias Алиас команды
   * @return Объект команды (если команда не обнаружена то {@code null})
   */
  public Command getCommand(final String alias) {
    for (final Command command : CommandManager.getCommandMap().values()) {
      if (Arrays.asList(command.getAliases()).contains(alias)) return command;
    }

    return null;
  }
}
