package org.easytechs.recordpersister.appenders;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.FileAppender;
import org.easytechs.recordpersister.appenders.file.RollingFileStrategy;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FileAppenderUTest extends AbstractTimedTest{
	private FileAppender testObj;
	private final String ROOT_DIR = "/tmp";
	private final String FILE_NAME_PREFIX = "tick";
	private final String FILE_NAME_SUFFIX = ".raw";

	@Mock
	private RollingFileStrategy rollingFileStrategy;

	@Mock
	private RecordGenerator<String> recordGenerator;

	@BeforeMethod
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		testObj = new FileAppender(ROOT_DIR, FILE_NAME_PREFIX, FILE_NAME_SUFFIX, rollingFileStrategy, false);
		testObj.setRecordGenerator(recordGenerator);
	}

	@Test
	public void shouldStoreSimpleRequests() throws Exception {
		Mockito.when(rollingFileStrategy.shouldRoll()).thenReturn(false);
		doTimed(new IndexedRunnable() {
			
			@Override
			public void run(int i) {
				NormalizedMessage message = new NormalizedMessage();
				Mockito.when(recordGenerator.generate(message)).thenReturn("This is the String to insert"+i+"\n");
				testObj.append(message);
			}
		}, 2000);
		Path path = testObj.getCurrentFile();
		Charset charset = Charset.forName("UTF-8");
		String readLine = Files.readAllLines(path, charset).get(0);
		//Assert.assertEquals(readLine, "This is the String to insert");
	}
}
