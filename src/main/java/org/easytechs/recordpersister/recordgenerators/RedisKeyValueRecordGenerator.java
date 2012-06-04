package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.redis.KeyValue;

public class RedisKeyValueRecordGenerator  implements RecordGenerator<KeyValue> {
	/**
	 */
	private String key;

	public RedisKeyValueRecordGenerator(String key){
		this.key=key;
	}
	
	@Override
	public KeyValue generate(NormalizedMessage message) {
		return new KeyValue(key, message.getValue("value"));
	}
	
}
