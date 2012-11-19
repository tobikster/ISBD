package core.c;

/**
 *
 * @author Zjamnik
 */
public class ElementaryValidator
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
	public static boolean lengthValidator(String string, int minLength, int maxLength)
	{
		return string.length() >= minLength && string.length() <= maxLength;
	}

	public static boolean minLengthValidator(String string, int minLength)
	{
		return string.length() >= minLength;
	}

	public static boolean maxLengthValidator(String string, int maxLength)
	{
		return string.length() <= maxLength;
	}

	public static boolean isNumber(String data)
	{
		boolean result = true;
		try
		{
			Double.valueOf((String)data);
		} catch (Exception e)
		{
			result = false;
		}
		return result;
	}
	
	public static boolean numberRangeValidator(double number, double minValue, double maxValue)
	{
		return number >= minValue && number <= maxValue;
	}
	
	public static boolean minNumberValidator(double number, double minValue)
	{
		return number >= minValue;
	}
	
	public static boolean maxNumberValidator(double number, double maxValue)
	{
		return number >= maxValue;
	}
	
	public static boolean isSpeedFactor(String string)
	{
		boolean result = false;
		
		String[] values = {"L","M","N","P","Q","R","S","T","U","H","V","W","Y","ZR"};
		
		for (String value : values)
		{
			result = result || string.equals(value);
		}
		
		return result;
	}
	// </editor-fold>
}
