/*
 * Copyright © 2022 The Helio contributors.
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

import java.io.ByteArrayOutputStream

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")

  kotlin("kapt")
}

/**
 * Получает короткую версию коммита.
 *
 * @return Короткий коммит.
 *
 * @author hepller
 */
fun getShortCommit(): String {
  val stdout = ByteArrayOutputStream()

  exec {
    commandLine("git", "rev-parse", "--short", "HEAD")

    standardOutput = stdout
  }

  return stdout.toString().trim()
}

val majorProjectVersion = "1.3.0"

android {
  compileSdk = 33

  defaultConfig {
    applicationId = "net.helio.app"

    minSdk = 29
    targetSdk = 33
    versionCode = 11
    versionName = "$majorProjectVersion-${getShortCommit()}"

    vectorDrawables {
      useSupportLibrary = true
    }

    // TODO: Настроить билд без указания конфига.
    signingConfig = signingConfigs.getByName("debug")
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }

    debug {
      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = "11"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    // https://maven.google.com/web/index.html#androidx.compose.compiler:compiler
    kotlinCompilerExtensionVersion = "1.3.1"
  }

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  namespace = "net.helio.app"
}

// region Версии библиотек.

val composeVersion = "1.2.1"
val moshiVersion = "1.14.0"
val ktorVersion = "2.1.2"

// endregion

dependencies {
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

  // region Compose-зависимости

  implementation("androidx.activity:activity-compose:1.6.0")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
  implementation("androidx.compose.material:material:$composeVersion")
  implementation("androidx.compose.material:material-icons-extended:$composeVersion")

  // https://developer.android.com/jetpack/androidx/releases/compose

  // endregion

  // region Сторонние зависимости.

  implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.1")

  implementation("com.squareup.moshi:moshi:$moshiVersion")
  implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
  implementation("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
  kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")

  implementation("com.github.jeziellago:compose-markdown:0.3.1")
  implementation("io.coil-kt:coil:2.2.2")

  // endregion
}