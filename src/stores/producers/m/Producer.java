package stores.producers.m;

/**
 *
 * @author tobikster
 */
public class Producer implements Cloneable {
  private int _id;
	private String _name;
	private Object _logo;

  public Producer() {
    
  }

	public Producer(int id, String name) {
		this(id, name, null);
	}

	public Producer(int id, String name, Object logo) {
		_id = id;
    _name = name;
		_logo = logo;
	}

  public int getId() {
    return _id;
  }

	public Object getLogo() {
		return _logo;
	}

	public String getName() {
		return _name;
	}

  public void setId(int id) {
    _id=id;
  }

	public void setLogo(Object logo) {
		_logo = logo;
	}

	public void setName(String name) {
		_name = name;
	}

  @Override
  public String toString() {
    return _name;
  }
  
  @Override
  public Producer clone() {
    return new Producer(_id, _name, _logo);
  }
}
