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
}

// region Версии.

val composeVersion = "1.1.0-beta01"

// endregion

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

android {
  compileSdk = 32

  defaultConfig {
    applicationId = "net.helio.app"
    minSdk = 31
    targetSdk = 32
    versionCode = 1
    versionName = "0.0.1-${getShortCommit()}"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false

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
    kotlinCompilerExtensionVersion = composeVersion
  }

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.compose.ui:ui:${composeVersion}")
  implementation("androidx.compose.material:material:${composeVersion}")
  implementation("androidx.compose.ui:ui-tooling-preview:${composeVersion}")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
  implementation("androidx.activity:activity-compose:1.3.1")

  // region Сторонние зависимости.

  implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.1")

  // endregion

  testImplementation("junit:junit:4.13.2")

  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:${composeVersion}")

  debugImplementation("androidx.compose.ui:ui-tooling:${composeVersion}")
  debugImplementation("androidx.compose.ui:ui-test-manifest:${composeVersion}")
}