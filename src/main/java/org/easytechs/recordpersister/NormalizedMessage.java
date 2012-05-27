package org.easytechs.recordpersister;

import java.util.HashMap;
import java.util.Map;

public class NormalizedMessage {
	private Map<String, String> values = new HashMap<>();

	public Map<String, String> getValues() {
		return values;
	}
	
	public void addValue(String key, String value){
		values.put(key, value);
	}
	
	public String getValue(String key){
		return values.get(key);
	}
}
