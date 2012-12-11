package services.m;

/**
 *
 * @author tobikster
 */
public class Service implements Cloneable {
  private int _id;
	private ServicesGroup _group;
	private String _name;
	private Double _minPrice;
	private Double _maxPrice;

  public Service() {
    
  }

	public Service(int id, ServicesGroup group, String name) {
		this(id, group, name, Double.NaN, Double.NaN);
	}

	public Service(int id, ServicesGroup group, String name, double minPrice, double maxPrice) {
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

	public Double getMaxPrice() {
		return _maxPrice;
	}

	public Double getMinPrice() {
		return _minPrice;
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

	public void setMaxPrice(double maxPrice) {
		_maxPrice = maxPrice;
	}

	public void setMinPrice(double minPrice) {
		_minPrice = minPrice;
	}

	public void setName(String name) {
		_name = name;
	}
  
  @Override
  public Service clone() {
    return new Service(_id, _group.clone(), _name, _minPrice, _maxPrice);
  }
}
