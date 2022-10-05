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

package net.helio.app.ui.scaffold

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.helio.app.ui.scaffold.structure.BottomBar
import net.helio.app.ui.scaffold.structure.Content
import net.helio.app.ui.scaffold.structure.DrawerContent
import net.helio.app.ui.scaffold.structure.TopBar

/**
 * Отрисовывает интерфейс приложения по шаблону структуры "Scaffold".
 *
 * @author hepller
 */
@Composable
fun AppScaffold() {
  val scaffoldState: ScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
  val scope: CoroutineScope = rememberCoroutineScope()

  val onNavigationIconClick: () -> Job = {
    scope.launch {
      scaffoldState.drawerState.apply {
        if (isClosed) open() else close()
      }
    }
  }

  Scaffold(
    scaffoldState = scaffoldState,

    topBar = { TopBar { onNavigationIconClick() } },
    drawerContent = { DrawerContent() },
    bottomBar = { BottomBar() },

    backgroundColor = MaterialTheme.colors.primary,

    drawerBackgroundColor = MaterialTheme.colors.primary,
    drawerScrimColor = MaterialTheme.colors.surface,
    drawerShape = MaterialTheme.shapes.small,
    drawerElevation = 16.dp
  ) {
    Content(it)
  }
}