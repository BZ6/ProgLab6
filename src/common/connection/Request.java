package common.connection;

import common.data.LabWork;

import java.io.Serializable;


public interface Request extends Serializable{
    public String getStringArg();
    public LabWork getLabWork();
    public String getCommandName();
    public void setLabWork(LabWork labWork);
}
