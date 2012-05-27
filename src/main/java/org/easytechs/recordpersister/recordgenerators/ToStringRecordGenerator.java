package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;

public class ToStringRecordGenerator implements RecordGenerator<String> {

	private String key;

	@Override
	public String generate(NormalizedMessage message) {
		return message.getValue(key).toString();
	}

	public void setKeyInNormalized(String key) {
		this.key = key;
	}

}
