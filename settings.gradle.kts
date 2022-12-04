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

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}

rootProject.name = "Monarch"

include(":app")
include(":core")

getModuleList("commands")
  ?.filter { name -> name != "build" }
  ?.forEach {
    include(":commands:$it")
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
