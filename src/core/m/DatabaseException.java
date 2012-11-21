package core.m;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tobikster
 */
public class DatabaseException extends Exception {
	private List<String> _errors;
    /**
     * Creates a new instance of
     * <code>DataBaseException</code> without detail message.
     */
    public DatabaseException(List<String> errors) {
		_errors = errors;
    }

    /**
     * Constructs an instance of
     * <code>DataBaseException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DatabaseException(String msg) {
        super(msg);
		_errors = new LinkedList<>();
    }
	
	public void addError(String error){
		_errors.add(error);
	}
	
	public boolean hasErrors() {
		return !_errors.isEmpty();
	}
}
