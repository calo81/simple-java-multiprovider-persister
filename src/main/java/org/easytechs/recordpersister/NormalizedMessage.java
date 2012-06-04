package org.easytechs.recordpersister;

import java.util.HashMap;
import java.util.Map;

public class NormalizedMessage {
	/**
	 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NormalizedMessage other = (NormalizedMessage) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	public void setValues(Map<String, String> values) {
		this.values=values;
	}
}
