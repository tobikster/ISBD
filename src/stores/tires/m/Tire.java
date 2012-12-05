package stores.tires.m;

import stores.groups.m.ArticlesGroup;

/**
 *
 * @author tobikster
 */
public class Tire {
	private ArticlesGroup _group;
	private Tread _tread;
	private TireSize _size;
	private double _loadIndex;
	private String _speedIndex;
	private double _margin;
	private double _grossPrice;

	public Tire() {
    
  }

  public Tire(ArticlesGroup group, Tread tread, TireSize size, double loadIndex, String speedIndex, double margin, double grossPrice) {
		_group = group;
		_tread = tread;
		_size = size;
		_loadIndex = loadIndex;
		_speedIndex = speedIndex;
		_margin = margin;
		_grossPrice = grossPrice;
	}

	public double getGrossPrice() {
		return _grossPrice;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

	public double getLoadIndex() {
		return _loadIndex;
	}

	public double getMargin() {
		return _margin;
	}

	public TireSize getSize() {
		return _size;
	}

	public String getSpeedIndex() {
		return _speedIndex;
	}

	public Tread getTread() {
		return _tread;
	}

	public void setGrossPrice(double grossPrice) {
		_grossPrice = grossPrice;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}

	public void setLoadIndex(double loadIndex) {
		_loadIndex = loadIndex;
	}

	public void setMargin(double margin) {
		_margin = margin;
	}

	public void setSize(TireSize size) {
		_size = size;
	}

	public void setSpeedIndex(String speedIndex) {
		_speedIndex = speedIndex;
	}

	public void setTread(Tread tread) {
		_tread = tread;
	}
}
