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
}

android {
  namespace = "net.monarch.commands.crypt"

  compileSdk = project.ext["compileSdk"] as Int

  defaultConfig {
    minSdk = project.ext["minSdk"] as Int
    targetSdk = project.ext["targetSdk"] as Int
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
    sourceCompatibility = project.ext["compatibilityVersion"] as JavaVersion
    targetCompatibility = project.ext["compatibilityVersion"] as JavaVersion
  }

  kotlinOptions {
    jvmTarget = project.ext["jvmTarget"] as String
  }
}

dependencies {
  api(project(":core"))
}
