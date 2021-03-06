package stores.producers.c.validators;

import stores.producers.m.Producer;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ProducerValidator implements EntityValidator<Producer>
{
  @Override
  public boolean validate(Producer object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(!ElementaryValidator.hasValue(object.getName()))
      errors.add("Nie podano nazwy producenta!");
    else
      if(!ElementaryValidator.maxLengthValidator(object.getName(), 20))
        errors.add("Podana nazwa producenta jest za długa! Maksymalna dopuszczalna długość to 20 znaków!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
}