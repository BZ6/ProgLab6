package client.main;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import client.client.Client;
import common.exceptions.ConnectionException;
import common.exceptions.InvalidPortException;
import common.exceptions.InvalidProgramArgumentsException;

import static common.io.OutputManager.*;

public class ClientMain {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            printErr("Unsupported encoding UTF-8");
        }

        String addr  = "localhost";
        int port = 5000;
        try {
            if (args.length != 2) throw new InvalidProgramArgumentsException("no address passed by arguments");
            addr = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                throw new InvalidPortException();
            }
            Client client = new Client(addr,port);
            client.run();
        }
        catch (InvalidProgramArgumentsException| ConnectionException e){
            printErr(e.getMessage());
        }
    }
}
