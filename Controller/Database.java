package Controller;

public abstract class Database {

    protected String directory = "data/";
    protected String delimiter = "\t";
    protected String emailDelimiter = "@";

    public abstract void readFile();

    public abstract void updateFile();

}
