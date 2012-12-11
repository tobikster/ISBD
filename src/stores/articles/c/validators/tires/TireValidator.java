package stores.articles.c.validators.tires;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;
import stores.articles.m.Tire;

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
    if(object.getMargin()==null && !ElementaryValidator.hasValue(object.getMarginText()))
      errors.add("Nie podano marży!");
    else if(object.getMargin()==null) {
      try {
        object.setMargin(Double.parseDouble(object.getMarginText()));
      } catch(NumberFormatException ex) {
        errors.add("Marża musi być liczbą nie mniejszą od 0!");
      }
    }
    if(object.getMargin()!=null && !ElementaryValidator.minNumberValidator(object.getMargin(), 0.0))
        errors.add("Marża nie może być mniejsza od 0!");
    if(object.getGrossPrice()==null && !ElementaryValidator.hasValue(object.getGrossPriceText()))
      errors.add("Nie podano ceny brutto!");
    else if(object.getGrossPrice()==null) {
      try {
        object.setGrossPrice(Double.parseDouble(object.getGrossPriceText()));
      } catch(NumberFormatException ex) {
        errors.add("Cena brutto musi być liczbą nie mniejszą od 0!");
      }
    }
    if(object.getGrossPrice()!=null && !ElementaryValidator.minNumberValidator(object.getGrossPrice(), 0.0))
        errors.add("Cena brutto nie może być mniejsza od 0!");

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
}