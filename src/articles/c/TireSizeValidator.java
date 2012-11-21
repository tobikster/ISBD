package articles.c;

import articles.m.TireSize;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class TireSizeValidator implements EntityValidator<TireSize> {
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
	public boolean validate(TireSize object) throws DatabaseException {
		List<String> errors = new LinkedList<>();
		
		if(!ElementaryValidator.hasValue(object.getWidth())) {
			errors.add("Nie podano szerokości!");
		}
		else {
			if(object.getWidth().matches("^[0-9]{1,3}$")) {
				errors.add("Podana wartość szerokości jest niepoprawna! Dozwolona wartość: od 1 do 3 cyfr!");
			}
		}
		
		if(!ElementaryValidator.hasValue(object.getProfile())) {
			errors.add("Nie podano profilu!");
		}
		else {
			if(object.getWidth().matches("^[0-9]{1,2}$")) {
				errors.add("Podana wartość profilu jest niepoprawna! Dozwolona wartość: od 1 do 2 cyfr!");
			}
		}
		if(!ElementaryValidator.hasValue(object.getDiameter())) {
			errors.add("Nie podano średnicy!");
		}
		else {
			if(object.getWidth().matches("^[0-9]{1,4}$")) {
				errors.add("Podana wartość średnicy jest niepoprawna! Dozwolona wartość: od 1 do 4 cyfr!");
			}
		}
		
		if(!errors.isEmpty()) {
			throw new DatabaseException(errors);
		}
		
		return errors.isEmpty();
	}
	// </editor-fold>
}