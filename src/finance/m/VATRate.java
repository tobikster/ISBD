package finance.m;

/**
 *
 * @author tobikster
 */
public class VATRate {
	private double _rate; // w bazie nie jest to pole wymagane

	public VATRate() {
		this(Double.NaN);
	}
	
	public VATRate(double rate) {
		_rate = rate;
	}

	public double getRate() {
		return _rate;
	}

	public void setRate(double rate) {
		_rate = rate;
	}
}
