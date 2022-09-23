/*
 * Copyright © 2022 The Helio contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.helio.app.ui.scaffold

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import net.helio.app.ui.scaffold.inner.BottomBar
import net.helio.app.ui.scaffold.inner.Content
import net.helio.app.ui.scaffold.inner.DrawerContent
import net.helio.app.ui.scaffold.inner.TopBar

/**
 * Отрисовывает интерфейс приложения по шаблону структуры "Scaffold".
 */
@Composable
fun AppScaffold() {
  val scaffoldState: ScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
  val messageListState: LazyListState = rememberLazyListState()
  val scope: CoroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = scaffoldState,
    topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
    drawerContent = { DrawerContent() },
    content = { Content() },
    bottomBar = { BottomBar(scope = scope, listState = messageListState) }
  )
}