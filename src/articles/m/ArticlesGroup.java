package articles.m;

import finance.m.VATRate;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tobikster
 */
public class ArticlesGroup {
	private int _id;
	private int _code;
	private String _name;
	private VATRate _vat;
	private ArticlesGroup _parentGroup;
	private Set<ArticleAttribute> _attributes;

	public ArticlesGroup() {
		this(-1, null, null);
	}

	public ArticlesGroup(int id, String name, VATRate vat) {
		this(id, name, vat, null, new HashSet<ArticleAttribute>());
	}

	public ArticlesGroup(int id, String name, VATRate vat, ArticlesGroup parentGroup, Set<ArticleAttribute> attributes) {
		_id = id;
		_name = name;
		_vat = vat;
		_parentGroup = parentGroup;
		_attributes = attributes;
	}

	public int getId() {
		return _id;
	}

	public int getCode() {
		return _code;
	}

	public String getName() {
		return _name;
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

	public void setId(int id) {
		_id = id;
	}

	public void setCode(int code) {
		_code = code;
	}

	public void setName(String name) {
		_name = name;
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
}
