package stores.articles.c.validators.tires;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import stores.articles.m.DOT;
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

    if(object.getTireDOTs()==null && object.getTireDOTsText()!=null) {
      Map<DOT, Integer> tireDOTs = new LinkedHashMap<>();
      DOT DOTs[] = new DOT[object.getTireDOTsText().keySet().size()];
      DOTs = object.getTireDOTsText().keySet().toArray(DOTs);
      for(int i=0;i<object.getTireDOTsText().size();i++) {
        try {
          tireDOTs.put(DOTs[i], Integer.parseInt(object.getTireDOTsText().get(DOTs[i])));
        } catch(NumberFormatException ex) {
          errors.add("Ilość opon dla numeru DOT "+DOTs[i]+" jest niepoprawna!");
        }
      }
      if(tireDOTs.size()==object.getTireDOTsText().size())
        object.setTireDOTs(tireDOTs);
    }
    if(object.getTireDOTs()!=null) {
      EntityValidator<DOT> validator = new DOTValidator();
      for(DOT dot : object.getTireDOTs().keySet()) {
        try {
          validator.validate(dot);
        } catch(DatabaseException ex) {
          errors.add("Numer DOT '"+dot+"' jest niepoprawny!");
          errors.addAll(ex.getErrors());
        }
      }
    }

    if(!errors.isEmpty())
      throw new DatabaseException(errors);

    return errors.isEmpty();
  }
}