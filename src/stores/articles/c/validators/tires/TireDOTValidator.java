package stores.articles.c.validators.tires;

import stores.articles.m.TireDOT;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class TireDOTValidator implements EntityValidator<TireDOT>
{
  // <editor-fold defaultstate="collapsed" desc="Object variables">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Creating object">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
  // <editor-fold defaultstate="collapsed" desc="Getters">
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Setters">
  // </editor-fold>
  @Override
  public boolean validate(TireDOT object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getDot()==null)
      errors.add("Nie podano DOTu!");
    if(object.getTire()==null)
      errors.add("Nie podano opony!");
    if(object.getCount()<0)
      errors.add("Podana ilość jest mniejsza od 0!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
  // </editor-fold>
}