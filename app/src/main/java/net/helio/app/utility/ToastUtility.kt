/*
 * Copyright © 2022 The Helio contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.helio.app.utility

import android.content.Context
import android.widget.Toast

/**
 * Утилита для отображения тостов.
 *
 * @author hepller
 */
class ToastUtility {
  companion object {

    /**
     * Отображает тост.
     *
     * @param context Контекст.
     * @param text Текст тоста.
     * @param duration Продолжительность (Toast.LENGTH_LONG | Toast.LENGTH_SHORT).
     */
    private fun makeToast(context: Context, text: String, duration: Int) {
      Toast.makeText(context, text, duration).show()
    }

    /**
     * Отображает долгосрочный тост.
     *
     * @param context Контекст.
     * @param text Текст тоста.
     */
    fun makeLongToast(context: Context, text: String) {
      makeToast(context, text, Toast.LENGTH_LONG)
    }

    /**
     * Отображает короткосрочный тост.
     *
     * @param context Контекст.
     * @param text Текст тоста.
     */
    fun makeShortToast(context: Context, text: String) {
      makeToast(context, text, Toast.LENGTH_SHORT)
    }
  }
}