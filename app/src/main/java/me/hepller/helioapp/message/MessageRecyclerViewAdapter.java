package me.hepller.helioapp.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.hepller.helioapp.R;

public final class MessageRecyclerViewAdapter extends RecyclerView.Adapter {

  private final ArrayList<MessageModal> messageModalArrayList;
  private final Context context;

  public MessageRecyclerViewAdapter(ArrayList<MessageModal> messageModalArrayList, Context context) {
    this.messageModalArrayList = messageModalArrayList;
    this.context = context;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final View view;

    switch (viewType) {
      case 0:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg, parent, false);

        return new UserViewHolder(view);
      case 1:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg, parent, false);

        return new BotViewHolder(view);
    }

    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    // this method is use to set data to our layout file.
    MessageModal modal = messageModalArrayList.get(position);
    switch (modal.getSender()) {
      case "user":
        // below line is to set the text to our text view of user layout
        ((UserViewHolder) holder).userTextView.setText(modal.getMessageText());
        break;
      case "bot":
        // below line is to set the text to our text view of bot layout
        ((BotViewHolder) holder).botTextView.setText(modal.getMessageText());
        break;
    }
  }

  @Override
  public int getItemCount() {
    // return the size of array list
    return messageModalArrayList.size();
  }

  @Override
  public int getItemViewType(int position) {
    // below line of code is to set position.
    switch (messageModalArrayList.get(position).getSender()) {
      case "user":
        return 0;
      case "bot":
        return 1;
      default:
        return -1;
    }
  }

  public static class UserViewHolder extends RecyclerView.ViewHolder {
    private final TextView userTextView;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);

      userTextView = itemView.findViewById(R.id.id_user_textview);
    }
  }

  public static class BotViewHolder extends RecyclerView.ViewHolder {
    private final TextView botTextView;

    public BotViewHolder(@NonNull View itemView) {
      super(itemView);

      botTextView = itemView.findViewById(R.id.id_bot_textview);
    }
  }
}