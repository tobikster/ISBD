package workers.m;

/**
 *
 * @author tobikster
 */
public class Worker implements Cloneable {
	private int _id;
	private String _name;
	private String _surname;
	private WorkerPosition _job;
	private String _login; // w bazie nie jest wymagane
	private String _password; // w bazie nie jest wymagane

	public Worker()
	{
		this(0, null, null, null, null, null);
	}
	
	public Worker(int id, String name, String surname, WorkerPosition job) {
		this(id, name, surname, job, null, null);
	}

	public Worker(int id, String name, String surname, WorkerPosition job, String login, String password) {
		_id = id;
		_name = name;
		_surname = surname;
		_job = job;
		_login = login;
		_password = password;
	}
	
	public int getId() {
		return _id;
	}

	public WorkerPosition getJob() {
		return _job;
	}

	public String getLogin() {
		return _login;
	}

	public String getName() {
		return _name;
	}

	public String getPassword() {
		return _password;
	}

	public String getSurname() {
		return _surname;
	}
	
	public void setId(int id) {
		_id = id;
	}

	public void setJob(WorkerPosition job) {
		_job = job;
	}

	public void setLogin(String login) {
		_login = login;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setSurname(String surname) {
		_surname = surname;
	}
	
	public String getFullName() {
		return getName() + " " + getSurname();
	}
  
  @Override
  public Worker clone() {
    return new Worker(_id, _name, _surname, _job, _login, _password);
  }
}
