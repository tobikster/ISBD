package workers.m;

/**
 *
 * @author MRKACZOR
 */
public enum WorkerPosition
{
  BOSS("Właściciel"), MECHANIC("Pracownik"), DISSMISSED("Zwolniony");
  
  private String _name;
  
  private WorkerPosition(String name) {
    _name = name;
  }
  
  public static WorkerPosition valueFor(String name) {
    if(name.equals(BOSS.toString()))
      return BOSS;
    else if(name.equals(MECHANIC.toString()))
      return MECHANIC;
    else
      return DISSMISSED;
  }
  
  @Override
  public String toString() {
    return _name;
  }
}
