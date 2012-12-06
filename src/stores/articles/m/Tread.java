package stores.articles.m;

import stores.producers.m.Producer;

/**
 *
 * @author tobikster
 */
public class Tread {
  private int _id;
	private Producer _producer;
	private String _name;
	private Object _picture; // w bazie jest jako tekst

  public Tread() {
    
  }

	public Tread(Producer producer, String name) {
		this(producer, name, null);
	}

	public Tread(Producer producer, String name, Object picture) {
		_producer = producer;
		_name = name;
		_picture = picture;
	}

  public int getId() {
    return _id;
  }

	public String getName() {
		return _name;
	}

	public Object getPicture() {
		return _picture;
	}

	public Producer getProducer() {
		return _producer;
	}

  public void setId(int id) {
    _id=id;
  }

  public void setName(String name) {
		_name = name;
	}

	public void setPicture(Object picture) {
		_picture = picture;
	}

	public void setProducer(Producer producer) {
		_producer = producer;
	}
}
