package stores.groups.m;

import stores.articles.m.ArticleAttribute;

/**
 *
 * @author tobikster
 */
public class ArticlesGroupAttribute implements Cloneable {
	private int _id;
	private ArticlesGroup _group;
	private ArticleAttribute _attribute;
	
	public ArticlesGroupAttribute() {
		this(-1, null, null);
	}

	public ArticlesGroupAttribute(int id, ArticlesGroup group, ArticleAttribute attribute) {
		_id = id;
		_group = group;
		_attribute = attribute;
	}

  public int getId() {
    return _id;
  }

	public ArticleAttribute getAttribute() {
		return _attribute;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

  public void setId(int id) {
    _id=id;
  }

	public void setAttribute(ArticleAttribute attribute) {
		_attribute = attribute;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}
  
  @Override
  public ArticlesGroupAttribute clone() {
    return new ArticlesGroupAttribute(_id, _group.clone(), _attribute.clone());
  }
}
