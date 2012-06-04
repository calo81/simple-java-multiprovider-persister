package org.easytechs.recordpersister.appenders.file.postprocessors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.ZipFile;

import org.easytechs.recordpersister.appenders.file.postprocessors.ZipFilePostProcessor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ZipFilePostProcessorUTest {
	private ZipFilePostProcessor testObj;
	private String LINE_TO_SAVE = "Saving a line here\n";
	
	@BeforeMethod
	public void setup(){
		testObj = new ZipFilePostProcessor();
	}
	
	@Test
	public void shouldZipFile() throws Exception{
		Path file = Paths.get("/tmp", "xx.ll");
		BufferedWriter writer = Files.newBufferedWriter(file, Charset.defaultCharset(), StandardOpenOption.CREATE);
		writer.write(LINE_TO_SAVE);
		writer.close();
		testObj.postProcess(file);
		Assert.assertFalse(Files.exists(file));
		Path zipped = Paths.get("/tmp", "xx.ll.zip");
		Assert.assertTrue(Files.exists(zipped));
		assertFileContainsLine(zipped,LINE_TO_SAVE);
	}

	private void assertFileContainsLine(Path zipped,String string) throws Exception{
		ZipFile zf = new ZipFile(zipped.toFile());
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(zf.getInputStream(zf.entries().nextElement())));
			String line = in.readLine()+"\n";
			Assert.assertEquals(line, string);
		} finally {
		  zf.close();
		}
	}
}
