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

package net.monarch.app.ui.main.scaffold.top.title

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.monarch.app.R

/**
 * Ряд заголовка приложения.
 *
 * @param isVersionFieldExpanded Состояние открытости выпадающего поля.
 * @param version Список элементов версии (название, номер, коммит).
 *
 * @author hepller
 */
@Composable
fun TitleRow(isVersionFieldExpanded: MutableState<Boolean>, version: List<String>) {
  Row(
    modifier = Modifier.clickable { isVersionFieldExpanded.value = !isVersionFieldExpanded.value }
  ) {
    Image(
      painter = painterResource(R.mipmap.ic_launcher_round),
      contentDescription = "Логотип",
      modifier = Modifier
        .size(35.dp)
        .clip(CircleShape)
    )

    Column(
      modifier = Modifier.padding(start = 10.dp)
    ) {
      val appName: String = remember { "Monarch" }
      val versionField: String = remember { "v${version[0]}" }

      Text(
        text = appName,
        color = MaterialTheme.colors.onPrimary,
        fontSize = 18.sp
      )

      Text(
        text = versionField,
        color = MaterialTheme.colors.onSecondary,
        fontSize = 14.sp
      )
    }
  }
}
