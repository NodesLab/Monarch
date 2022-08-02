package me.hepller.helioapp.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.hepller.helioapp.message.MessageModal;
import me.hepller.helioapp.message.MessageRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Абстрактная команда.
 *
 * @author hepller
 */
@RequiredArgsConstructor
public abstract class Command {

  /**
   * Алиасы команды.
   */
  @Getter
  private final String[] aliases;

  /**
   * Описание команды.
   */
  @Getter
  private final String description;

  /**
   * Бета-статус команды.
   */
  @Setter
  @Getter
  private Boolean betaStatus = false;

  /**
   * Доступность команды только для администраторов.
   */
  @Setter
  @Getter
  private Boolean forAdmin = false;

  /**
   * Скрытая команда.
   */
  @Setter
  @Getter
  private Boolean hiddenCommand = false;

  /**
   * Выполняет функцию команды.
   *
   * @param vk Методы VK API.
   * @param message Объект сообщения.
   *
   * @throws Exception Возможная ошибка.
   */
  public abstract void execute(final @NotNull String message, final ArrayList<MessageModal> messageModalArrayList, final MessageRecyclerViewAdapter messageRecyclerViewAdapter, final String @NotNull [] arguments) throws Exception;
}
