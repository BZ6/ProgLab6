package client.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.exceptions.ExitException;
public class ExitCommand extends CommandImpl {
    public ExitCommand(){
        super("exit", CommandType.NORMAL);
    }
    @Override
    public String execute(){
        throw new ExitException();
    }
}
