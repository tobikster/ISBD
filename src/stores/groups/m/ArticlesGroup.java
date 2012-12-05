package stores.groups.m;

import finance.m.VATRate;
import java.util.HashSet;
import java.util.Set;
import stores.articles.m.ArticleAttribute;

/**
 *
 * @author tobikster
 */
public class ArticlesGroup {
	private int _code;
	private String _name;
  private ArticlesGroupType _type;
	private VATRate _vat;
	private ArticlesGroup _parentGroup;
	private Set<ArticleAttribute> _attributes;

	public ArticlesGroup() {
		this(-1, null, null, null);
	}

	public ArticlesGroup(int code, String name, ArticlesGroupType type, VATRate vat) {
		this(code, name, type, vat, null, new HashSet<ArticleAttribute>());
	}

	public ArticlesGroup(int code, String name, ArticlesGroupType type, VATRate vat, ArticlesGroup parentGroup, Set<ArticleAttribute> attributes) {
		_code = code;
    _name = name;
    _type = type;
		_vat = vat;
		_parentGroup = parentGroup;
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
	
	public ArticlesGroup getParentGroup() {
		return _parentGroup;
	}

	public Set<ArticleAttribute> getAttributes() {
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
	
	public void setParentGroup(ArticlesGroup parentGroup) {
		_parentGroup = parentGroup;
	}

	public void setAttributes(Set<ArticleAttribute> attributes) {
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
}
