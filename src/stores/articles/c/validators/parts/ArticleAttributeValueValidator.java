package stores.articles.c.validators.parts;

import stores.articles.m.ArticleAttributeValue;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ArticleAttributeValueValidator implements EntityValidator<ArticleAttributeValue>
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
  public boolean validate(ArticleAttributeValue object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getArticle()==null)
      errors.add("Nie podano części!");

    if(object.getAttribute()==null)
      errors.add("Nie podano atrybutu!");

    if(!ElementaryValidator.hasValue(object.getValue()))
      errors.add("Nie podano wartości atrybutu!");
    else
      if(ElementaryValidator.maxLengthValidator(object.getValue(), 100))
        errors.add("Przekroczono dopuszczalną liczbę znaków! Wartość atrybutu może zawierać co najwyżej 100 znaków!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
  // </editor-fold>
}