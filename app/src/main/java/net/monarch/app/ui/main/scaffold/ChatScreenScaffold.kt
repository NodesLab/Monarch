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

package net.monarch.app.ui.main.scaffold

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.monarch.app.ui.main.scaffold.structure.DrawerContent
import net.monarch.app.ui.main.scaffold.structure.bottom.BottomBar
import net.monarch.app.ui.main.scaffold.structure.content.ChatContent
import net.monarch.app.ui.main.scaffold.structure.top.TopBar

/**
 * Чато-подобный UI.
 *
 * @author hepller
 */
@Composable
fun ChatScreenScaffold() {
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
    drawerShape = RoundedCornerShape(
      topStart = 0.dp,
      topEnd = 16.dp,
      bottomStart = 0.dp,
      bottomEnd = 16.dp
    )
  ) {
    ChatContent(it)
  }
}
