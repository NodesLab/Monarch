/*
 * Copyright © 2022 The Monarch Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.monarch.app.ui.utility

import android.content.Context
import android.widget.Toast

/**
 * Утилита для отображения тостов.
 *
 * @author hepller
 */
object ToastUtility {

  /**
   * Отображает тост.
   *
   * @param text Текст тоста.
   * @param context Контекст.
   * @param duration Продолжительность (Toast.LENGTH_LONG | Toast.LENGTH_SHORT).
   */
  private fun makeToast(text: String, context: Context, duration: Int) {
    Toast.makeText(context, text, duration).show()
  }

  /**
   * Отображает долгосрочный тост.
   *
   * @param text Текст тоста.
   * @param context Контекст.
   */
  fun makeLongToast(text: String, context: Context) {
    makeToast(text = text,context = context, duration = Toast.LENGTH_LONG)
  }

  /**
   * Отображает короткосрочный тост.
   *
   * @param text Текст тоста.
   * @param context Контекст.
   */
  fun makeShortToast(text: String, context: Context) {
    makeToast(text = text,context = context, duration = Toast.LENGTH_SHORT)
  }
}