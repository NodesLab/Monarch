package me.hepller.helioapp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Объект данных о сообщениях.
 */
object MessagesData {

  /**
   * Список всех сообщений.
   */
  var messageList: SnapshotStateList<Message> = mutableStateListOf()
}