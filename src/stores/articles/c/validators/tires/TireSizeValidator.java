package stores.articles.c.validators.tires;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;
import stores.articles.m.TireSize;

public class TireSizeValidator implements EntityValidator<TireSize>
{
  @Override
  public boolean validate(TireSize object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(!ElementaryValidator.hasValue(object.getWidth()))
      errors.add("Nie podano szerokości!");
    else
      if(!object.getWidth().matches("^[0-9]{2,3}$"))
        errors.add("Podana wartość szerokości jest niepoprawna! Dozwolona wartość: od 2 do 3 cyfr!");

    if(!ElementaryValidator.hasValue(object.getProfile()))
      errors.add("Nie podano profilu!");
    else
      if(!object.getProfile().matches("^[0-9]{2}$"))
        errors.add("Podana wartość profilu jest niepoprawna! Dozwolona wartość: 2 cyfry!");
    if(!ElementaryValidator.hasValue(object.getDiameter()))
      errors.add("Nie podano średnicy!");
    else
      if(!object.getDiameter().matches("^[0-9]{1,2}([0-9]|(,[0-9]))?$"))
        errors.add("Podana wartość średnicy jest niepoprawna! Dozwolona wartość: liczba od 0 do 999,9 (maksymalnie 1 miejsce po przecinku)!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
}