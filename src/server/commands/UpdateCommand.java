package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.exceptions.*;
import common.data.*;
import server.collection.CollectionManager;
import server.exceptions.CheckIdException;

import static common.utils.Parser.*;
public class UpdateCommand extends CommandImpl {
    private CollectionManager<LabWork> collectionManager;
    public UpdateCommand(CollectionManager<LabWork> cm){
        super("update", CommandType.NORMAL);
        collectionManager = cm;
    }

    @Override
    public String execute() throws InvalidDataException{
        if (collectionManager.getCollection().isEmpty()) throw new EmptyCollectionException();
        if (!hasStringArg()) throw new MissedCommandArgumentException();
        Integer id = parseId(getStringArg());
        if (!collectionManager.checkId(id)) throw new InvalidCommandArgumentException("no such id");
        if (!hasLabWorkArg()) throw new CheckIdException();

        boolean success = collectionManager.updateById(id, getLabWorkArg());
        if (success) return "element #" + Integer.toString(id) + " updated";
        else throw new CommandException("cannot update");
    }
}
