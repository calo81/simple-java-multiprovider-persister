package org.easytechs.recordpersister.replayers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.easytechs.recordpersister.TestBean2;
import org.easytechs.recordpersister.DataRetriever;
import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.replayers.RecordDateReplayer;
import org.easytechs.recordpersister.replayers.ReplayCallback;
import org.easytechs.recordpersister.replayers.date.DateExtractor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class PeriodicReplayerUTest {
	/**
	 */
	private RecordDateReplayer<TestBean2> testObj;

	/**
	 */
	private DateExtractor<TestBean2> dateExtractor = new BloombergTickDateExtractor();

	/**
	 */
	private MessageDenormalizer<TestBean2> normalizedInverseTransformer = new NormalizedToBloombergTickTransformer();

	/**
	 */
	@Mock
	private DataRetriever<Object> retriever;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testObj = new RecordDateReplayer<>();
		testObj.setDateExtractor(dateExtractor);
		testObj.setNormalizedTransformer(normalizedInverseTransformer);
		testObj.setDataRetriever(retriever);
	}

	@Test
	public void shouldReplayTwo()throws Exception {
		Mockito.when(retriever.retrieve(Mockito.anyInt())).thenReturn(createListOfNormalized(2));
		final AtomicInteger counter = new AtomicInteger(0);
		testObj.replay(new ReplayCallback<TestBean2>() {
			@Override
			public void replay(TestBean2 value) {
				counter.addAndGet(1);
			}
		});
		Thread.sleep(1000);
		Assert.assertEquals(counter.get(), 2);
	}
	
	@Test
	public void shouldReplayWhenTimed10SecondsFromNow()throws Exception {
		List<NormalizedMessage> normalized = createListOfNormalized(1);
		Mockito.when(retriever.retrieve(Mockito.anyInt())).thenReturn(normalized);
		normalized.get(0).addValue("value",  "gbp,12.33," + secondsFromNow(10));
		final AtomicInteger counter = new AtomicInteger(0);
		testObj.replay(new ReplayCallback<TestBean2>() {
			@Override
			public void replay(TestBean2 value) {
				counter.addAndGet(1);
			}
		});
		Thread.sleep(5000);
		Assert.assertEquals(counter.get(), 0);
		Thread.sleep(7000);
		Assert.assertEquals(counter.get(), 1);
	}

	private long secondsFromNow(int seconds) {
		return (new Date().getTime()) + seconds*1000;
	}
	
	private List<NormalizedMessage> createListOfNormalized(int times) {
		List<NormalizedMessage> normalizedMessages = new ArrayList<>();
		for (int i = 0; i < times; i++) {
			NormalizedMessage message = new NormalizedMessage();
			message.addValue("value", "gbp,12.33," + new Date().getTime());
			normalizedMessages.add(message);
		}
		return normalizedMessages;
	}

	private static class BloombergTickDateExtractor implements DateExtractor<TestBean2> {

		@Override
		public long extractDate(TestBean2 object) {
			return object.getTradeTime();
		}
	}

	private static class NormalizedToBloombergTickTransformer implements MessageDenormalizer<TestBean2> {

		@Override
		public TestBean2 transform(NormalizedMessage message) {
			String csv = message.getValue("value");
			String[] fields = csv.split(",");
			TestBean2 tick = new TestBean2(fields[0], Long.parseLong(fields[2]), fields[1]);
			return tick;
		}

	}
}
