package org.easytechs.recordpersister.denormalizers;

import org.easytechs.recordpersister.TestBean2;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.denormalizers.CsvToBeanDenormalizer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CsvToBeanDenormalizerUTest {
	private CsvToBeanDenormalizer<TestBean2> testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new CsvToBeanDenormalizer<>(TestBean2.class, "symbol","price","tradeTime");
	}
	
	@Test
	public void shouldDenormalizeMessage(){
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", "GBP,123.44,123123");
		TestBean2 tick = testObj.transform(message);
		Assert.assertEquals(tick.getPrice(), "123.44");
		Assert.assertEquals(tick.getSymbol(), "GBP");
		Assert.assertEquals(tick.getTradeTime(), 123123l);
	}
}
