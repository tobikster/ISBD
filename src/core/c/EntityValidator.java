package core.c;

import core.m.DatabaseException;

/**
 *
 * @author tobikster
 */
public interface EntityValidator <T>{
    public boolean validate(T object) throws DatabaseException;
}
