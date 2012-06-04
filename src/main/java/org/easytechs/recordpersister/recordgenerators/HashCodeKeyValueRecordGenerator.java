package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.redis.KeyValue;

public class HashCodeKeyValueRecordGenerator implements RecordGenerator<KeyValue> {

	@Override
	public KeyValue generate(NormalizedMessage message) {
		return new KeyValue(String.valueOf(message.hashCode()), message.getValue("value"));
	}

}
