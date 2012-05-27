package org.easytechs.recordpersister.appenders.redis;

public class KeyValue {
	private String key;
	private String value;
	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
}
