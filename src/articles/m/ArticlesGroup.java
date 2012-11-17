package articles.m;

import finance.m.VATRate;

/**
 *
 * @author tobikster
 */
public class ArticlesGroup {
	private String _name;
	private VATRate _vat;

	public ArticlesGroup(String name, VATRate vat) {
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
}
