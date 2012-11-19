package core.c;

/**
 *
 * @author tobikster
 */
public class DataBaseException extends Exception {

    /**
     * Creates a new instance of
     * <code>DataBaseException</code> without detail message.
     */
    public DataBaseException() {
    }

    /**
     * Constructs an instance of
     * <code>DataBaseException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DataBaseException(String msg) {
        super(msg);
    }
}
