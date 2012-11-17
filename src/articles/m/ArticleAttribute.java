package articles.m;

/**
 *
 * @author tobikster
 */
public class ArticleAttribute {
	private String _name;

	public ArticleAttribute() {
		this(null);
	}

	public ArticleAttribute(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
}
