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

package net.helio.app.ui.theme

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
private val DarkColorPalette = darkColors(
  primary = PrimaryDark,
  primaryVariant = PrimaryVariantDark,
  secondary = Secondary400,
  secondaryVariant = SecondaryVariant600,
  background = BackgroundDark,
  surface = BotMessageBackgroundDark
)

/**
 * Цвета светлой темы.
 *
 * @author hepller
 */
private val LightColorPalette = lightColors(
  primary = PrimaryLight,
  primaryVariant = PrimaryVariantLight,
  secondary = Secondary400,
  secondaryVariant = SecondaryVariant600,
  background = backgroundLight,
  surface = botMessageBackgroundLight
)

//private val DarkColorPalette = darkColors(
//  primary = Purple200,
//  primaryVariant = Purple700,
//  secondary = Teal200
//)

//private val LightColorPalette = lightColors(
//  primary = Purple500,
//  primaryVariant = Purple700,
//  secondary = Teal200
//
//  /* Other default colors to override
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//    */
//)

/**
 * Тема приложения.
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

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )

  val systemUiController: SystemUiController = rememberSystemUiController()

  SideEffect {
    if (darkTheme) {
      systemUiController.setStatusBarColor(
        color = PrimaryVariantDark,
        darkIcons = false
      )

      systemUiController.setNavigationBarColor(
        color = PrimaryVariantDark,
        darkIcons = false
      )
    } else {
      systemUiController.setStatusBarColor(
        color = PrimaryVariantLight,
        darkIcons = true
      )

      systemUiController.setNavigationBarColor(
        color = PrimaryVariantLight,
        darkIcons = true
      )
    }
  }
}