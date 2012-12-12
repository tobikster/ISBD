package stores.articles.c.validators.tires;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;
import stores.articles.m.DOT;

public class DOTValidator implements EntityValidator<DOT>
{
  @Override
  public boolean validate(DOT object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(!ElementaryValidator.hasValue(object.getDot()))
      errors.add("Nie podano wartości DOTu!");
    else
      if(!object.getDot().matches("^[0-9]{3,4}$"))
        errors.add("Podano niepoprawną wartość DOTu! Musi się on składać z 3 lub 4 cyfr!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
}