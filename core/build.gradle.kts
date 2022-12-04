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

plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")

  kotlin("kapt")
}

android {
  namespace = "net.monarch.core"

  compileSdk = project.ext["compileSdk"] as Int

  defaultConfig {
    minSdk = project.ext["minSdk"] as Int
    targetSdk = project.ext["targetSdk"] as Int
  }

  buildTypes {
    release {
      isMinifyEnabled = true

      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }

  compileOptions {
    sourceCompatibility = project.ext["compatibilityVersion"] as JavaVersion
    targetCompatibility = project.ext["compatibilityVersion"] as JavaVersion
  }

  kotlinOptions {
    jvmTarget = project.ext["jvmTarget"] as String
  }
}

dependencies {
  val ktorVersion = "2.1.3"

  // region COMPOSE/ANDROID ЗАВИСИМОСТИ.

  implementation("androidx.core:core-ktx:${project.ext["androidxCoreVersion"]}")
  implementation("androidx.compose.ui:ui:${project.ext["composeVersion"]}")

  // endregion

  // region СТОРОННИЕ ЗАВИСИМОСТИ.

  implementation("com.squareup.moshi:moshi:${project.ext["moshiVersion"]}")
  implementation("com.squareup.moshi:moshi-adapters:${project.ext["moshiVersion"]}")
  implementation("com.squareup.moshi:moshi-kotlin:${project.ext["moshiVersion"]}")
  implementation("com.squareup.moshi:moshi-kotlin-codegen:${project.ext["moshiVersion"]}")

  kapt("com.squareup.moshi:moshi-kotlin-codegen:${project.ext["moshiVersion"]}")

  implementation("io.ktor:ktor-client-core:$ktorVersion")
  implementation("io.ktor:ktor-client-cio:$ktorVersion")

  implementation("com.linkedin.urls:url-detector:0.1.17")

//  implementation("com.github.curious-odd-man:rgxgen:1.4")

  // endregion
}
