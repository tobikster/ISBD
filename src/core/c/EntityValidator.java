package core.c;

/**
 *
 * @author tobikster
 */
public interface EntityValidator <T>{
    public boolean validate(T object) throws DataBaseException;
}
