package me.hepller.helioapp.ui.scaffold

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import me.hepller.helioapp.ui.scaffold.inner.BottomBar
import me.hepller.helioapp.ui.scaffold.inner.Content
import me.hepller.helioapp.ui.scaffold.inner.DrawerContent
import me.hepller.helioapp.ui.scaffold.inner.TopBar

/**
 * Скаффолд приложения.
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