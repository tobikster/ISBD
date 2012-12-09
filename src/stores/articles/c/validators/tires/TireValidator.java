package stores.articles.c.validators.tires;

import stores.articles.m.Tire;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class TireValidator implements EntityValidator<Tire>
{
  @Override
  public boolean validate(Tire object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getGroup()==null)
      errors.add("Nie podano grupy towarowej!");
    if(object.getTread()==null)
      errors.add("Nie podano bieżnika!");
    if(object.getSize()==null)
      errors.add("Nie podano rozmiaru!");
    if(object.getLoadIndex()==null)
      errors.add("Nie podano indeksu nośności!");
    if(object.getSpeedIndex()==null)
      errors.add("Nie podano indeksu prędkości!");
    if(object.getMargin()==Double.NaN)
      errors.add("Nie podano marży!");
    else
      if(!ElementaryValidator.minNumberValidator(object.getMargin(), 0.0))
        errors.add("Marża nie może być mniejsza od 0!");
    if(object.getGrossPrice()==Double.NaN)
      errors.add("Nie podano ceny!");
    else
      if(!ElementaryValidator.minNumberValidator(object.getGrossPrice(), 0.0))
        errors.add("Cena nie może być mniejsza od 0!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
}