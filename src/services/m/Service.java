package services.m;

/**
 *
 * @author tobikster
 */
public class Service implements Cloneable {
  private Integer _id;
	private ServicesGroup _group;
	private String _name;
	private Float _minPrice;
	private String _minPriceText;
	private Float _maxPrice;
	private String _maxPriceText;

  public Service() {
    
  }

	public Service(int id, ServicesGroup group, String name) {

	}

	public Service(int id, ServicesGroup group, String name, float minPrice, float maxPrice) {
		_id = id;
    _group = group;
		_name = name;
		_minPrice = minPrice;
		_maxPrice = maxPrice;
	}

  public int getId() {
    return _id;
  }

	public ServicesGroup getGroup() {
		return _group;
	}

	public Float getMaxPrice() {
		return _maxPrice;
	}

	public String getMaxPriceText() {
		return _maxPriceText;
	}

	public Float getMinPrice() {
		return _minPrice;
	}

	public String getMinPriceText() {
		return _minPriceText;
	}

	public String getName() {
		return _name;
	}

  public void setId(int id) {
    _id = id;
  }

	public void setGroup(ServicesGroup group) {
		_group = group;
	}

	public void setMaxPrice(float maxPrice) {
		_maxPrice = maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		_maxPriceText = maxPrice;
	}

	public void setMinPrice(float minPrice) {
		_minPrice = minPrice;
	}

	public void setMinPrice(String minPrice) {
		_minPriceText = minPrice;
	}

	public void setName(String name) {
		_name = name;
	}
  
  @Override
  public Service clone() {
    return new Service(_id, _group.clone(), _name, _minPrice, _maxPrice);
  }
}
