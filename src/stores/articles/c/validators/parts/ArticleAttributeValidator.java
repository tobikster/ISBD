package stores.articles.c.validators.parts;

import stores.articles.m.ArticleAttribute;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ArticleAttributeValidator implements EntityValidator<ArticleAttribute>
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
  public boolean validate(ArticleAttribute object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(!ElementaryValidator.hasValue(object.getName()))
      errors.add("Nazwa atrybutu jest wymagana!");
    else
      if(!ElementaryValidator.maxLengthValidator(object.getName(), 40))
        errors.add("Nazwa nie może być dłuższa niż 40 znaków!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
  // </editor-fold>
}