package workers.m;

/**
 *
 * @author tobikster
 */
public class Worker {
	private String _name;
	private String _surname;
	private String _job;
	private String _login; // w bazie nie jest wymagane
	private String _password; // w bazie nie jest wymagane

	public Worker()
	{
		this(null, null, null, null, null);
	}
	
	public Worker(String name, String surname, String job) {
		this(name, surname, job, null, null);
	}

	public Worker(String name, String surname, String job, String login, String password) {
		_name = name;
		_surname = surname;
		_job = job;
		_login = login;
		_password = password;
	}

	public String getJob() {
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

	public void setJob(String job) {
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
}