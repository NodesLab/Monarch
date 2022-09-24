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

package net.helio.app.ui.scaffold.inner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.helio.app.R
import net.helio.app.utility.RandomUtility.Companion.getRandomListElement

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
    val appName: String = stringResource(R.string.app_name)

    val motdList: List<String> = stringArrayResource(id = R.array.motd_strings).toList()
    val motdMessage: String = remember { getRandomListElement(motdList) ?: "null" } // remember для исправления рекомпозиции.

    Text(text = appName, color = Color.White, fontSize = 18.sp)
    Text(text = motdMessage, color = Color.Gray, fontSize = 14.sp)
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
    backgroundColor = MaterialTheme.colors.primary,
    elevation = 4.dp
  )
}