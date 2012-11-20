/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.c;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import finance.m.VATRate;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Zjamnik
 */
public class VATRateValidator implements EntityValidator<VATRate>
{

	@Override
	public boolean validate(VATRate object) throws DatabaseException
	{
		List<String> errors = new LinkedList<>();

		if (!ElementaryValidator.hasValue(object.getRate()))
		{
			errors.add("Nie wprowadzono wartości podaktu VAT.");
		} else
		{
//			if(!ElementaryValidator.isNumber(object.getRate()))
//			{
//				errors.add("Wprowadzona wartość podatku VAT nie jest liczbą.");
//			}else
//			{
			if (!ElementaryValidator.minNumberValidator(Double.valueOf(object.getRate()), 0))
			{
				errors.add("Wprowadzona wartość podatku VAT jest mniejsza od zera.");
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
