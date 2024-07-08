package common.command.core;

import common.connection.*;
import common.data.LabWork;
import common.exceptions.*;
import server.exceptions.CheckIdException;

/**
 * basic command implementation
 */
public abstract class CommandImpl implements Command{
    private CommandType type;
    private String name;
    private Request arg;
    public CommandImpl(String n, CommandType t){
        name = n;
        type = t;
    }
    public CommandType getType(){
        return type;
    }
    public String getName(){
        return name;
    }

    /**
     * custom execute command
     * @return
     * @throws InvalidDataException
     * @throws CommandException
     * @throws FileException
     * @throws ConnectionException
     */
    public abstract String execute() throws InvalidDataException,CommandException, FileException, ConnectionException;

    /**
     * wraps execute into response
     * @return
     */
    public Response run(){
        AnswerMsg res = new AnswerMsg();
        try{
            res.info(execute());
        }
        catch(ExitException e){
            res.info(e.getMessage());
            res.setStatus(Status.EXIT);
        }
        catch(CheckIdException e){
            res.setStatus(Status.CHECK_ID);
        }
        catch(InvalidDataException|CommandException|FileException|ConnectionException e){
            res.error(e.getMessage());
        }
        return res;
    }
    public Request getArgument(){
        return arg;
    }
    public void setArgument(Request req){
        arg=req;
    }
    public boolean hasStringArg(){
        return arg!=null && arg.getStringArg()!=null && !arg.getStringArg().equals("");
    }
    public boolean hasLabWorkArg(){
        return arg!=null && arg.getLabWork()!=null;
    }

    public String getStringArg(){
        return getArgument().getStringArg();
    }

    public LabWork getLabWorkArg(){
        return getArgument().getLabWork();
    }
}
