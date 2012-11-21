package articles.m;

/**
 *
 * @author tobikster
 */
public class Article {
  private int _id;
	private ArticlesGroup _group;
	private Producer _producer;
	private String _catalogNumber;
	private String _name;
	private double _margin;
	private double _grossPrice;
	private float _count;
	private Object _picture;
        
	public Article() {
		this(null, null, null, null, Double.NaN, Double.NaN, 0, null);
	}

	public Article(ArticlesGroup group, Producer producer, String name, double margin, double grossPrice, int count) {
		this(group, producer, null, name, margin, grossPrice, count, null);
	}

	public Article(ArticlesGroup group, Producer producer, String catalogNumber, String name, double margin, double grossPrice, int count, Object picture) {
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

	public float getCount() {
		return _count;
	}

	public double getGrossPrice() {
		return _grossPrice;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

	public double getMargin() {
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

	public void setCount(float count) {
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
}
