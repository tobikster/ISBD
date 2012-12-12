package stores.groups.m;

import finance.m.VATRate;
import java.util.ArrayList;
import java.util.List;
import stores.articles.m.ArticleAttribute;

/**
 *
 * @author tobikster
 */
public class ArticlesGroup implements Cloneable {
	private int _code;
	private String _name;
	private ArticlesGroupType _type;
	private VATRate _vat;
//	private ArticlesGroup _parentGroup;
	private List<ArticleAttribute> _attributes;

	public ArticlesGroup() {
		this(-1, null, null, null);
	}

	public ArticlesGroup(int code, String name, ArticlesGroupType type, VATRate vat) {
		this(code, name, type, vat, null);
	}

	public ArticlesGroup(int code, String name, ArticlesGroupType type, VATRate vat, List<ArticleAttribute> attributes) {
		_code = code;
		_name = name;
		_type = type;
		_vat = vat;
		_attributes = attributes;
	}

	public int getCode() {
		return _code;
	}

	public String getName() {
		return _name;
	}

	public void setType(ArticlesGroupType type) {
		_type = type;
	}

	public VATRate getVat() {
		return _vat;
	}

	public List<ArticleAttribute> getAttributes() {
		return _attributes;
	}

	public void setCode(int code) {
		_code = code;
	}

	public void setName(String name) {
		_name = name;
	}

	public ArticlesGroupType getType() {
		return _type;
	}

	public void setVat(VATRate vat) {
		_vat = vat;
	}

	public void setAttributes(List<ArticleAttribute> attributes) {
		_attributes = attributes;
	}

	public boolean addAttribute(ArticleAttribute attribute) {
		boolean result = true;
		for (ArticleAttribute attr : _attributes) {
			if (attr.equals(attribute)) {
				result = false;
				break;
			}
		}
		if (result) {
			result = _attributes.add(attribute);
		}
		return result;
	}

	public boolean removeAttribute(ArticleAttribute attribute) {
		return _attributes.remove(attribute);
	}

	public void clearAttributes() {
		_attributes.clear();
	}

	@Override
	public String toString() {
		return _name;
	}
  
  @Override
  public ArticlesGroup clone() {
    if(_attributes!=null) {
      List<ArticleAttribute> newAttributes = new ArrayList<>();
      for(ArticleAttribute attribute : _attributes) {
        newAttributes.add(attribute.clone());
      }
      return new ArticlesGroup(_code, _name, _type, _vat.clone(), newAttributes);
    }
    return new ArticlesGroup(_code, _name, _type, _vat.clone());
  }
}
