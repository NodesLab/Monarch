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

  val file = File(".git")

  // Для возможности сборки после скачивания репозитория (т.к. он не содержит директорию .git).
  if (!file.isDirectory) return "xxxxxxx"

  exec {
    commandLine("git", "rev-parse", "--short", "HEAD")

    standardOutput = stdout
  }

  return stdout.toString().trim()
}

val projectVersion = "1.8.0"
val projectVersionCode = 25

android {
  compileSdk = 33

  defaultConfig {
    applicationId = "net.monarch.app"

    minSdk = 29
    targetSdk = 33
    versionCode = projectVersionCode
    versionName = "$projectVersion-$projectVersionCode-${getShortCommit()}"

    vectorDrawables {
      useSupportLibrary = true
    }
  }

  signingConfigs {
    create("release") {
      storeFile = file("monarch_public_key.jks")
      storePassword = "public"
      keyAlias = "public_key"
      keyPassword = "public"
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )

      signingConfig = signingConfigs.getByName("release")
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
    kotlinCompilerExtensionVersion = "1.3.2" // https://maven.google.com/web/index.html#androidx.compose.compiler:compiler
  }

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  buildToolsVersion = "33.0.0"

  namespace = "net.monarch.app"
}

// region Версии библиотек.

val composeVersion = "1.3.1" // https://developer.android.com/jetpack/androidx/releases/compose
val moshiVersion = "1.14.0"
val ktorVersion = "2.1.3"

// endregion

dependencies {

  // region Compose/Android зависимости.

  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
  implementation("androidx.activity:activity-compose:1.6.1")
  implementation("androidx.core:core-ktx:1.9.0")
  implementation("androidx.compose.ui:ui:$composeVersion")
  implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
  implementation("androidx.compose.material:material:$composeVersion")
  implementation("androidx.compose.material:material-icons-extended:1.3.1")

  // endregion

  // region Сторонние зависимости.

  implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

  implementation("com.squareup.moshi:moshi:$moshiVersion")
  implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
  implementation("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

  kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:2.1.3")

  implementation("com.linkedin.urls:url-detector:0.1.17")

//  implementation("com.github.curious-odd-man:rgxgen:1.4")

  // endregion
}
