package me.hepller.helioapp.ui.scaffold.inner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import me.hepller.helioapp.R

/**
 * Иконка навигации.
 *
 * @param scope Область видимости.
 * @param scaffoldState Состояние скаффолда.
 */
@Composable
fun NavigationIcon(scope: CoroutineScope, scaffoldState: ScaffoldState) {
  Surface(
    color = MaterialTheme.colors.primary,
    shape = RoundedCornerShape(16.dp),
    modifier = Modifier
      .size(40.dp)
      .padding(start = 6.dp)
  ) {
    Image(
      painter = painterResource(R.drawable.ic_menu),
      contentDescription = null,
      modifier = Modifier
        .clickable {
          scope.launch {
            scaffoldState.drawerState.apply {
              if (isClosed) open() else close()
            }
          }
        }
    )
  }
}

/**
 * Заголовок приложения.
 */
@Composable
fun Title() {
  Image(
    painter = painterResource(R.mipmap.ic_launcher_round),
    contentDescription = null,
    modifier = Modifier
      .size(35.dp)
      .clip(CircleShape)
  )

  Column(
    modifier = Modifier.padding(start = 10.dp)
  ) {
    Text(text = "Helio", color = Color.White, fontSize = 18.sp)

    Text(text = "v0.0.1", color = Color.Gray, fontSize = 14.sp)
  }
}

/**
 * Верхняя панель.
 *
 * @param scope Область видимости.
 * @param scaffoldState Состояние скаффолда.
 */
@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
  TopAppBar(
    navigationIcon = { NavigationIcon(scope, scaffoldState) },
    title = { Title() },
    backgroundColor = MaterialTheme.colors.primary
  )
}