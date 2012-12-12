package stores.articles.m;

import java.util.LinkedHashMap;
import java.util.Map;
import stores.groups.m.ArticlesGroup;

/**
 *
 * @author tobikster
 */
public class Tire implements Cloneable {
  private int _id;
	private ArticlesGroup _group;
	private Tread _tread;
	private TireSize _size;
	private LoadIndex _loadIndex;
	private SpeedIndex _speedIndex;
	private Double _margin;
  private String _marginText;
	private Double _grossPrice;
  private String _grossPriceText;
  private Map<DOT, Integer> _tireDOTs;
  private Map<DOT, String> _tireDOTsText;

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

  public Tire(int id, ArticlesGroup group, Tread tread, TireSize size, LoadIndex loadIndex, SpeedIndex speedIndex, double margin, double grossPrice, Map<DOT, Integer> tireDOTs) {
		_id = id;
    _group = group;
		_tread = tread;
		_size = size;
		_loadIndex = loadIndex;
		_speedIndex = speedIndex;
		_margin = margin;
		_grossPrice = grossPrice;
    _tireDOTs = tireDOTs;
	}

  public int getId() {
    return _id;
  }

	public Double getGrossPrice() {
		return _grossPrice;
	}

  public String getGrossPriceText() {
    return _grossPriceText;
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

  public String getMarginText() {
		return _marginText;
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
  
  public Map<DOT, Integer> getTireDOTs() {
    return _tireDOTs;
  }

  public Map<DOT, String> getTireDOTsText() {
    return _tireDOTsText;
  }

  public int getCount() {
    int iCount = 0;
    if(_tireDOTs!=null) {
      for(DOT dot : _tireDOTs.keySet()) {
        iCount += _tireDOTs.get(dot);
      }
    }
    return iCount;
  }

  public void setId(int id) {
    _id = id;
  }

	public void setGrossPrice(double grossPrice) {
		_grossPrice = grossPrice;
	}

  public void setGrossPrice(String grossPrice) {
		_grossPrice = null;
    _grossPriceText = grossPrice;
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

	public void setMargin(String margin) {
		_margin = null;
    _marginText = margin;
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
  
  public void setTireDOTs(Map<DOT, Integer> tireDOTs) {
    _tireDOTs = tireDOTs;
  }
  
  public void setTireDOTsText(Map<DOT, String> tireDOTs) {
    _tireDOTs = null;
    _tireDOTsText = tireDOTs;
  }
  
  @Override
  public Tire clone() {
    if(_tireDOTs != null) {
      Map<DOT, Integer> newTireDOTs = new LinkedHashMap<>();
      for(int i=0;i<_tireDOTs.size();i++) {
        newTireDOTs.put(((DOT)(_tireDOTs.keySet().toArray()[i])).clone(), _tireDOTs.get((DOT)(_tireDOTs.keySet().toArray()[i])));
      }
      return new Tire(_id, _group.clone(), _tread.clone(), _size.clone(), _loadIndex, _speedIndex, _margin, _grossPrice, newTireDOTs);
    }
    return new Tire(_id, _group.clone(), _tread.clone(), _size.clone(), _loadIndex, _speedIndex, _margin, _grossPrice);
  }
}
