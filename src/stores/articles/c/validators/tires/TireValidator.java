package stores.articles.c.validators.tires;

import stores.articles.m.Tire;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class TireValidator implements EntityValidator<Tire>
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
  public boolean validate(Tire object) throws DatabaseException
  {
    List<String> errors=new LinkedList<>();

    if(object.getGroup()==null)
      errors.add("Nie podano grupy towarowej!");
    if(object.getTread()==null)
      errors.add("Nie podano bieżnika!");
    if(object.getSize()==null)
      errors.add("Nie podano rozmiaru!");
    if(object.getLoadIndex()==Double.NaN)
      errors.add("Nie podano indeksu nośności!");
    else
      if(!ElementaryValidator.numberRangeValidator(object.getLoadIndex(), 0.0, 200.0))
        errors.add("Podany indeks nośności jest z poza zakresu! Dozwolone wartości: pomiędzy 0.0 a 200.0!");
    if(!ElementaryValidator.hasValue(object.getSpeedIndex()))
      errors.add("Nie podano indeksu prędkości!");
    else
      if(!ElementaryValidator.isSpeedFactor(object.getSpeedIndex()))
        errors.add("Podana wartość indeksu prędkości jest niedozwolona! Dopuszczalne wartości: L, M, N, P, Q, R, S, T, U, H, V, W, Y, ZR");
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
  // </editor-fold>
}