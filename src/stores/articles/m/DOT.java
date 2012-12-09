package stores.articles.m;

/**
 *
 * @author tobikster
 */
public class DOT {
  private int _id;
	private String _dot;

  public DOT() {
    
  }

	public DOT(int id, String dot) {
    _id = id;
		_dot = dot;
	}

  public int getId() {
    return _id;
  }

	public String getDot() {
		return _dot;
	}

  public void setId(int id) {
    _id = id;
  }

	public void setDot(String dot) {
		_dot = dot;
	}
}
