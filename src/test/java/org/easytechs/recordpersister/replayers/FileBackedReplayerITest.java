package org.easytechs.recordpersister.replayers;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.TestBean;
import org.easytechs.recordpersister.normalizers.StringToValueNormalizedNormalizer;
import org.easytechs.recordpersister.replayers.RecordDateReplayer;
import org.easytechs.recordpersister.replayers.ReplayCallback;
import org.easytechs.recordpersister.replayers.date.BeanPropertyDateExtractor;
import org.easytechs.recordpersister.replayers.date.DateExtractor;
import org.easytechs.recordpersister.retrievers.AbstractDataRetriever;
import org.easytechs.recordpersister.retrievers.FileRetriever;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class FileBackedReplayerITest {



	private static final String DIR = "/tmp";
	private static final String FILE = "file.test";
	/**
	 */
	private Charset charset = Charset.forName("UTF-8");
	
	/**
	 */
	private RecordDateReplayer<TestBean> testObj;

	/**
	 */
	private DateExtractor<TestBean> dateExtractor = new BeanPropertyDateExtractor<TestBean>("date");

	/**
	 */
	private MessageDenormalizer<TestBean> normalizedInverseTransformer = new NormalizedToTradeTickTransformer();

	/**
	 */
	private AbstractDataRetriever<String> retriever;

	@BeforeMethod
	public void setup() throws Exception{
		saveACoupleOfFileRecords();
		testObj = new RecordDateReplayer<>();
		testObj.setDateExtractor(dateExtractor);
		testObj.setNormalizedTransformer(normalizedInverseTransformer);
		retriever =  new FileRetriever(DIR,FILE);
		retriever.setTransformer(new StringToValueNormalizedNormalizer());
		testObj.setDataRetriever(retriever);
	}

	@Test
	public void shouldReplayTwo()throws Exception {
		final AtomicInteger counter = new AtomicInteger(0);
		testObj.replay(new ReplayCallback<TestBean>() {
			@Override
			public void replay(TestBean value) {
				counter.addAndGet(1);
			}
		});
		Assert.assertEquals(counter.get(), 0);
		Thread.sleep(10000);
		Assert.assertEquals(counter.get(), 2);
	}
	
	private void saveACoupleOfFileRecords() throws Exception{
		Path path = Paths.get(DIR, FILE);
		BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.CREATE);
		writer.write("GBPUSD,123.44,"+datePlusSeconds(5)+"\n");
		writer.write("GBPUSD,122.11,"+datePlusSeconds(8)+"\n");
		writer.flush();
		writer.close();
	}

	private long datePlusSeconds(int seconds){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.SECOND, seconds);
		return cal.getTimeInMillis();
	}

	private static class NormalizedToTradeTickTransformer implements MessageDenormalizer<TestBean> {

		@Override
		public TestBean transform(NormalizedMessage message) {
			String csv = message.getValue("value");
			String[] fields = csv.split(",");
			TestBean tick = new TestBean();
			tick.setDate(Long.parseLong(fields[2]));
			tick.setSymbol(fields[0]);
			tick.setValue(fields[1]);
			return tick;
		}

	}


}
