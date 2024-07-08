package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.exceptions.*;
import common.data.*;
import server.collection.CollectionManager;

import static common.utils.Parser.*;
public class RemoveByIdCommand extends CommandImpl {
    private CollectionManager<LabWork> collectionManager;
    public RemoveByIdCommand(CollectionManager<LabWork> cm){
        super("remove_by_id", CommandType.NORMAL);
        collectionManager = cm;
    }


    @Override
    public String execute() throws InvalidDataException {
        if(collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if(!hasStringArg()) throw new MissedCommandArgumentException();
        Integer id = parseId(getStringArg());
        if(!collectionManager.checkId(id)) throw new InvalidCommandArgumentException("no such id");

        boolean success = collectionManager.removeById(id);
        if (success) return "element #" + Integer.toString(id) + " removed";
        else throw new CommandException("cannot remove");
    }
}
