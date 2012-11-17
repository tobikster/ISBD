package services.m;

/**
 *
 * @author tobikster
 */
public class Service {
	private ServicesGroup _group;
	private String _name;
	private double _minPrice;
	private double _maxPrice;

	public Service(ServicesGroup group, String name) {
		this(group, name, Double.NaN, Double.NaN);
	}

	public Service(ServicesGroup group, String name, double minPrice, double maxPrice) {
		_group = group;
		_name = name;
		_minPrice = minPrice;
		_maxPrice = maxPrice;
	}

	public ServicesGroup getGroup() {
		return _group;
	}

	public double getMaxPrice() {
		return _maxPrice;
	}

	public double getMinPrice() {
		return _minPrice;
	}

	public String getName() {
		return _name;
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
}
