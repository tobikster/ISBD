package stores.articles.m;

/**
 *
 * @author MRKACZOR
 */
public enum LoadIndex
{
  _70(70), _82(82), _93(93), _95(95);
  
  private int _value;

  private LoadIndex(int value) {
    _value = value;
  }

  public static LoadIndex valueOf(int input) {
    return valueOf("_"+input);
  }

  @Override
  public String toString() {
    return ""+_value;
  }
}
