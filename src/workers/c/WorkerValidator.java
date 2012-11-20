package workers.c;

import core.m.DatabaseException;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import java.util.LinkedList;
import java.util.List;
import workers.m.Worker;

/**
 *
 * @author Zjamnik
 */
public class WorkerValidator implements EntityValidator<Worker>
{

	@Override
	public boolean validate(Worker object) throws DatabaseException
	{
		List<String> errors = new LinkedList<>();
		
		if (!ElementaryValidator.hasValue(object.getJob()))
		{
			if (!ElementaryValidator.maxLengthValidator(object.getJob(), 20))
			{
				errors.add("Nazwy stanowiska jest za długa. Maksymalna długość wynosi 20 znaków.");
			}
		} else
		{
			errors.add("Nie podano nazwy stanowiska.");
		}
		
		if (!ElementaryValidator.hasValue(object.getName()))
		{
			if (!ElementaryValidator.maxLengthValidator(object.getName(), 20))
			{
				errors.add("Imie jest za długie. Maksymalna długość wynosi 20 znaków.");
			}
		} else
		{
			errors.add("Nie podano imienia.");
		}
		
		if (!ElementaryValidator.hasValue(object.getSurname()))
		{
			if (!ElementaryValidator.maxLengthValidator(object.getSurname(), 20))
			{
				errors.add("Nazwisko jest za długie. Maksymalna długość wynosi 20 znaków.");
			}
		} else
		{
			errors.add("Nie podano nazwiska.");
		}
		
		if (!ElementaryValidator.maxLengthValidator(object.getLogin(), 20))
		{
			errors.add("Nazwa użytkownika jest za długa. Maksymalna długość wynosi 20 znaków.");
		}
		if (!ElementaryValidator.minLengthValidator(object.getLogin(), 3))
		{
			errors.add("Nazwa użytkownika jest za krótka. Minimalna długość wynosi 3 znaki.");
		}
		
		if (!ElementaryValidator.maxLengthValidator(object.getPassword(), 20))
		{
			errors.add("Hasło jest za długie. Maksymalna długość wynosi 20 znaków.");
		}
		if (!ElementaryValidator.minLengthValidator(object.getPassword(), 3))
		{
			errors.add("Hasło jest za krótkie. Minimalna długość wynosi 3 znaki.");
		}

		
		if (!errors.isEmpty())
		{
			throw new DatabaseException(errors);
		}

		return true;
	}
}
