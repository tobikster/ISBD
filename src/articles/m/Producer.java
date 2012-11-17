package articles.m;

/**
 *
 * @author tobikster
 */
public class Producer {
	private String _name;
	private Object _logo;

	public Producer(String name) {
		this(name, null);
	}

	public Producer(String name, Object logo) {
		_name = name;
		_logo = logo;
	}

	public Object getLogo() {
		return _logo;
	}

	public String getName() {
		return _name;
	}

	public void setLogo(Object logo) {
		_logo = logo;
	}

	public void setName(String name) {
		_name = name;
	}
	
}
