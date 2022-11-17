/*
 * Copyright © 2022 Node.
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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Отображает полосу прокрутки списка.
 *
 * @param state Состояние [LazyListState].
 * @param horizontal "true", для горизонтальной прокрутки и "false", для вертикальной прокрутки.
 * @param alignEnd Если "true", полоса прокрутки появится в "конце" прокручиваемого композитного
 * элемента, который она украшает. Если значение "false", полоса прокрутки появится в "начале"
 * прокручиваемого композитного элемента, который она украшает.
 * @param thickness Какой толщины / ширины должны быть направляющая и ручка.
 * @param fixedKnobRatio Если не `null`, ручка всегда будет иметь этот размер, пропорциональный
 * размеру дорожки. Используется для списков с непостоянным содержимым, чтобы ручка не колебалась по
 * размеру при прокрутке.
 * @param knobCornerRadius Радиус краёв ручки.
 * @param trackCornerRadius Радиус краёв направляющей.
 * @param knobColor Цвет ручки.
 * @param trackColor Цвет направляющей. Укажите [Color.Transparent], чтобы скрыть.
 * @param padding Заполнение краев, чтобы "сжать" начало / конец полосы прокрутки, чтобы она не была
 * на одном уровне с содержимым прокручиваемого композитного элемента, который она украшает.
 * @param visibleAlpha Альфа-значение, когда полоса прокрутки полностью скрыта.
 * @param hiddenAlpha Альфа-значение, когда полоса прокрутки полностью исчезает. Используйте число,
 * отличное от "0", чтобы полоса прокрутки никогда полностью не исчезала.
 * @param fadeInAnimationDurationMs Продолжительность анимации затухания, когда появляется полоса
 * прокрутки, как только пользователь начинает прокрутку.
 * @param fadeOutAnimationDurationMs Продолжительность анимации затухания, когда полоса прокрутки
 * исчезает после того, как пользователь закончил прокрутку.
 * @param fadeOutAnimationDelayMs Время ожидания после завершения прокрутки пользователем, прежде
 * чем полоса прокрутки начнет свою анимацию затухания.
 *
 * @author Warlax (https://stackoverflow.com/a/71932181).
 */
fun Modifier.scrollbar(
  state: LazyListState,
  horizontal: Boolean,
  alignEnd: Boolean = true,
  thickness: Dp = 4.dp,
  fixedKnobRatio: Float? = null,
  knobCornerRadius: Dp = 4.dp,
  trackCornerRadius: Dp = 2.dp,
  knobColor: Color = Color.Black,
  trackColor: Color = Color.White,
  padding: Dp = 0.dp,
  visibleAlpha: Float = 1f,
  hiddenAlpha: Float = 0f,
  fadeInAnimationDurationMs: Int = 150,
  fadeOutAnimationDurationMs: Int = 500,
  fadeOutAnimationDelayMs: Int = 1000,
): Modifier = composed {
  check(thickness > 0.dp) { "Thickness must be a positive integer." }
  check(fixedKnobRatio == null || fixedKnobRatio < 1f) { "A fixed knob ratio must be smaller than 1." }
  check(knobCornerRadius >= 0.dp) { "Knob corner radius must be greater than or equal to 0." }
  check(trackCornerRadius >= 0.dp) { "Track corner radius must be greater than or equal to 0." }
  check(hiddenAlpha <= visibleAlpha) { "Hidden alpha cannot be greater than visible alpha." }
  check(fadeInAnimationDurationMs >= 0) { "Fade in animation duration must be greater than or equal to 0." }
  check(fadeOutAnimationDurationMs >= 0) { "Fade out animation duration must be greater than or equal to 0." }
  check(fadeOutAnimationDelayMs >= 0) { "Fade out animation delay must be greater than or equal to 0." }

  val targetAlpha: Float =
    if (state.isScrollInProgress) visibleAlpha
    else hiddenAlpha

  val animationDurationMs: Int =
    if (state.isScrollInProgress) fadeInAnimationDurationMs
    else fadeOutAnimationDurationMs

  val animationDelayMs: Int =
    if (state.isScrollInProgress) 0
    else fadeOutAnimationDelayMs

  val alpha: Float by animateFloatAsState(
    targetValue = targetAlpha,
    animationSpec = tween(delayMillis = animationDelayMs, durationMillis = animationDurationMs)
  )

  drawWithContent {
    drawContent()

    state.layoutInfo.visibleItemsInfo.firstOrNull()?.let { firstVisibleItem ->
      if (state.isScrollInProgress || alpha > 0f) {

        // Размер видового экрана, весь размер прокручиваемого композитного элемента, который
        // украшается этой полосой прокрутки.
        val viewportSize: Float =
          if (horizontal) size.width
          else size.height
          - padding.toPx() * 2

        // Размер первого видимого элемента. Используется, чтобы оценить, сколько элементов
        // может поместиться в окне просмотра.
        val firstItemSize: Int = firstVisibleItem.size

        // *Предполагаемый* размер всего прокручиваемого составного элемента, как если бы все это
        // было на экране одновременно. Оценивается, потому что возможно, что размер первого
        // видимого элемента не соответствует размеру других элементов. Это приведет к увеличению и
        // уменьшению размера ручки полосы прокрутки по мере прокрутки, если размеры элементов
        // неодинаковы.
        val estimatedFullListSize: Int = firstItemSize * state.layoutInfo.totalItemsCount

        // Разница в положении между первыми пикселями, видимыми в нашем окне просмотра при
        // прокрутке, и верхней частью полностью заполненного прокручиваемого композитного элемента,
        // если бы он отображал все элементы сразу. Сначала значение равно 0, так как начинается с
        // самого верха (или начального края). По мере прокрутки вниз (или ближе к концу) это число
        // будет расти.
        val viewportOffsetInFullListSpace: Int = state.firstVisibleItemIndex * firstItemSize + state.firstVisibleItemScrollOffset

        // Место, где должна отображатся ручка в нашем компонуемом списке.
        val knobPosition: Float = (viewportSize / estimatedFullListSize) * viewportOffsetInFullListSpace + padding.toPx()

        // Какого размера должна быть ручка.
        val knobSize: Float = fixedKnobRatio?.let { it * viewportSize } ?: ((viewportSize * viewportSize) / estimatedFullListSize)

        // Отрисовка полосы.
        drawRoundRect(
          color = trackColor,
          topLeft = when {

            // Когда полоса прокрутки горизонтальна и выровнена по низу:
            horizontal && alignEnd -> Offset(padding.toPx(), size.height - thickness.toPx())

            // Когда полоса прокрутки горизонтальна и выровнена по верху:
            horizontal && !alignEnd -> Offset(padding.toPx(), 0f)

            // Когда полоса прокрутки вертикальна и выровнена до конца:
            alignEnd -> Offset(size.width - thickness.toPx(), padding.toPx())

            // Когда полоса прокрутки вертикальна и выровнена по началу:
            else -> Offset(0f, padding.toPx())
          },
          size =
            if (horizontal) Size(size.width - padding.toPx() * 2, thickness.toPx())
            else Size(thickness.toPx(), size.height - padding.toPx() * 2),
          alpha = alpha,
          cornerRadius = CornerRadius(x = trackCornerRadius.toPx(), y = trackCornerRadius.toPx()),
        )

        // Отрисовка ручки.
        drawRoundRect(
          color = knobColor,
          topLeft = when {

            // Когда полоса прокрутки горизонтальна и выровнена по низу:
            horizontal && alignEnd -> Offset(knobPosition, size.height - thickness.toPx())

            // Когда полоса прокрутки горизонтальна и выровнена по верху:
            horizontal && !alignEnd -> Offset(knobPosition, 0f)

            // Когда полоса прокрутки вертикальна и выровнена до конца:
            alignEnd -> Offset(size.width - thickness.toPx(), knobPosition)

            // Когда полоса прокрутки вертикальна и выровнена по началу:
            else -> Offset(0f, knobPosition)
          },
          size =
            if (horizontal) Size(knobSize, thickness.toPx())
            else Size(thickness.toPx(), knobSize),
          alpha = alpha,
          cornerRadius = CornerRadius(x = knobCornerRadius.toPx(), y = knobCornerRadius.toPx()),
        )
      }
    }
  }
}
