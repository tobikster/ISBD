package stores.articles.m;

import stores.groups.m.ArticlesGroup;

/**
 *
 * @author tobikster
 */
public class Tire {
  private int _id;
	private ArticlesGroup _group;
	private Tread _tread;
	private TireSize _size;
	private LoadIndex _loadIndex;
	private SpeedIndex _speedIndex;
	private Double _margin;
	private Double _grossPrice;

	public Tire() {
    
  }

  public Tire(int id, ArticlesGroup group, Tread tread, TireSize size, LoadIndex loadIndex, SpeedIndex speedIndex, double margin, double grossPrice) {
		_id = id;
    _group = group;
		_tread = tread;
		_size = size;
		_loadIndex = loadIndex;
		_speedIndex = speedIndex;
		_margin = margin;
		_grossPrice = grossPrice;
	}

  public int getId() {
    return _id;
  }

	public Double getGrossPrice() {
		return _grossPrice;
	}

  public Double getNetPrice() {
    if(_grossPrice!=null && _group!=null && _group.getVat()!=null)
      return _grossPrice/(1+_group.getVat().getRate());
    return null;
  }

	public ArticlesGroup getGroup() {
		return _group;
	}

	public LoadIndex getLoadIndex() {
		return _loadIndex;
	}

	public Double getMargin() {
		return _margin;
	}

	public TireSize getSize() {
		return _size;
	}

	public SpeedIndex getSpeedIndex() {
		return _speedIndex;
	}

	public Tread getTread() {
		return _tread;
	}

  public void setId(int id) {
    _id = id;
  }

	public void setGrossPrice(double grossPrice) {
		_grossPrice = grossPrice;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}

	public void setLoadIndex(LoadIndex loadIndex) {
		_loadIndex = loadIndex;
	}

	public void setMargin(double margin) {
		_margin = margin;
	}

	public void setSize(TireSize size) {
		_size = size;
	}

	public void setSpeedIndex(SpeedIndex speedIndex) {
		_speedIndex = speedIndex;
	}

	public void setTread(Tread tread) {
		_tread = tread;
	}
}
