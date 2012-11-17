package articles.m;

/**
 *
 * @author tobikster
 */
public class ArticlesGroupAttribute {
	private ArticlesGroup _group;
	private ArticleAttribute _attribute;

	public ArticlesGroupAttribute(ArticlesGroup group, ArticleAttribute attribute) {
		_group = group;
		_attribute = attribute;
	}

	public ArticleAttribute getAttribute() {
		return _attribute;
	}

	public ArticlesGroup getGroup() {
		return _group;
	}

	public void setAttribute(ArticleAttribute attribute) {
		_attribute = attribute;
	}

	public void setGroup(ArticlesGroup group) {
		_group = group;
	}
}
