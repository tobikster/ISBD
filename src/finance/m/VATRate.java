package finance.m;

/**
 *
 * @author tobikster
 */
public class VATRate implements Cloneable {
	private Integer _id;
	private Float _rate; // w bazie nie jest to pole wymagane

	public VATRate() {
		this(null, null);
	}

	public VATRate(Integer id, Float rate) {
		_id = id;
		_rate = rate;
	}

	public Integer getId() {
		return _id;
	}

	public Float getRate() {
		return _rate;
	}

	public void setId(int id) {
		_id = id;
	}

	public void setRate(float rate) {
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
