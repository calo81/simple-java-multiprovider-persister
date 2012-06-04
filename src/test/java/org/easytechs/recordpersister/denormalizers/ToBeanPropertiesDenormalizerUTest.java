package org.easytechs.recordpersister.denormalizers;

import org.easytechs.recordpersister.TestBean2;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.denormalizers.MapToBeanPropertiesDenormalizer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ToBeanPropertiesDenormalizerUTest {
	/**
	 */
	private MapToBeanPropertiesDenormalizer<TestBean2> testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new MapToBeanPropertiesDenormalizer<>(TestBean2.class);
	}
	
	@Test
	public void denormalizeShouldWork(){
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("tradeTime", "12334");
		message.addValue("symbol", "GBP");
		message.addValue("price", "1233.44");
		TestBean2 tick = testObj.transform(message);
		Assert.assertEquals(tick.getPrice(), "1233.44");
		Assert.assertEquals(tick.getSymbol(), "GBP");
		Assert.assertEquals(tick.getTradeTime(), 12334l);
	}
}
