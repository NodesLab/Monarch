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

package net.monarch.app.ui.theme

import androidx.compose.ui.graphics.Color

// region Пояснения к использованию цветов.

// Accent - Цвет акцента.

// Primary<Type> - Для фонов меню, панелей и системного UI.
// * PrimaryVariant<Type> - Не используется.
// Secondary<Type> - Фон сообщения пользователя.
// SecondaryVariant<Type> - Фон сообщения бота.
// * Background<Type> - Не используется.
// Surface<Type> - Эффект тени для выдвижных меню.
// * Error<Type> - Не используется.
// OnPrimary<Type> - Цвет для основных текстов и иконок.
// OnSecondary<Type> - Цвет для дополнительных текстов и недоступных иконок.
// * OnBackground<Type> - Не используется.
// * OnSurface<Type> - Не используется.
// * OnError<Type> - Не используется.

// endregion

// region Цвета не зависящие от темы.

val Accent: Color = Color(0xFF42A5F5)
val Green: Color = Color(0xFF1FAD36)
val Red: Color = Color(0xFFDB2B39)
// TODO: val LightGray: Color = Color(0xFF888888)

// endregion

// region Тёмная тема.

val PrimaryDark: Color = Color.Black
/**/ val PrimaryVariantDark: Color = Color.Transparent
val SecondaryDark: Color = Color(0xFF3F3F3F)
val SecondaryVariantDark: Color = Color(0xFF1A1A1A)
/**/ val BackgroundDark: Color = Color.Transparent
val SurfaceDark: Color = Color(0xB31B1B1B)
/**/ val ErrorDark: Color = Color.Transparent
val OnPrimaryDark: Color = Color(0xFFFFFFFF)
val OnSecondaryDark: Color = Color(0xFF888888)
/**/ val OnBackgroundDark: Color = Color.Transparent
/**/ val OnSurfaceDark: Color = Color.Transparent
/**/ val OnErrorDark: Color = Color.Transparent

// endregion

// region Светлая тема.

val PrimaryLight: Color = Color(0xFFF5F5F5) // Color(0xFFF0F2F5)
/**/ val PrimaryVariantLight: Color = Color.Transparent
val SecondaryLight: Color = Color(0xFFE9E9E9)
val SecondaryVariantLight: Color = Color(0xFFF8F8F8)
/**/ val BackgroundLight: Color = Color.Transparent
val SurfaceLight: Color = Color(0xB3FFFFFF)
/**/ val ErrorLight: Color = Color.Transparent
val OnPrimaryLight: Color = Color.Black
val OnSecondaryLight: Color = Color(0xFF888888)
/**/ val OnBackgroundLight: Color = Color.Transparent
/**/ val OnSurfaceLight: Color = Color.Transparent
/**/ val OnErrorLight: Color = Color.Transparent

// endregion
