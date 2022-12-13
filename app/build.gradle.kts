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
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")

  kotlin("kapt")
}

val appVersion = "1.9.0"
val appVersionCode = 26

android {
  namespace = "net.monarch.app"

  compileSdk = project.ext["compileSdk"] as Int

  buildToolsVersion = "33.0.0"

  defaultConfig {
    applicationId = "net.monarch.app"

    minSdk = project.ext["minSdk"] as Int
    targetSdk = project.ext["targetSdk"] as Int
    versionCode = appVersionCode
    versionName = "$appVersion-$appVersionCode-${getShortCommit()}"

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
      isMinifyEnabled = project.ext["isMinifyEnabled"] as Boolean
      isShrinkResources = project.ext["isShrinkResources"] as Boolean

      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )

      signingConfig = signingConfigs.getByName("release")
    }
  }

  compileOptions {
    sourceCompatibility = project.ext["compatibilityVersion"] as JavaVersion
    targetCompatibility = project.ext["compatibilityVersion"] as JavaVersion
  }

  kotlinOptions {
    jvmTarget = project.ext["jvmTarget"] as String
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion =
      "1.3.2" // https://maven.google.com/web/index.html#androidx.compose.compiler:compiler
  }

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  api(project(":core"))

  getModuleList("commands")
    ?.filter { name -> name != "build" }
    ?.forEach {
      api(project(":commands:$it"))
    }

  // region COMPOSE/ANDROID ЗАВИСИМОСТИ.

  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
  implementation("androidx.activity:activity-compose:1.6.1")
  implementation("androidx.core:core-ktx:${project.ext["androidxCoreVersion"]}")
  implementation("androidx.compose.ui:ui:${project.ext["composeVersion"]}")
  implementation("androidx.compose.material:material:${project.ext["composeVersion"]}")
  implementation("androidx.compose.material:material-icons-extended:1.3.1")

  // endregion

  // region СТОРОННИЕ ЗАВИСИМОСТИ.

  implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

  // endregion
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

/**
 * Получает список модулей из директории.
 *
 * @param path Путь к директории модулей.
 *
 * @return Список модулей.
 *
 * @author hepller
 */
fun getModuleList(path: String): List<String>? {
  return try {
    val directories: List<String> = Files.walk(Paths.get(path), 1)
      .skip(1)
      .filter(Files::isDirectory)
      .map(Path::toFile)
      .map(File::getName)
      .collect(Collectors.toList())

    directories
  } catch (_: IOException) {
    null
  }
}

/**
 * Записывает содержимое файла в список строк.
 *
 * @param file Имя файла.
 *
 * @return Содержимое файла в виде списка строк.
 *
 * @author hepller
 */
fun readFile(file: String): List<String> {
  val inputStream: InputStream = File(file).inputStream()

  val lineList: MutableList<String> = mutableListOf()

  inputStream.bufferedReader().forEachLine { lineList.add(it) }

  return lineList
}
