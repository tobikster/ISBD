package services.m;

import finance.m.VATRate;

/**
 *
 * @author tobikster
 */
public class ServicesGroup implements Cloneable {
	private String _name;
	private VATRate _vat;

	public ServicesGroup(String name, VATRate vat) {
		_name = name;
		_vat = vat;
	}

	public String getName() {
		return _name;
	}

	public VATRate getVat() {
		return _vat;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setVat(VATRate vat) {
		_vat = vat;
	}
  
  @Override
  public ServicesGroup clone() {
    return new ServicesGroup(_name, _vat.clone());
  }
}
