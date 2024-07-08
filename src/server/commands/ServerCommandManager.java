package server.commands;

import common.command.CommandManager;
import common.command.core.Command;
import common.file.ReaderWriter;
import common.connection.*;
import common.data.LabWork;
import common.exceptions.*;
import server.collection.CollectionManager;
import server.log.Log;
import server.server.Server;

public class ServerCommandManager extends CommandManager {
    private Server server;
    private CollectionManager<LabWork> collectionManager;
    private ReaderWriter fileManager;

    public ServerCommandManager(Server  serv){
        server = serv;
        collectionManager = server.getCollectionManager();
        fileManager = server.getFileManager();
        addCommand(new ExitCommand());
        addCommand(new HelpCommand());
        addCommand(new ExecuteScriptCommand(this));
        addCommand(new InfoCommand(collectionManager));
        addCommand(new AddCommand(collectionManager));
        addCommand(new AddIfMaxCommand(collectionManager));
        addCommand(new UpdateCommand(collectionManager));
        addCommand(new RemoveByIdCommand(collectionManager));
        addCommand(new ClearCommand(collectionManager));
        addCommand(new ShowCommand(collectionManager));
        addCommand(new MinByPersonalQualitiesMinimumCommand(collectionManager));
        addCommand(new MaxByDisciplineCommand(collectionManager));
        addCommand(new FilterStartsWithNameCommand(collectionManager));
        addCommand(new HistoryCommand(this));
        addCommand(new SaveCommand(collectionManager,fileManager));
        addCommand(new LoadCommand(collectionManager,fileManager));
    }
    public Server getServer(){
        return server;
    }
    @Override
    public AnswerMsg runCommand(Request msg) {
        AnswerMsg res = new AnswerMsg();
        try {
            Command cmd = getCommand(msg.getCommandName());
            cmd.setArgument(msg);
            res = (AnswerMsg) cmd.run();
        } catch (CommandException e){
            res.error(e.getMessage());
        }
        switch (res.getStatus()){
            case EXIT:
                getCommand("save").run();
                Log.logger.error(res.getMessage());
                server.close();
                break;
            case ERROR:
                Log.logger.error(res.getMessage());
                break;
            default:
                Log.logger.info(res.getMessage());
                break;
        }
        return res;
    }
}
