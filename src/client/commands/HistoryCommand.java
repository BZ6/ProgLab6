package client.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;

import java.util.Stack;

public class HistoryCommand extends CommandImpl {
    ClientCommandManager commandManager;

   public HistoryCommand(ClientCommandManager cm) {
       super("history", CommandType.NORMAL);
       commandManager = cm;
   }

    @Override
    public String execute() {
       Stack<String> commandHistory = commandManager.getCommandHistory();
        StringBuilder text = new StringBuilder();
       if (commandHistory.isEmpty()) {
           text.append("history is empty");
       } else
           for (int i = commandHistory.size(); i > 0; i--)
               text.append(commandHistory.get(commandHistory.size() - i) + "\n");
       return text.toString();
   }
}
