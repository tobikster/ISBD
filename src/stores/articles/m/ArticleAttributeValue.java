package stores.articles.m;

import stores.groups.m.ArticlesGroupAttribute;

/**
 *
 * @author tobikster
 */
public class ArticleAttributeValue implements Cloneable {
	private Part _article;
	private ArticlesGroupAttribute _attribute;
	private String _value;

	public ArticleAttributeValue(Part article, ArticlesGroupAttribute attribute, String value) {
		_article = article;
		_attribute = attribute;
		_value = value;
	}

	public Part getArticle() {
		return _article;
	}

	public ArticlesGroupAttribute getAttribute() {
		return _attribute;
	}

	public String getValue() {
		return _value;
	}

	public void setArticle(Part article) {
		_article = article;
	}

	public void setAttribute(ArticlesGroupAttribute attribute) {
		_attribute = attribute;
	}

	public void setValue(String value) {
		_value = value;
	}
  
  @Override
  public ArticleAttributeValue clone() {
    return new ArticleAttributeValue(_article.clone(), _attribute.clone(), _value);
  }
}
