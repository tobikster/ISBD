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
import services.m.ServicesGroup;

/**
 *
 * @author Zjamnik
 */
public class ServicesGroupValidator implements EntityValidator<ServicesGroup>
{

	@Override
	public boolean validate(ServicesGroup object) throws DatabaseException
	{
		List<String> errors = new LinkedList<>();
		
		if(!ElementaryValidator.hasValue(object.getName()))
		{
			errors.add("Nie podano nazwy działu usług.");
		}else
		{
			if(!ElementaryValidator.maxLengthValidator(object.getName(), 30));
		}
		
		if(object.getVat() == null)
		{
			errors.add("Nie podano stawki VAT dla działu usług.");
		}


		if (!errors.isEmpty())
		{
			throw new DatabaseException(errors);
		}

		return true;
	}
	
}
