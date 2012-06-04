package org.easytechs.recordpersister;

import java.util.List;

public interface Appender {
	void append(List<NormalizedMessage> messages);
	void append(NormalizedMessage normalizedMessage);
	void close();
}
