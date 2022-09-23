package me.hepller.helioapp.data

/**
 * Объект сообщения.
 *
 * todo: жесткое ограничение на имя автора (bot | user)
 */
data class Message(

  /**
   * Автор сообщения.
   */
  val author: String,

  /**
   * Текст сообщения.
   */
  val text: String,

  /**
   * Дата создания.
   */
  val time: String
)