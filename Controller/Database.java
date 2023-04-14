package Controller;

/**
 * An abstract class used to implement specific databases under the same
 * directory.
 * 
 * @author Lab A34 Assignment Team 1
 * @version 1.0
 * @since 2023-04-14
 */
public abstract class Database {

    /**
     * Represents the directory where all database are stored.
     */
    protected String directory = "data/";

    /**
     * Represents the delimiter used in all database to split values.
     */
    protected String delimiter = "\t";

    /**
     * Represents the delimiter used to obtain User ID from User's email.
     */
    protected String emailDelimiter = "@";
    

    /**
     * Abstract method to be implemented by child classes to read the specified
     * file.
     */
    public abstract void readFile();

    /**
     * Abstract method to be implemented by child classes to update the specified
     * file.
     */
    public abstract void updateFile();

}
