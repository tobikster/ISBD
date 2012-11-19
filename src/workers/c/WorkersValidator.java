/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package workers.c;

import core.c.DatabaseException;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import java.util.LinkedList;
import java.util.List;
import workers.m.Worker;

/**
 *
 * @author Zjamnik
 */
public class WorkersValidator implements EntityValidator<Worker>
{

	@Override
	public boolean validate(Worker object) throws DatabaseException
	{
		List<String> errors = new LinkedList<>();
		
		if(!ElementaryValidator.maxLengthValidator(object.getJob(), 20))
		{
			errors.add("Maksymalna długość nazwy stanowiska wynosi 20 znaków.");
		}
		if(!ElementaryValidator.maxLengthValidator(object.getName(), 20))
		{
			errors.add("Maksymalna długość imienia wynosi 20 znaków.");
		}
		if(!ElementaryValidator.maxLengthValidator(object.getSurname(), 20))
		{
			errors.add("Maksymalna długość nazwiska wynosi 20 znaków.");
		}
		if(!ElementaryValidator.maxLengthValidator(object.getLogin(), 20))
		{
			errors.add("Maksymalna długość nazwy użytkownika wynosi 20 znaków.");
		}
		if(!ElementaryValidator.maxLengthValidator(object.getPassword(), 20))
		{
			errors.add("Maksymalna długość hasła wynosi 20 znaków.");
		}
		
		if(!errors.isEmpty())
		{
			throw new DatabaseException(errors);
		}
		
		return true;
	}
}
