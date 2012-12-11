package stores.articles.m;

import stores.producers.m.Producer;

/**
 *
 * @author tobikster
 */
public class Tread implements Cloneable {
  private int _id;
	private Producer _producer;
	private String _name;
	private String _picture; // w bazie jest jako tekst

  public Tread() {
    
  }

	public Tread(int id, Producer producer, String name) {
		this(id, producer, name, null);
	}

	public Tread(int id, Producer producer, String name, String picture) {
		_id = id;
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

	public String getPicture() {
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

	public void setPicture(String picture) {
		_picture = picture;
	}

	public void setProducer(Producer producer) {
		_producer = producer;
	}
  
  @Override
  public String toString() {
    return _name;
  }
  
  @Override
  public Tread clone() {
    return new Tread(_id, _producer.clone(), _name, _picture);
  }
}
