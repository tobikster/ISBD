package stores.articles.m;


/**
 *
 * @author tobikster
 */
public class ArticleAttribute implements Cloneable {
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
		boolean result = (obj instanceof ArticleAttribute) ? this.getName().equals(((ArticleAttribute)(obj)).getName()) : false;
		System.out.println(result);
		return result;
	}
  
  @Override
  public ArticleAttribute clone() {
    return new ArticleAttribute(_id, _name);
  }
}
