package stores.articles.m;

/**
 *
 * @author MRKACZOR
 */
public enum LoadIndex
{
  _75(75), _79(79), _80(80), _81(81), _82(82), _83(83), _90(90), _91(91), _93(93), _94(94), _95(95), _101(101);
  
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
