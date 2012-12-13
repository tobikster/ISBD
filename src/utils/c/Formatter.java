package utils.c;

import java.text.DecimalFormat;

public abstract class Formatter extends DecimalFormat
{
  public static String formatPercent(double value) {
    return new DecimalFormat("0.00 %").format(value/100);
  }

  public static String formatPrice(double value) {
    return new DecimalFormat("0.00 zł").format(value);
  }
}