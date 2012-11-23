package finance.m;

/**
 *
 * @author tobikster
 */
public class VATRate {
  private int _id;
	private double _rate; // w bazie nie jest to pole wymagane

	public VATRate() {
		this(Double.NaN);
	}
	
	public VATRate(double rate) {
		_rate = rate;
	}

  public int getId() {
    return _id;
  }

	public double getRate() {
		return _rate;
	}

  public void setId(int id) {
    _id=id;
  }

	public void setRate(double rate) {
		_rate = rate;
	}
}
