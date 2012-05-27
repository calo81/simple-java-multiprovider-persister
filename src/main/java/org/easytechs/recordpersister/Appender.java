package org.easytechs.recordpersister;

public interface Appender {

	void append(NormalizedMessage normalizedMessage);
	void close();
}
