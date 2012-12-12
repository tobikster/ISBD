package finance.m;

/**
 *
 * @author tobikster
 */
public class VATRate implements Cloneable {
	private int _id;
	private double _rate; // w bazie nie jest to pole wymagane

	public VATRate() {
		this(-1, Double.NaN);
	}

	public VATRate(int id, double rate) {
		_id = id;
		_rate = rate;
	}

	public int getId() {
		return _id;
	}

	public double getRate() {
		return _rate;
	}

	public void setId(int id) {
		_id = id;
	}

	public void setRate(double rate) {
		_rate = rate;
	}

	@Override
	public String toString() {
		return (int)(_rate * 100) + " %";
	}
  
  @Override
  public VATRate clone() {
    return new VATRate(_id, _rate);
  }
}
