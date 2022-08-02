package me.hepller.helioapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import me.hepller.helioapp.command.CommandHandler;
import me.hepller.helioapp.command.CommandManager;
import me.hepller.helioapp.message.MessageModal;
import me.hepller.helioapp.message.MessageRecyclerViewAdapter;
import me.hepller.helioapp.message.MessageSender;

/**
 * Основная активити.
 */
public final class MainActivity extends AppCompatActivity {

  private RecyclerView chatsRecyclerView;
  private ImageButton sendMessageButton;
  private EditText editText;

  private ArrayList<MessageModal> messageModalArrayList;
  private MessageRecyclerViewAdapter messageRecyclerViewAdapter;

  private MessageSender messageSender;
  private CommandHandler commandHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    CommandManager.registerAllCommands();

    chatsRecyclerView = findViewById(R.id.id_recyclerview_chats);
    sendMessageButton = findViewById(R.id.id_send_button);
    editText = findViewById(R.id.id_edit_text);

    messageModalArrayList = new ArrayList<>();

    sendMessageButton.setOnClickListener(view -> {
      String userText = editText.getText().toString().trim();

      if (userText.isEmpty()) {
        Toast.makeText(MainActivity.this, "Введите команду", Toast.LENGTH_SHORT).show();

        return;
      }

      sendMessage(userText);

      editText.setText("");
    });

    messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(messageModalArrayList, this);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);

    chatsRecyclerView.setLayoutManager(linearLayoutManager);
    chatsRecyclerView.setAdapter(messageRecyclerViewAdapter);

    messageSender = new MessageSender(messageModalArrayList, messageRecyclerViewAdapter, chatsRecyclerView);
    commandHandler = new CommandHandler(messageSender);

    messageSender.viewBotMessage("Helio запущен, введите команду (для просмотра списка команд введите \"help\")");
  }

  private void sendMessage(String userMessage) {
    messageSender.viewUserMessage(userMessage);

    CompletableFuture.runAsync(() -> commandHandler.handleCommand(userMessage));
  }
}

// https://github.com/sendbird/Sendbird-Android
// https://sendbird.com/developer/tutorials/android-chat-tutorial-building-a-messaging-ui
// https://www.youtube.com/watch?v=g4xtkH1ckjE
// https://www.youtube.com/watch?v=BO5iCL7U6Lc