package workers.c.validators;

import core.m.DatabaseException;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import java.util.LinkedList;
import java.util.List;
import workers.m.Worker;

/**
 *
 * @author Zjamnik
 */
public class WorkerValidator implements EntityValidator<Worker> {
	@Override
	public boolean validate(Worker object) throws DatabaseException {
		List<String> errors = new LinkedList<>();

		if (object.getJob()==null) {
			errors.add("Nie podano nazwy stanowiska.");
		}
		else {
			if (!ElementaryValidator.maxLengthValidator(object.getJob().toString(), 20)) {
				errors.add("Nazwy stanowiska jest za długa. Maksymalna długość wynosi 20 znaków.");
			}
		}

		if (!ElementaryValidator.hasValue(object.getName())) {
			errors.add("Nie podano imienia.");
		}
		else {
			if (!ElementaryValidator.maxLengthValidator(object.getName(), 20)) {
				errors.add("Imie jest za długie. Maksymalna długość wynosi 20 znaków.");
			}
		}

		if (!ElementaryValidator.hasValue(object.getSurname())) {
			errors.add("Nie podano nazwiska.");
		}
		else {
			if (!ElementaryValidator.maxLengthValidator(object.getSurname(), 20)) {
				errors.add("Nazwisko jest za długie. Maksymalna długość wynosi 20 znaków.");
			}
		}

		if (!ElementaryValidator.hasValue(object.getLogin())) {
			errors.add("Nie podano loginu!");
		}
		else {
			if (!ElementaryValidator.maxLengthValidator(object.getLogin(), 20)) {
				errors.add("Nazwa użytkownika jest za długa. Maksymalna długość wynosi 20 znaków.");
			}
			if (!ElementaryValidator.minLengthValidator(object.getLogin(), 3)) {
				errors.add("Nazwa użytkownika jest za krótka. Minimalna długość wynosi 3 znaki.");
			}
		}

		if (!ElementaryValidator.hasValue(object.getPassword())) {
			errors.add("Nie podano hasła!");
		}
		else {
			if (!ElementaryValidator.maxLengthValidator(object.getPassword(), 20)) {
				errors.add("Hasło jest za długie. Maksymalna długość wynosi 20 znaków.");
			}
			if (!ElementaryValidator.minLengthValidator(object.getPassword(), 3)) {
				errors.add("Hasło jest za krótkie. Minimalna długość wynosi 3 znaki.");
			}
		}

		if (!errors.isEmpty()) {
			throw new DatabaseException(errors);
		}

		return true;
	}
}
