package articles.c.validators;

import articles.m.ArticlesGroup;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ArticlesGroupValidator implements EntityValidator<ArticlesGroup>
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
  public boolean validate(ArticlesGroup object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(!ElementaryValidator.hasValue(object.getName()))
      errors.add("Nazwa grupy jest wymagana!");
    else
      if(!ElementaryValidator.maxLengthValidator(object.getName(), 30))
        errors.add("Nazwa grupy może mieć co najwyżej 30 znaków!");

    if(object.getVat()==null)
      errors.add("Stawka VAT jest wymagana!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
  // </editor-fold>
}