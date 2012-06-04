package org.easytechs.recordpersister.appenders.file;

import java.nio.file.Path;

public interface FilePostProcessor {
	void postProcess(Path path);
}
