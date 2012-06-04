package org.easytechs.recordpersister.appenders;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.easytechs.recordpersister.appenders.file.FilePostProcessor;
import org.easytechs.recordpersister.appenders.file.NOPPathFacilitator;
import org.easytechs.recordpersister.appenders.file.PathFacilitator;
import org.easytechs.recordpersister.appenders.file.postprocessors.NOPFilePostProcessor;
import org.easytechs.recordpersister.recordgenerators.ToStringRecordGenerator;


public class FileAppender extends AbstractAppender<String> {

	private PathFacilitator pathFacilitator;
	private Writer writer;
	private Charset charset = Charset.forName("UTF-8");
	private Path currentFile;
	private boolean buffered;
	private FilePostProcessor postProcessor;

	public FileAppender(String filePath) throws IOException {
		this(new NOPPathFacilitator(filePath), false);
	}

	public FileAppender(PathFacilitator pathFacilitator, boolean buffered) throws IOException {
		this.pathFacilitator = pathFacilitator;
		recordGenerator = new ToStringRecordGenerator("value");
		postProcessor = new NOPFilePostProcessor();
		currentFile = pathFacilitator.getPath();
		this.buffered = buffered;
		setupWriter();
	}

	@Override
	protected void doAppend(String record) throws Exception {
		if (needsToRollFile()) {	
			closeWriterIfOpen();
			postProcessor.postProcess(currentFile);
			currentFile = pathFacilitator.getPath();
			setupWriter();
		}
		writer.write(record + "\n");
	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO: One of those checked exceptions that make no sense to catch
		}
	}
	
	private boolean needsToRollFile() {
		return !currentFile.equals(pathFacilitator.getPath());
	}
	
	private void setupWriter() throws IOException {
		if (!Files.exists(Paths.get("/", currentFile.toFile().getParent()))) {
			Files.createDirectories(Paths.get("/", currentFile.toFile().getParent()));
		}
		closeWriterIfOpen();
		if (buffered) {
			writer = Files.newBufferedWriter(currentFile, charset, StandardOpenOption.CREATE);
		} else {
			writer = new OutputStreamWriter(Files.newOutputStream(currentFile, StandardOpenOption.CREATE));
		}
	}
	
	private void closeWriterIfOpen() {
		if (writer != null) {
			try{
				writer.close();
			}catch(IOException e){
				// Nothing to do again.
			}
		}
	}
	
	public Path getCurrentFile() {
		return currentFile;
	}


	public void flush() {
		try {
			writer.flush();
		} catch (IOException e) {
			// Nothing to do?
		}
	}

	public void setPostProcessor(FilePostProcessor postProcessor) {
		this.postProcessor=postProcessor;
	}

}
