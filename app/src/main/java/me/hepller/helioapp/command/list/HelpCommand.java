package me.hepller.helioapp.command.list;

import androidx.annotation.NonNull;

import me.hepller.helioapp.command.Command;
import me.hepller.helioapp.command.CommandManager;
import me.hepller.helioapp.message.MessageModal;
import me.hepller.helioapp.message.MessageRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author hepller
 */
public final class HelpCommand extends Command {

  public HelpCommand() {
    super(new String[] {"help", "помощь", "commands", "cmds", "команды"}, "Список команд");
  }

  @Override
  public void execute(@NotNull String message, @NonNull ArrayList<MessageModal> messageModalArrayList, MessageRecyclerViewAdapter messageRecyclerViewAdapter, String @NotNull [] arguments) {
    final String[] messageScheme = {
      "\uD83C\uDF35 Доступные команды:",
      "",
      String.join("\n", getCommandList()),
    };

    messageModalArrayList.add(new MessageModal(String.join("\n", messageScheme), "bot"));
  }

  /**
   * Получает список команд с описанием в виде массива строк.
   *
   * @return Список команд с описанием.
   */
  private static @NotNull ArrayList<String> getCommandList() {
    final HashSet<Command> commandSet = new HashSet<>(CommandManager.getCommandMap().values());
    final ArrayList<String> commandList = new ArrayList<>();

    for (final Command command : commandSet) {
      if (command.getHiddenCommand() || command.getForAdmin()) continue;

      final String betaStatus = command.getBetaStatus()
        ? "ᵇᵉᵗᵃ"
        : "";

      commandList.add(String.format("/%s — %s %s", command.getAliases()[0], command.getDescription(), betaStatus));
    }

    Collections.sort(commandList);

    return commandList;
  }
}