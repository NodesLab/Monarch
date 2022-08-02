package me.hepller.helioapp.command;

import lombok.experimental.UtilityClass;
import me.hepller.helioapp.message.MessageRecyclerViewAdapter;
import me.hepller.helioapp.utils.config.ConfigWrapper;
import me.hepller.helioapp.message.MessageModal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Обработчик команд.
 *
 * @author hepller
 */
@UtilityClass
public final class CommandHandler {

  /**
   * Обрабатывает команды.
   *
   * @param message Объект сообщения.
   */
  public void handleCommand(final String message, final ArrayList<MessageModal> messageModalArrayList, final MessageRecyclerViewAdapter messageRecyclerViewAdapter) {
    final String[] arguments = decomposeIntoArguments(message);
    final Command command = CommandManager.getCommand(arguments[0].toLowerCase());

    if (command == null) messageModalArrayList.add(new MessageModal("⚠ Команда не обнаружена, для просмотра списка команд введите \"help\"", "bot"));

    executeCommand(message, messageModalArrayList, messageRecyclerViewAdapter, arguments, command);
  }

  /**
   * Выполняет команду.
   *
   * @param command Объект команды
   * @param inputMessage Объект входящего сообщения
   * @param arguments Аргументы команды
   */
  private void executeCommand(final String inputMessage, final ArrayList<MessageModal> messageModalArrayList, final MessageRecyclerViewAdapter messageRecyclerViewAdapter, final String[] arguments, final Command command) {
    try {
      command.execute(inputMessage, messageModalArrayList, messageRecyclerViewAdapter, arguments);
    } catch (Exception exception) {
      System.out.println("error");
    }
  }

  /**
   * Раскладывает сообщение на массив аргументов.
   *
   * Разложение происходит по пробелам (каждое следующее слово = новый аргумент).
   *
   * @param message Объект сообщения
   * @return Массив аргументов
   */
  private String @NotNull [] decomposeIntoArguments(final @NotNull String message) {
    return message.split("\\s+");
  }
}