package common.command;

import common.command.core.Command;
import common.command.core.Commandable;
import common.command.core.Commands;
import common.connection.CommandMsg;
import common.connection.Response;
import common.connection.Status;
import common.exceptions.FileException;
import common.exceptions.NoSuchCommandException;
import common.io.ConsoleInputManager;
import common.io.FileInputManager;
import common.io.InputManager;

import java.io.Closeable;
import java.util.Stack;

import static common.io.OutputManager.print;


public abstract class CommandManager implements Commandable, Closeable {
    private final Commands commands;

    private InputManager inputManager;
    private boolean isRunning;
    private String currentScriptFileName;

    private static final Stack<String> callStack = new Stack<>();

    public void clearStack(){
        callStack.clear();
    }
    public Stack<String> getStack(){
        return callStack;
    }
    public String getCurrentScriptFileName(){
        return currentScriptFileName;
    }
    public CommandManager(){
        isRunning = false;
        currentScriptFileName = "";
        commands = new Commands();
    }
    public void addCommand(Command c) {
        commands.addCommand(c.getName(),c);
    }
    public void addCommand(String key, Command c){
        commands.addCommand(key, c);
    }

    public Command getCommand(String s){
        if (!hasCommand(s)) throw new NoSuchCommandException();
        Command cmd =  commands.get(s);
        return cmd;
    }
    public boolean hasCommand(String s){
        return commands.hasCommand(s);
    }

    public void executeCommand() {
        CommandMsg commandMsg = inputManager.readCommand();
        Response answerMsg = runCommand(commandMsg);
        if (answerMsg.getStatus() == Status.EXIT) close();
    }

    public void consoleMode() {
        inputManager = new ConsoleInputManager();
        isRunning = true;
        while (isRunning) {
            print("enter command (help to get command list): ");
            executeCommand();
        }
    }
    public void fileMode(String path) throws FileException {
        currentScriptFileName = path;
        inputManager = new FileInputManager(path);
        isRunning = true;
        while (isRunning && inputManager.getScanner().hasNextLine())
            executeCommand();
    }

    public Stack<String> getCommandHistory(){
        return commands.getCommandHistory();
    }
    public void setInputManager(InputManager in){
        inputManager = in;
    }
    public InputManager getInputManager(){
        return inputManager;
    }
    public boolean isRunning(){
        return isRunning;
    }

    public void setRunning(boolean running){
        isRunning = running;
    }
    public void close(){
        setRunning(false);
    }
}
