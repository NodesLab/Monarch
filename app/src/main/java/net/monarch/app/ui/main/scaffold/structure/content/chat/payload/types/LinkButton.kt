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

package net.monarch.app.ui.main.scaffold.structure.content.chat.payload.types

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import net.monarch.app.core.message.model.payload.LinkMessagePayload

/**
 * Кнопка-ссылка.
 *
 * @param payload Объект полезной нагрузки.
 * @param modifier Модификатор (для корректного отображения).
 *
 * @see LinkMessagePayload
 *
 * @author hepller
 */
@Composable
fun LinkButton(payload: LinkMessagePayload, modifier: Modifier) {
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.padding(top = 5.dp)
  ) {
    val uriHandler: UriHandler = LocalUriHandler.current

    TextButton(
      onClick = { uriHandler.openUri(payload.linkSource) },
      colors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.secondary
      ),
      modifier = Modifier.fillMaxWidth(),
      shape = MaterialTheme.shapes.medium
    ) {
      Text(
        text = payload.linkLabel,
        color = MaterialTheme.colors.onPrimary
      )

      Spacer(
        modifier = Modifier.width(5.dp)
      )

      Icon(
        imageVector = Icons.Rounded.OpenInNew,
        contentDescription = "Открыть ссылку",
        tint = MaterialTheme.colors.onPrimary
      )
    }
  }
}
