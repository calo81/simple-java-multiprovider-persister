package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.recordgenerators.ToStringRecordGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ToStringRecordGeneratorUTest {
	private static final String STRING_MESSAGE = "vkascdvck asdvckasgvd";
	/**
	 */
	private ToStringRecordGenerator testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new ToStringRecordGenerator("dd");
	}
	
	@Test
	public void shouldGenerateStringRecordBasedOnToString(){
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("dd", STRING_MESSAGE);
		String value = testObj.generate(message);
		Assert.assertEquals(value, STRING_MESSAGE);
	}
}
