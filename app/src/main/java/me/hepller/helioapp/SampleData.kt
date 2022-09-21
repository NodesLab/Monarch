package me.hepller.helioapp

import androidx.compose.runtime.mutableStateListOf
import me.hepller.helioapp.data.Message

object SampleData {
  // Sample conversation data
  var conversationSample = mutableStateListOf<Message>(
    Message(
      "bot",
      "Test...Test...Test...",
      "00:00"
    ),
    Message(
      "bot",
      "List of Android versions:\n" +
        "Android KitKat (API 19)\n" +
        "Android Lollipop (API 21)\n" +
        "Android Marshmallow (API 23)\n" +
        "Android Nougat (API 24)\n" +
        "Android Oreo (API 26)\n" +
        "Android Pie (API 28)\n" +
        "Android 10 (API 29)\n" +
        "Android 11 (API 30)\n" +
        "Android 12 (API 31)\n",
      "00:00"
    ),
    Message(
      "bot",
      "I think Kotlin is my favorite programming language.\n" +
        "It's so much fun!",
      "00:00"
    ),
    Message(
      "bot",
      "Searching for alternatives to XML layouts...",
      "00:00"
    ),
    Message(
      "bot",
      "Hey, take a look at Jetpack Compose, it's great!\n" +
        "It's the Android's modern toolkit for building native UI." +
        "It simplifies and accelerates UI development on Android." +
        "Less code, powerful tools, and intuitive Kotlin APIs :)",
      "00:00"
    ),
    Message(
      "bot",
      "It's available from API 21+ :)",
      "00:00"
    ),
    Message(
      "bot",
      "Writing Kotlin for UI seems so natural, Compose where have you been all my life?",
      "00:00"
    ),
    Message(
      "bot",
      "Android Studio next version's name is Arctic Fox",
      "00:00"
    ),
    Message(
      "bot",
      "Android Studio Arctic Fox tooling for Compose is top notch ^_^",
      "00:00"
    ),
    Message(
      "bot",
      "I didn't know you can now run the emulator directly from Android Studio",
      "00:00"
    ),
    Message(
      "bot",
      "Compose Previews are great to check quickly how a composable layout looks like",
      "00:00"
    ),
    Message(
      "bot",
      "Previews are also interactive after enabling the experimental setting",
      "00:00"
    ),
    Message(
      "bot",
      "Have you tried writing build.gradle with KTS?",
      "00:00"
    )
  )
}