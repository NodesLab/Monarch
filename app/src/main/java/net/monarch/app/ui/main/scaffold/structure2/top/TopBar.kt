/*
 * Copyright © 2022 The Monarch Contributors.
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

package net.monarch.app.ui.main.scaffold.structure2.top

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import net.monarch.app.ui.main.scaffold.structure2.top.buttons.NavigationIcon

/**
 * Верхняя панель.
 *
 * @param onNavigationIconClick Функция, выполняемая при нажатии на иконку навигации.
 *
 * @author hepller
 */
@Composable
fun TopBar(onNavigationIconClick: () -> Unit) {
  TopAppBar(
    navigationIcon = { NavigationIcon(onNavigationIconClick) },
    title = { Title() },
    backgroundColor = MaterialTheme.colors.primary
  )
}
