package services.m;

import finance.m.VATRate;

/**
 *
 * @author tobikster
 */
public class ServicesGroup implements Cloneable {
	private Integer _id;
  private String _name;
	private VATRate _vat;

  public ServicesGroup() {
    
  }

	public ServicesGroup(int id, String name, VATRate vat) {
		_id = id;
    _name = name;
		_vat = vat;
	}

  public Integer getId() {
    return _id;
  }

	public String getName() {
		return _name;
	}

	public VATRate getVat() {
		return _vat;
	}

  public void setId(int id) {
    _id = id;
  }

	public void setName(String name) {
		_name = name;
	}

	public void setVat(VATRate vat) {
		_vat = vat;
	}

  @Override
  public String toString() {
    return _name;
  }

  @Override
  public ServicesGroup clone() {
    return new ServicesGroup(_id.intValue(), _name, _vat!=null?_vat.clone():null);
  }
}
