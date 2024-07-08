package client.commands;

import client.client.Asker;
import client.client.Client;
import static common.io.OutputManager.*;

import common.command.core.Command;
import common.command.*;
import common.connection.*;

/**
 * command manager for client
 */
public class ClientCommandManager extends CommandManager{
    private Client client;
    private Asker asker;
    public ClientCommandManager(Client client){
        this.client = client;
        asker = new Asker(client);
        addCommand(new ExecuteScriptCommand(this));
        addCommand(new ExitCommand());
        addCommand(new HelpCommand());
        addCommand(new HistoryCommand(this));
    }

    public Client getClient(){
        return client;
    }
    @Override
    public AnswerMsg runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        if (hasCommand(msg)){
            Command cmd =  getCommand(msg);
            cmd.setArgument(msg);
            res = (AnswerMsg)cmd.run();
        } else {
            /* if this command is not executed on the client, then try to ask a server about command */
            res = asker.ask(msg);
        }
        print(res);
        return res;
    }
}
