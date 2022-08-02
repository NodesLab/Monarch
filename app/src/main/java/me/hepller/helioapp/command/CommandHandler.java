package me.hepller.helioapp.command;

import me.hepller.helioapp.message.MessageSender;

import org.jetbrains.annotations.NotNull;

/**
 * Обработчик команд.
 *
 * @author hepller
 */
public final class CommandHandler {

  private MessageSender messageSender;

  public CommandHandler(MessageSender messageSender) {
    this.messageSender = messageSender;
  }

  /**
   * Обрабатывает команды.
   *
   * @param message Объект сообщения.
   */
  public void handleCommand(final String message) {
    final String[] arguments = decomposeIntoArguments(message);
    final Command command = CommandManager.getCommand(arguments[0].toLowerCase());

    if (command == null) messageSender.viewBotMessage("⚠ Команда не обнаружена");

    executeCommand(message, arguments, command);
  }

  /**
   * Выполняет команду.
   *
   * @param command Объект команды.
   * @param inputMessage Объект входящего сообщения.
   * @param arguments Аргументы команды.
   */
  private void executeCommand(final String inputMessage, final String[] arguments, final Command command) {
    try {
      command.execute(inputMessage, messageSender, arguments);
    } catch (Exception exception) {
      System.out.println("error");
    }
  }

  /**
   * Раскладывает сообщение на массив аргументов.
   *
   * Разложение происходит по пробелам (каждое следующее слово = новый аргумент).
   *
   * @param message Объект сообщения.
   *
   * @return Массив аргументов.
   */
  private String @NotNull [] decomposeIntoArguments(final @NotNull String message) {
    return message.split("\\s+");
  }
}