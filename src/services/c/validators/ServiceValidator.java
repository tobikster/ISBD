/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package services.c.validators;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;
import services.m.Service;

/**
 *
 * @author Zjamnik
 */
public class ServiceValidator implements EntityValidator<Service>
{

	@Override
	public boolean validate(Service object) throws DatabaseException
	{
		List<String> errors = new LinkedList<>();
		
		if(object.getGroup() == null) {
			errors.add("Nie wprowadzono działu usług dla usługi.");
		}

		if(!ElementaryValidator.hasValue(object.getName())) {
			errors.add("Nie wprowadzono nazwy usługi.");
		} else if(!ElementaryValidator.maxLengthValidator(object.getName(), 40)) {
				errors.add("Wprowadzona nazwa uśługi jest zbyt długa (maksymalna długość wynosi 40 znaków).");
		}
		
		if(!ElementaryValidator.minNumberValidator(object.getMinPrice(), 0)) {
			errors.add("Minimalna cena usługi nie może być mniejsza od zera.");
		}
    
    if(!ElementaryValidator.minNumberValidator(object.getMaxPrice(), 0)) {
			errors.add("Maksymalna cena usługi nie może być mniejsza od zera.");
		}
		if(object.getMinPrice() > object.getMaxPrice()) {
			errors.add("Minimalna cena usługi nie może być większa od ceny maksymalnej.");
		}

		if(!errors.isEmpty()) {
			throw new DatabaseException(errors);
		}

		return true;
	}
	
}
