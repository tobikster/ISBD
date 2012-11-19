package articles.c;

import articles.m.Article;
import core.c.DataBaseException;
import core.c.ElementaryValidator;
import core.c.EntityValidator;
import java.util.LinkedList;
import java.util.List;

public class ArticleValidator implements EntityValidator <Article>{
    // <editor-fold defaultstate="collapsed" desc="Object variables">
	private List<String> _errors;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Creating object">
	public ArticleValidator(){
		_errors = new LinkedList<>();
	}
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
    // <editor-fold defaultstate="collapsed" desc="Getters">
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Setters">
    // </editor-fold>
	@Override
	public boolean validate(Article object) throws DataBaseException{
		boolean result = true;
		if(object.getGroup() == null){
			result = false;
			_errors.add("Nie podano grupy towarowej!");
		}
		if(object.getProducer() == null){
			result = false;
			_errors.add("Nie podano producenta!");
		}
		if(!ElementaryValidator.maxLengthValidator(object.getCatalogNumber(), 20)){
			result = false;
			_errors.add("Numer katalogowy jest za długi! Maksymalna dopuszczalna długość to 20 znaków!");
		}
		return result;
	}
    // </editor-fold>
}