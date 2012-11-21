package articles.c.validators;

import articles.m.ArticlesGroupAttribute;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ArticlesGroupAttributeValidator implements EntityValidator<ArticlesGroupAttribute>
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
  public boolean validate(ArticlesGroupAttribute object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getGroup()==null)
      errors.add("Grupa towarowa jest wymagana!");

    if(object.getAttribute()==null)
      errors.add("Atrybut jest wymagany!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
  // </editor-fold>
}