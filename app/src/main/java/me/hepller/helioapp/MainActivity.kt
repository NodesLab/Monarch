package me.hepller.helioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.hepller.helioapp.ui.scaffold.AppScaffold
import me.hepller.helioapp.ui.theme.HelioTheme
import me.hepller.helioapp.utility.MessageUtility

/**
 * Главный активити.
 */
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      HelioTheme {
        MessageUtility.addBotMessage("test")

        AppScaffold()
      }
    }
  }
}