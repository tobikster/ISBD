package stores.groups.c.validators;

import core.c.ElementaryValidator;
import core.c.EntityValidator;
import core.m.DatabaseException;
import java.util.LinkedList;
import java.util.List;
import stores.articles.c.validators.ArticleAttributeValidator;
import stores.articles.m.ArticleAttribute;
import stores.groups.m.ArticlesGroup;
import stores.groups.m.ArticlesGroupType;

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
		
		if(object.getType() == null) {
			errors.add("Typ zawartości grupy jest wymagany!");
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