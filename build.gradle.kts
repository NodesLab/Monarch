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
  id("com.android.application") version "7.3.1" apply false
  id("com.android.library") version "7.3.1" apply false

  id("org.jetbrains.kotlin.android") version "1.7.20" apply false
}

allprojects {
  ext {

    // region ВЕРСИИ ANDROID SDK.

    set("compileSdk", 33)
    set("targetSdk", 33)
    set("minSdk", 29)

    // endregion

    // region ВЕРСИИ JAVA.

    set("compatibilityVersion", JavaVersion.VERSION_11)
    set("jvmTarget", "11")

    // endregion

    // region ВЕРСИИ ОБЩИХ ЗАВИСИМОСТЕЙ.

    set("androidxCoreVersion", "1.9.0")
    set("composeVersion", "1.3.1") // https://developer.android.com/jetpack/androidx/releases/compose

    set("moshiVersion", "1.14.0")

    // endregion
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
  delete(project.buildDir)
}
