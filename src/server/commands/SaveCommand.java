package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.data.LabWork;
import common.exceptions.*;
import common.file.ReaderWriter;
import server.collection.CollectionManager;

public class SaveCommand extends CommandImpl {
    ReaderWriter fileManager;
    CollectionManager<LabWork> collectionManager;
    public SaveCommand(CollectionManager<LabWork> cm, ReaderWriter fm){
        super("save", CommandType.SERVER_ONLY);
        collectionManager = cm;
        fileManager = fm;
    }
    @Override
    public String execute() throws FileException{
        if(hasStringArg()) {
            fileManager.setPath(getStringArg());
        };
        fileManager.write(collectionManager.serializeCollection());
        return "collection successfully saved";
    }
}
