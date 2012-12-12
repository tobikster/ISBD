package stores.articles.c.validators.tires;

import stores.articles.m.Tread;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class TreadValidator implements EntityValidator<Tread>
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
  public boolean validate(Tread object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getProducer()==null)
      errors.add("Nie podano producenta!");
    if(!ElementaryValidator.hasValue(object.getName()))
      errors.add("Nie podano nazwy!");
    else
      if(!ElementaryValidator.maxLengthValidator(object.getName(), 30))
        errors.add("Podana nazwa jest za długa! Dopuszczalna długość: 30 znaków!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
  // </editor-fold>
}