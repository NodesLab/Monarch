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

package net.monarch.app.ui.main.scaffold.structure.top.title

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import net.monarch.app.BuildConfig

/**
 * Заголовок приложения.
 *
 * @author hepller
 */
@Composable
fun Title() {
  val version: List<String> = remember { BuildConfig.VERSION_NAME.split("-") }

  val isVersionFieldExpanded: MutableState<Boolean> = remember { mutableStateOf(false) }

  TitleRow(isVersionFieldExpanded = isVersionFieldExpanded, version = version)
  DropdownVersionField(isVersionFieldExpanded = isVersionFieldExpanded, version = version)
}
