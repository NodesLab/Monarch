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

package net.helio.app.ui.main.scaffold.structure2.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Содержимое поля карточек действий.
 *
 * @param contentPadding Отспупы для содержимого.
 */
@Composable
fun ActionsContent(contentPadding: PaddingValues) {
  val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7")

  Surface(
    modifier = Modifier
      .fillMaxSize()
      .padding(paddingValues = contentPadding)
  ) { // start point.
    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      contentPadding = PaddingValues(8.dp),
      modifier = Modifier.background(color = MaterialTheme.colors.primary)
    ) {
      items(data) { item ->
        Card(
          backgroundColor = MaterialTheme.colors.secondaryVariant,
          modifier = Modifier
            .padding(5.dp)
            .size(50.dp)
        ) {
          Icon(
            imageVector = Icons.Rounded.Settings,
            contentDescription = "test",
            tint = MaterialTheme.colors.onPrimary,
              modifier = Modifier.padding(5.dp)
          )

//          Text(
//            textAlign = TextAlign.Center,
//            text = item,
//            color = MaterialTheme.colors.onPrimary,
//            modifier = Modifier.padding(24.dp)
//          )
        }
      }
    }
  } // end point.
}
