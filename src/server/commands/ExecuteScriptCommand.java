package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.exceptions.*;

public class ExecuteScriptCommand extends CommandImpl {
    ServerCommandManager commandManager;
    public ExecuteScriptCommand(ServerCommandManager cm){
        super("execute_script", CommandType.NORMAL);
        commandManager = cm;
    }
    @Override
    public String execute() throws FileException{
        if(!hasStringArg()) throw new MissedCommandArgumentException();
        if (commandManager.getStack().contains(getStringArg())) throw new RecursiveScriptExecuteException();
        commandManager.getStack().add(getStringArg());
        ServerCommandManager process = new ServerCommandManager(commandManager.getServer());
        process.fileMode(getStringArg());

        commandManager.getStack().pop();
        return "script successfully executed";
    }
}
