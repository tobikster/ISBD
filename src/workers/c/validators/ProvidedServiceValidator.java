package workers.c.validators;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;
import workers.m.ProvidedService;

/**
 *
 * @author Zjamnik
 */
public class ProvidedServiceValidator implements EntityValidator<ProvidedService>
{

	@Override
	public boolean validate(ProvidedService object) throws DatabaseException
	{
		List<String> errors = new LinkedList<>();

		if (object.getService() == null)
		{
			errors.add("Nie wprowadzono usługi, która została wykonana.");
		}

		if (object.getWorker() == null)
		{
			errors.add("Nie wprowadzono pracownika, kóry wykonał usługę.");
		}

		if (object.getDate() == null)
		{
			errors.add("Nie wprowadzono daty wykonanej usługi.");
		} else
		{
		}
		
		if (object.getValue() == Double.NaN)
		{
			errors.add("Nie wprowadzono wartości wykonanej usługi.");
		}else
		{
//			if(!ElementaryValidator.isNumber(object.getValue()))
//			{
//				errors.add("Wprowadzona wartość wykonanej usługi nie jest liczbą.");
//			}else
//			{
				if(!ElementaryValidator.minNumberValidator(Double.valueOf(object.getValue()), 0))
				{
					errors.add("Wprowadzona wartość wykonanej usługi jest mniejsza od zera.");
				}
//			}
		}


		if (!errors.isEmpty())
		{
			throw new DatabaseException(errors);
		}

		return true;
	}
}
