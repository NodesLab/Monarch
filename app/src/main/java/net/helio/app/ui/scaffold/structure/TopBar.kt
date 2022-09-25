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

package net.helio.app.ui.scaffold.structure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.helio.app.R
import net.helio.app.utility.RandomUtility.getRandomListElement

/**
 * Иконка навигации (кнопка).
 *
 * @param onClick Функция, выполняемая при нажатии на кнопку.
 */
@Composable
private fun NavigationIcon(onClick: () -> Unit) {
  IconButton(onClick = { onClick() }) {
    Icon(
      imageVector = Icons.Default.Menu,
      contentDescription = null,
      tint = MaterialTheme.colors.onPrimary,
      modifier = Modifier.size(30.dp)
    )
  }
}

/**
 * Заголовок приложения.
 */
@Composable
private fun Title() {
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

    val motdList: List<String> = stringArrayResource(R.array.motd_strings).toList()
    val motdMessage: String = remember { getRandomListElement(motdList) ?: "null" }

    Text(text = appName, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)
    Text(text = motdMessage, color = MaterialTheme.colors.onSecondary, fontSize = 14.sp)
  }
}

/**
 * Верхняя панель.
 *
 * @param onNavigationIconClick Функция, выполняемая при нажатии на иконку навигации.
 */
@Composable
fun TopBar(onNavigationIconClick: () -> Unit) {
  TopAppBar(
    navigationIcon = { NavigationIcon(onNavigationIconClick) },
    title = { Title() },
    backgroundColor = MaterialTheme.colors.primary,
    elevation = 4.dp
  )
}