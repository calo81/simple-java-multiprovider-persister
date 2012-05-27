package org.easytechs.recordpersister;

public interface RecordGenerator<T extends Object>{

	T generate(NormalizedMessage message);

}
