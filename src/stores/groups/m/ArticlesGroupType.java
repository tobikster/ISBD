package stores.groups.m;

/**
 *
 * @author MRKACZOR
 */
public enum ArticlesGroupType {
	PARTS("c"), TIRES("o");
	private String _type;

	private ArticlesGroupType(String type) {
		_type = type;
	}

	@Override
	public String toString() {
		return _type;
	}
}
