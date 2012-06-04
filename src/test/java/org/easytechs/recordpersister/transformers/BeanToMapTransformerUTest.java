package org.easytechs.recordpersister.transformers;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.TestBean;
import org.easytechs.recordpersister.normalizers.BeanToMapNormalizer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class BeanToMapTransformerUTest {
	/**
	 */
	private BeanToMapNormalizer<TestBean> testObj;
	
	
	@BeforeMethod
	public void setup(){
		testObj = new BeanToMapNormalizer<>("symbol","value");		
	}
	
	@Test
	public void testTransformation(){
		TestBean tick = new TestBean();
		tick.setDate(123124l);
		tick.setSymbol("GBP");
		tick.setValue("123");
		NormalizedMessage message = testObj.transform(tick);
		Assert.assertEquals(message.getValue("symbol"), "GBP");
		Assert.assertEquals(message.getValue("value"), "123");
		Assert.assertNull(message.getValue("date"));
	}
}
