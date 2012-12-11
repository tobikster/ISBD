package utils.m;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorkingMap<K, V> extends LinkedHashMap<K, V> {
	// <editor-fold defaultstate="collapsed" desc="Object variables">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PRIVATE methods">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Object PUBLIC methods">
	// <editor-fold defaultstate="collapsed" desc="Getters">
	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="Setters">
	// </editor-fold>
	@Override
	public V get(Object key) {
		V result = null;
		if (key != null) {
			for (Map.Entry<K, V> element : entrySet()) {
				if(key.equals(element.getKey())) {
					result = element.getValue();
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean containsKey(Object key) {
		boolean result = false;
		for(Map.Entry<K, V> element : entrySet()) {
			if(key == null) {
				result |= element.getKey() == null;
			}
			else {
				result |= key.equals(element.getKey());
			}
		}
		return result;
	}
}