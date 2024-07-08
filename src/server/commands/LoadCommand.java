package server.commands;

import common.command.core.CommandImpl;
import common.command.core.CommandType;
import common.data.LabWork;
import common.exceptions.*;
import common.file.ReaderWriter;
import server.collection.CollectionManager;

public class LoadCommand extends CommandImpl {
    ReaderWriter fileManager;
    CollectionManager<LabWork> collectionManager;
    public LoadCommand(CollectionManager<LabWork> cm, ReaderWriter fm){
        super("load", CommandType.SERVER_ONLY);
        collectionManager = cm;
        fileManager = fm;
    }
    @Override
    public String execute() throws FileException{
        if(hasStringArg()) {
            fileManager.setPath(getStringArg());
        }
        collectionManager.deserializeCollection(fileManager.read());
        return "collection successfully loaded";
    }
}
