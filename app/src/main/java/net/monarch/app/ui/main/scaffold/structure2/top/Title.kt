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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.monarch.app.BuildConfig
import net.monarch.app.R

/**
 * Заголовок приложения.
 *
 * @author hepller
 */
@Composable
fun Title() {
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

    val version: List<String> = remember { BuildConfig.VERSION_NAME.split("-") }
    val versionField: String = remember { "v${version[0]} ${version[1]} ${version[2]}" }

    Text(text = appName, color = MaterialTheme.colors.onPrimary, fontSize = 18.sp)
    Text(text = versionField, color = MaterialTheme.colors.onSecondary, fontSize = 14.sp)
  }
}
