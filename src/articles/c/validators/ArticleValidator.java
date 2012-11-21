package articles.c.validators;

import articles.m.Article;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ArticleValidator implements EntityValidator<Article>
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
  public boolean validate(Article object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getGroup()==null)
      errors.add("Nie podano grupy towarowej!");

    if(object.getProducer()==null)
      errors.add("Nie podano producenta!");

    if(ElementaryValidator.hasValue(object.getCatalogNumber())&&!ElementaryValidator.maxLengthValidator(object.getCatalogNumber(), 20))
      errors.add("Numer katalogowy jest za długi! Maksymalna dopuszczalna długość to 20 znaków!");

    if(!ElementaryValidator.hasValue(object.getName()))
      errors.add("Nazwa części jest wymagana!");
    else
      if(!ElementaryValidator.maxLengthValidator(object.getName(), 30))
        errors.add("Podana nazwa jest za długa! Maksymalna dopuszczalna długość to 30 znaków!");

    if(object.getMargin()==Double.NaN)
      errors.add("Nie podano marży!");
    else
      if(!ElementaryValidator.minNumberValidator(object.getMargin(), 0.0))
        errors.add("Marża nie może być liczbą ujemną!");

    if(object.getGrossPrice()==Double.NaN)
      errors.add("Nie podano ceny!");
    else
      if(!ElementaryValidator.minNumberValidator(object.getGrossPrice(), 0.0))
        errors.add("Cena brutto nie może być ujemna!");

    if(!ElementaryValidator.minNumberValidator(object.getCount(), 0.0))
      errors.add("Ilość nie może być ujemna!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);
    return errors.isEmpty();
  }
  // </editor-fold>
}