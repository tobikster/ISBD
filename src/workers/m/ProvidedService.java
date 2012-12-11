package workers.m;

import java.util.Date;
import services.m.Service;

/**
 *
 * @author tobikster
 */
public class ProvidedService implements Cloneable {
  private int _id;
	private Service _service;
	private Worker _worker;
	private Date _date;
	private double _value;

  public ProvidedService() {
    
  }

	public ProvidedService(int id, Service service, Worker worker, Date date, double value) {
		_id = id;
    _service = service;
		_worker = worker;
		_date = date;
		_value = value;
	}

  public int getId() {
    return _id;
  }

	public Date getDate() {
		return _date;
	}

	public Service getService() {
		return _service;
	}

	public double getValue() {
		return _value;
	}

	public Worker getWorker() {
		return _worker;
	}

  public void setId(int id) {
    _id = id;
  }

	public void setDate(Date date) {
		_date = date;
	}

	public void setService(Service service) {
		_service = service;
	}

	public void setValue(double value) {
		_value = value;
	}

	public void setWorker(Worker worker) {
		_worker = worker;
	}
  
  @Override
  public ProvidedService clone() {
    return new ProvidedService(_id, _service.clone(), _worker.clone(), (Date)_date.clone(), _value);
  }
}
