package core.c;

import core.m.DatabaseException;

public class ErrorHandler
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // <editor-fold defaultstate="collapsed" desc="Singleton">
  public static ErrorHandler getInstance()
  {
    return InstanceHolder.p_instance;
  }

  private static final class InstanceHolder
  {
    private static final ErrorHandler p_instance = new ErrorHandler();
  }
  // </editor-fold>

  private ErrorHandler()
  {

  }
  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">

  // </editor-fold>

  // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
  // <editor-fold defaultstate="collapsed" desc="Getters">

  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Setters">

  // </editor-fold>
  public void reportError(Throwable error) {
	  error.printStackTrace();
	  if(error instanceof DatabaseException) {
		  for(String msg : ((DatabaseException)(error)).getErrors()){
			  System.out.println(msg);
		  }
	  }
  }
  // </editor-fold>
}
