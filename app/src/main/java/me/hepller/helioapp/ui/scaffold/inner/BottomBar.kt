package me.hepller.helioapp.ui.scaffold.inner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.hepller.helioapp.data.MessagesData
import me.hepller.helioapp.utility.MessageUtility

/**
 * Нижняя панель.
 *
 * @param scope Область видимости.
 * @param listState Состояние листа сообщений.
 */
@Composable
fun BottomBar(scope: CoroutineScope, listState: LazyListState) {
  var input: String by rememberSaveable { mutableStateOf("") }
  val isValid: Boolean = input.isNotEmpty()

  Surface(
    color = MaterialTheme.colors.primary,
    modifier = Modifier
      .fillMaxWidth()
      .height(60.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxSize()
    ) {
      TextField(
        value = input,
        onValueChange = { newText ->
          input = newText.trimStart { it == '0' }
        },
        placeholder = {
          Text(text = "Введите команду")
        },
        isError = !isValid,
        colors = TextFieldDefaults.textFieldColors(
          textColor = Color.White,
          backgroundColor = Color.Transparent,
          placeholderColor = Color.Gray,
          cursorColor = Color.White,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
          disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
          .height(60.dp)
          .width(270.dp)
          .padding(start = 10.dp)
      )

      Button(
        onClick = {
          MessageUtility.addUserMessage(input)

          input = ""

          scope.launch {
            listState.animateScrollToItem(index = MessagesData.messageList.size - 1)
          }
        },
        shape = RoundedCornerShape(10.dp),
        elevation =  ButtonDefaults.elevation(
          defaultElevation = 0.dp,
          pressedElevation = 15.dp,
          disabledElevation = 0.dp
        ),
        modifier = Modifier
          .padding(start = 12.dp, top = 6.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Send,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier
            .size(30.dp)
        )
      }
    }
  }
}