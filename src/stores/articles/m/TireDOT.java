package stores.articles.m;

/**
 *
 * @author tobikster
 */
public class TireDOT {
	private Tire _tire;
	private DOT _dot;
	private int _count;

	public TireDOT(Tire tire, DOT dot, int count) {
		_tire = tire;
		_dot = dot;
		_count = count;
	}

	public int getCount() {
		return _count;
	}

	public DOT getDot() {
		return _dot;
	}

	public Tire getTire() {
		return _tire;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setDot(DOT dot) {
		_dot = dot;
	}

	public void setTire(Tire tire) {
		_tire = tire;
	}
}
