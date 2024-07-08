package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.exceptions.*;
import common.data.*;
import server.collection.CollectionManager;

public class InfoCommand extends CommandImpl {
    private CollectionManager<LabWork> collectionManager;
    public InfoCommand(CollectionManager<LabWork> cm){
        super("info", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException {
        return collectionManager.getInfo();
    }
}
