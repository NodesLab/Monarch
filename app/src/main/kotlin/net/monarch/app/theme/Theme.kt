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

package net.monarch.app.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Цвета тёмной темы.
 *
 * @author hepller
 */
@SuppressLint("ConflictingOnColor")
private val DarkColorPalette: Colors = darkColors(
  primary = PrimaryDark,
  primaryVariant = PrimaryVariantDark,
  secondary = SecondaryDark,
  secondaryVariant = SecondaryVariantDark,
  background = BackgroundDark,
  surface = SurfaceDark,
  error = ErrorDark,
  onPrimary = OnPrimaryDark,
  onSecondary = OnSecondaryDark,
  onBackground = OnBackgroundDark,
  onSurface = OnSurfaceDark,
  onError = OnErrorDark
)

/**
 * Цвета светлой темы.
 *
 * @author hepller
 */
@SuppressLint("ConflictingOnColor")
private val LightColorPalette: Colors = lightColors(
  primary = PrimaryLight,
  primaryVariant = PrimaryVariantLight,
  secondary = SecondaryLight,
  secondaryVariant = SecondaryVariantLight,
  background = BackgroundLight,
  surface = SurfaceLight,
  error = ErrorLight,
  onPrimary = OnPrimaryLight,
  onSecondary = OnSecondaryLight,
  onBackground = OnBackgroundLight,
  onSurface = OnSurfaceLight,
  onError = OnErrorLight
)

/**
 * Тема приложения.
 *
 * @param darkTheme Включена ли тёмная тема.
 *
 * @author hepller
 */
@Composable
fun HelioTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors: Colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  SetSystemUiColor(colors = colors, darkTheme = darkTheme)

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}

/**
 * Перекрашивает панель состояния и панель навигации.
 *
 * @author hepller
 */
@Composable
fun SetSystemUiColor(colors: Colors, darkTheme: Boolean) {
  val systemUiController: SystemUiController = rememberSystemUiController()

  SideEffect {
    systemUiController.setStatusBarColor(
      color = colors.primary,
      darkIcons = !darkTheme
    )

    systemUiController.setNavigationBarColor(
      color = colors.primary,
      darkIcons = !darkTheme
    )
  }
}
