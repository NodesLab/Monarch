package me.hepller.helioapp.message;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageSender {
  private ArrayList<MessageModal> messageModalArrayList;
  private MessageRecyclerViewAdapter messageRecyclerViewAdapter;
  private RecyclerView chatsRecyclerView;

  public MessageSender(ArrayList<MessageModal> messageModalArrayList, MessageRecyclerViewAdapter messageRecyclerViewAdapter, RecyclerView chatsRecyclerView) {
    this.messageModalArrayList = messageModalArrayList;
    this.messageRecyclerViewAdapter = messageRecyclerViewAdapter;
    this.chatsRecyclerView = chatsRecyclerView;
  }

  public void viewBotMessage(final String text) {
    viewMessage(text, "bot");
  }

  public void viewUserMessage(final String text) {
    viewMessage(text, "user");
  }

  private void viewMessage(final String text, final String sender) {
    this.messageModalArrayList.add(new MessageModal(text, sender));

    this.messageRecyclerViewAdapter.notifyItemChanged(0);

    this.chatsRecyclerView.scrollToPosition(messageRecyclerViewAdapter.getItemCount() - 1);
  }
}