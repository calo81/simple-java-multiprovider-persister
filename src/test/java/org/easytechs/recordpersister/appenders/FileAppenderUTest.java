package org.easytechs.recordpersister.appenders;

import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.FileAppender;
import org.easytechs.recordpersister.appenders.file.FilePostProcessor;
import org.easytechs.recordpersister.appenders.file.PathFacilitator;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class FileAppenderUTest extends AbstractTimedTest {

	private static final int NUMBER_OF_RECORDS = 20000;

	private FileAppender testObj;

	private final String ROOT_DIR = "/tmp";

	private final String FILE_NAME_PREFIX = "tick";

	private final String FILE_NAME_SUFFIX = ".rawc";

	@Mock
	private PathFacilitator pathFacilitator;

	@Mock
	private RecordGenerator<String> recordGenerator;
	
	@Mock
	private FilePostProcessor postProcessor;

	@BeforeMethod
	public void setup() throws Exception {
		Paths.get(ROOT_DIR, FILE_NAME_PREFIX + FILE_NAME_SUFFIX).toFile().delete();
		MockitoAnnotations.initMocks(this);
		Mockito.when(pathFacilitator.getPath()).thenReturn(Paths.get(ROOT_DIR, FILE_NAME_PREFIX + FILE_NAME_SUFFIX));
		testObj = new FileAppender(pathFacilitator, false);
		testObj.setRecordGenerator(recordGenerator);
		testObj.setPostProcessor(postProcessor);
	}

	@Test(invocationCount=10)
	public void shouldStoreSimpleRequests() throws Exception {
		Mockito.when(pathFacilitator.getPath()).thenReturn(Paths.get(ROOT_DIR, FILE_NAME_PREFIX + FILE_NAME_SUFFIX));
		Mockito.when(recordGenerator.generate(Mockito.any(NormalizedMessage.class))).thenReturn("This is the String to insert");
		doTimed(new IndexedRunnable() {

			@Override
			public void run(int i) {
				NormalizedMessage message = new NormalizedMessage();
				testObj.append(message);
			}
		},closeAppender(), NUMBER_OF_RECORDS);
		testObj.close();
		Path path = testObj.getCurrentFile();
		Charset charset = Charset.forName("UTF-8");
		List<String> readLine = Files.readAllLines(path, charset);
		Assert.assertEquals(readLine.size(), NUMBER_OF_RECORDS);
		
	}
	
	@Test
	public void shouldPostProcessFileWhenPathChanges(){
		Mockito.when(pathFacilitator.getPath()).thenReturn(Paths.get(ROOT_DIR, FILE_NAME_PREFIX + FILE_NAME_SUFFIX),Paths.get(ROOT_DIR, FILE_NAME_PREFIX + FILE_NAME_SUFFIX+"dd"));
		NormalizedMessage message = new NormalizedMessage();
		testObj.append(message);
		testObj.append(message);
		Mockito.verify(postProcessor).postProcess(Mockito.any(Path.class));
	}

	private Runnable closeAppender() {
		return new Runnable() {
			
			@Override
			public void run() {
				testObj.close();				
			}
		};
	}
}
