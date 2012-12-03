package articles.m;


/**
 *
 * @author tobikster
 */
public class ArticleAttribute {
	private int _id;
	private String _name;

	public ArticleAttribute() {
		this(-1, null);
	}

	public ArticleAttribute(int id, String name) {
		_id = id;
		_name = name;
	}

	public int getId() {
		return _id;
	}
	
	public String getName() {
		return _name;
	}
	
	public void setId(int id) {
		_id = id;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ArticleAttribute) ? this.getName().equals(((ArticleAttribute)(obj)).getName()) : false;
	}
}
