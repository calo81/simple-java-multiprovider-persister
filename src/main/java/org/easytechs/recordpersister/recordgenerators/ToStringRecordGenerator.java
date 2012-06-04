package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;

public class ToStringRecordGenerator implements RecordGenerator<String> {


	private String key;
	
	public ToStringRecordGenerator(String keyInNormalized){
		this.key=keyInNormalized;
	}

	@Override
	public String generate(NormalizedMessage message) {
		return message.getValue(key).toString();
	}
}
