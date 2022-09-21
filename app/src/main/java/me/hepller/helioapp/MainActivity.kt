package me.hepller.helioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import me.hepller.helioapp.data.Message
import me.hepller.helioapp.ui.theme.BotMessageBackgroundDark
import me.hepller.helioapp.ui.theme.HelioTheme
import me.hepller.helioapp.ui.theme.UserMessageBackgroundDark

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      HelioTheme {
        ScaffoldTest()
      }
    }
  }
}

@Composable
fun ScaffoldTest() {
  val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
  val scope = rememberCoroutineScope()

  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  Scaffold(
    scaffoldState = scaffoldState,

    topBar = {
      TopAppBar(
        navigationIcon = {
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

        },

        title = {
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
        },

        backgroundColor = MaterialTheme.colors.primary
      )
    },

    drawerContent = {
      Text(text = "Test")
      Divider()
    },

    content = {
      Surface(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 60.dp),
        color = MaterialTheme.colors.background
      ) {
        MessageList(messages = SampleData.conversationSample)
      }
    },

    bottomBar = {
      var input by rememberSaveable { mutableStateOf("") }
      val isValid = input.isNotEmpty()

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
              SampleData.conversationSample.add(Message("User", input, "00:00"))

              input = ""

              coroutineScope.launch {
                listState.animateScrollToItem(index = SampleData.conversationSample.size - 1)
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
  )
}

@Composable
fun MessageCard(message: Message) {
  val color = if (message.author == "bot") BotMessageBackgroundDark else UserMessageBackgroundDark
  val alignment = if (message.author == "bot") Alignment.TopStart else Alignment.TopEnd
  val author = if (message.author == "bot") "Helio" else "User"

  Box(
    modifier = Modifier.fillMaxWidth()
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = color,
      modifier = Modifier.align(alignment)
    ) {
      Column(
        modifier = Modifier.defaultMinSize(100.dp),
      ) {
        Text(
          text = author,
          color = MaterialTheme.colors.secondary,
          style = MaterialTheme.typography.subtitle2,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.padding(start = 10.dp, top = 4.dp)
        )

        SelectionContainer {
          Text(
            text = message.text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 10.dp),
          )
        }

        Text(
          text = "00:00",
          style = MaterialTheme.typography.body2,
          color = Color.Gray,
          modifier = Modifier
            .align(Alignment.End)
            .padding(end = 8.dp, bottom = 4.dp, top = 4.dp)
            .clickable {
              SampleData.conversationSample.remove(message)
            }
        )
      }
    }
  }
}

@Composable
fun MessageList(messages: List<Message>) {
  val listState = rememberLazyListState()

  LazyColumn(
    state = listState,
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    items(messages) { message -> MessageCard(message) }
  }
}