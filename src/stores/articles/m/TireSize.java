package stores.articles.m;

/**
 *
 * @author tobikster
 */
public class TireSize implements Cloneable {
  private int _id;
	private String _width;
	private String _profile;
	private String _diameter;

  public TireSize() {
    
  }

	public TireSize(int id, String width, String profile, String diameter) {
		_id = id;
    _width = width;
		_profile = profile;
		_diameter = diameter;
	}

  public int getId() {
    return _id;
  }

	public String getDiameter() {
		return _diameter;
	}

	public String getProfile() {
		return _profile;
	}

	public String getWidth() {
		return _width;
	}

  public void setId(int id) {
    _id = id;
  }

	public void setDiameter(String diameter) {
		_diameter = diameter;
	}

	public void setProfile(String profile) {
		_profile = profile;
	}

	public void setWidth(String width) {
		_width = width;
	}
  
  @Override
  public String toString() {
    return _width+"/"+_profile+"/"+_diameter;
  }

  @Override
  public TireSize clone() {
    return new TireSize(_id, _width, _profile, _diameter);
  }
}
