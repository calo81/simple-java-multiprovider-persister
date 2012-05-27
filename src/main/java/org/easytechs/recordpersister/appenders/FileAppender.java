package org.easytechs.recordpersister.appenders;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.file.NOPRollingFileStrategy;
import org.easytechs.recordpersister.appenders.file.RollingFileStrategy;
import org.easytechs.recordpersister.recordgenerators.ToStringRecordGenerator;

public class FileAppender extends AbstractAppender<String> {

	private final String rootDir;
	private RollingFileStrategy rollingFileStrategy;
	private String fileNamePrefix;
	private String fileNamesuffix;
	private Writer writer;
	private Charset charset = Charset.forName("UTF-8");
	private Path currentFile;

	public FileAppender(String rootDir, String filenamePrefix, String filenameSuffix) throws IOException {
		this(rootDir, filenamePrefix, filenameSuffix, new NOPRollingFileStrategy(), false);
	}

	public FileAppender(String rootDir, String filenamePrefix, String filenameSuffix, RollingFileStrategy rollingFileStrategy,
			boolean buffered) throws IOException {
		this.rootDir = rootDir;
		this.rollingFileStrategy = rollingFileStrategy;
		this.fileNamePrefix = filenamePrefix;
		this.fileNamesuffix = filenameSuffix;
		recordGenerator = new ToStringRecordGenerator();
		currentFile = Paths.get(rootDir, filenamePrefix + "-" + new Date().getTime() + filenameSuffix);
		if (buffered) {
			writer = Files.newBufferedWriter(currentFile, charset, StandardOpenOption.CREATE);
		} else {
			writer = new OutputStreamWriter(Files.newOutputStream(currentFile, StandardOpenOption.CREATE));
		}
	}

	@Override
	protected void doAppend(String record) throws Exception{
			writer.write(record);
	}

	public void setRecordGenerator(RecordGenerator<String> recordGenerator) {
		this.recordGenerator = recordGenerator;
	}

	public Path getCurrentFile() {
		return currentFile;
	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			//TODO: One of those checked exceptions that make no sense to catch
		}
	}

}
