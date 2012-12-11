package stores.articles.m;

import stores.groups.m.ArticlesGroup;
import stores.producers.m.Producer;

/**
 *
 * @author tobikster
 */
public class Part implements Cloneable {
  private int _id;
	private ArticlesGroup _group;
	private Producer _producer;
	private String _catalogNumber;
	private String _name;
	private Double _margin;
	private Double _grossPrice;
	private Integer _count;
	private Object _picture;
        
	public Part() {
		
	}

	public Part(int id, ArticlesGroup group, Producer producer, String name, double margin, double grossPrice, int count) {
		this(id, group, producer, null, name, margin, grossPrice, count, null);
	}

	public Part(int id, ArticlesGroup group, Producer producer, String catalogNumber, String name, double margin, double grossPrice, int count, Object picture) {
		_id = id;
    _group = group;
		_producer = producer;
		_catalogNumber = catalogNumber;
		_name = name;
		_margin = margin;
		_grossPrice = grossPrice;
		_count = count;
		_picture = picture;
	}

  public int getId() {
    return _id;
  }

	public String getCatalogNumber() {
		return _catalogNumber;
	}

	public Integer getCount() {
		return _count;
	}

	public Double getGrossPrice() {
		return _grossPrice;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

	public Double getMargin() {
		return _margin;
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
    _id = id;
  }

	public void setCatalogNumber(String catalogNumber) {
		_catalogNumber = catalogNumber;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setGrossPrice(double grossPrice) {
		_grossPrice = grossPrice;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}

	public void setMargin(double margin) {
		_margin = margin;
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
  
  @Override
  public String toString() {
    return _name;
  }
  
  @Override
  public Part clone() {
    return new Part(_id, _group.clone(), _producer.clone(), _catalogNumber, _name, _margin, _grossPrice, _count, _picture);
  }
}
