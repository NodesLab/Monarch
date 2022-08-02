package me.hepller.helioapp.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Модальное окно сообщения.
 */
public final class MessageModal {

  /**
   * Текст сообщения.
   */
  @Getter
  @Setter
  private String messageText;

  /**
   * Отправитель сообщения.
   */
  @Getter
  @Setter
  private String sender;

  /**
   * Конструктор сообщения.
   *
   * @param messageText Текст сообщения.
   * @param sender Отправитель сообщения.
   */
  public MessageModal(String messageText, String sender) {
    this.messageText = messageText;
    this.sender = sender;
  }
}
