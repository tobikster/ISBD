package articles.m;

/**
 *
 * @author tobikster
 */
public class TireSize {
	private String _width;
	private String _profile;
	private String _diameter;

	public TireSize(String width, String profile, String diameter) {
		_width = width;
		_profile = profile;
		_diameter = diameter;
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

	public void setDiameter(String diameter) {
		_diameter = diameter;
	}

	public void setProfile(String profile) {
		_profile = profile;
	}

	public void setWidth(String width) {
		_width = width;
	}
}
