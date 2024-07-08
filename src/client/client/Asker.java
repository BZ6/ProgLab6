package client.client;

import common.connection.AnswerMsg;
import common.connection.Request;
import common.exceptions.ConnectionException;
import common.exceptions.ConnectionTimeoutException;
import common.exceptions.InvalidDataException;

public class Asker {
    private Client client;
    public Asker(Client client){
        this.client = client;
    }

    public AnswerMsg ask(Request req) {
        AnswerMsg res = new AnswerMsg();
        try{
            client.send(req);
            res = (AnswerMsg)client.receive();
        }
        catch (ConnectionTimeoutException e){
            res.info("no attempts left");
        }
        catch(InvalidDataException | ConnectionException e){
            res.error(e.getMessage());
        }
        return res;
    }
}
