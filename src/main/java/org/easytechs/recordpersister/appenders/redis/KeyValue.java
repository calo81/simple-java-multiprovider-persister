package org.easytechs.recordpersister.appenders.redis;

public class KeyValue {
	/**
	 */
	private String key;
	/**
	 */
	private String value;
	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	/**
	 * @return
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
