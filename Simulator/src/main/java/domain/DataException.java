package domain;

/**
Exception for every problem regarding retreiving data or converting this data trough adapters or
 other communication channels.
 */
public class DataException extends Exception {
    public DataException(String message, Exception cause) {
        super(message, cause);
    }
}
