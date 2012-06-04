package org.easytechs.recordpersister.retrievers;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.normalizers.StringToValueNormalizedNormalizer;
import org.easytechs.recordpersister.retrievers.FileRetriever;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class FileRetrieverUTest {
	/**
	 */
	private FileRetriever testObj;
	/**
	 */
	private String DIR = "/tmp";
	/**
	 */
	private String FILE_NAME="file.xx";
	/**
	 */
	private Charset charset = Charset.forName("UTF-8");
	
	@BeforeMethod
	public void steup()throws Exception{
		createACoupleOfRecordsInFile();
		testObj = new FileRetriever(DIR, FILE_NAME);
		testObj.setTransformer(new StringToValueNormalizedNormalizer());
	}

	
	
	@Test
	public void shouldRetrieveElements(){
		List<NormalizedMessage> messages = testObj.retrieve(2);
		Assert.assertEquals(messages.size(), 2);
		Assert.assertEquals(messages.get(0).getValue("value"), "GBPUSD,123.44,111");
	}
	
	private void createACoupleOfRecordsInFile() throws Exception{
		Path path = Paths.get(DIR, FILE_NAME);
		BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.CREATE);
		writer.write("GBPUSD,123.44,111\n");
		writer.write("GBPUSD,122.11,222\n");
		writer.flush();
		writer.close();
	}
}
