package me.hepller.helioapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
  primary = PrimaryDark,
  primaryVariant = PrimaryVariantDark,
  secondary = Secondary400,
  secondaryVariant = SecondaryVariant600,
  background = BackgroundDark,
  surface = BotMessageBackgroundDark
)

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

@Composable
fun HelioTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
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

  val systemUiController = rememberSystemUiController()

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