package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.exceptions.*;
import common.data.*;
import server.collection.CollectionManager;

public class AddCommand extends CommandImpl {
    private CollectionManager<LabWork> collectionManager;
    public AddCommand(CollectionManager<LabWork> cm){
        super("add", CommandType.NORMAL);
        collectionManager = cm;
    }
    public CollectionManager<LabWork> getManager(){
        return collectionManager;
    }

    @Override
    public String execute() throws InvalidDataException, CommandException {
        getManager().add(getLabWorkArg());
        return "Added element: " + getLabWorkArg().toString();
    }
}
