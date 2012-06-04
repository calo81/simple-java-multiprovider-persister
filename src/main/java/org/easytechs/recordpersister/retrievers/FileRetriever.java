package org.easytechs.recordpersister.retrievers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileRetriever extends AbstractDataRetriever<String> {

	/**
	 */
	private Path filePath;
	/**
	 */
	private Charset charset = Charset.forName("UTF-8");

	public FileRetriever(String rootDir, String fileName) throws IOException {
		filePath = Paths.get(rootDir, fileName);
	}

	@Override
	protected List<String> getElements(int howMany) {
		BufferedReader reader = obtainReaderOnFile();
		String line = null;
		List<String> records = new ArrayList<>();
		int counter = 0;
		try {
			while ((line = reader.readLine()) != null && counter <= howMany) {
				records.add(line);
				counter ++;
			}
			return records;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private BufferedReader obtainReaderOnFile() {
		try {
			return Files.newBufferedReader(filePath, charset);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
