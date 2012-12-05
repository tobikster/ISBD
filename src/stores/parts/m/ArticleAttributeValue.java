package stores.parts.m;

import stores.groups.m.ArticlesGroupAttribute;

/**
 *
 * @author tobikster
 */
public class ArticleAttributeValue {
	private Article _article;
	private ArticlesGroupAttribute _attribute;
	private String _value;

	public ArticleAttributeValue(Article article, ArticlesGroupAttribute attribute, String value) {
		_article = article;
		_attribute = attribute;
		_value = value;
	}

	public Article getArticle() {
		return _article;
	}

	public ArticlesGroupAttribute getAttribute() {
		return _attribute;
	}

	public String getValue() {
		return _value;
	}

	public void setArticle(Article article) {
		_article = article;
	}

	public void setAttribute(ArticlesGroupAttribute attribute) {
		_attribute = attribute;
	}

	public void setValue(String value) {
		_value = value;
	}
}
