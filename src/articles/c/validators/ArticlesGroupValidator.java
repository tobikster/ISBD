package articles.c.validators;

import articles.m.ArticleAttribute;
import articles.m.ArticlesGroup;
import articles.m.ArticlesGroupType;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;

public class ArticlesGroupValidator implements EntityValidator<ArticlesGroup> {
	@Override
	public boolean validate(ArticlesGroup object) throws DatabaseException {
		List<String> errors = new LinkedList<>();

    if (object.getType()!=ArticlesGroupType.PARTS) {
			errors.add("Typ grupy jest niedozwolony!");
		}

		if (!ElementaryValidator.hasValue(object.getName())) {
			errors.add("Nazwa grupy jest wymagana!");
		}
		else if (!ElementaryValidator.maxLengthValidator(object.getName(), 30)) {
			errors.add("Nazwa grupy może mieć co najwyżej 30 znaków!");
		}

		if (object.getVat() == null) {
			errors.add("Stawka VAT jest wymagana!");
		}
		
		ArticleAttributeValidator validator = new ArticleAttributeValidator();
		for (ArticleAttribute attribute : object.getAttributes()) {
			try {
				validator.validate(attribute);
			}
			catch (DatabaseException ex) {
				errors.addAll(ex.getErrors());
			}
		}

		if (!errors.isEmpty()) {
			throw new DatabaseException(errors);
		}

		return errors.isEmpty();
	}
}