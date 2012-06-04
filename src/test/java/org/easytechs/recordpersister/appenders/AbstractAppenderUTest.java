package org.easytechs.recordpersister.appenders;

import java.util.Arrays;
import java.util.List;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.AbstractAppender;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AbstractAppenderUTest extends AbstractAppender<String>{

	int timesAppended = 0;
	
	@BeforeMethod
	public void setup(){
		this.setRecordGenerator(new MockGenerator());
	}
	
	@Override
	public void close() {
		
	}

	@Override
	protected void doAppend(String record) throws Exception {
		timesAppended++;
	}
	
	@Test
	public void shouldBatchAppend(){
		List<NormalizedMessage> messages = Arrays.asList(new NormalizedMessage[]{new NormalizedMessage(), new NormalizedMessage()});
		this.append(messages);
		Assert.assertEquals(timesAppended, 2);
	}

	private static class MockGenerator implements RecordGenerator<String>{

		@Override
		public String generate(NormalizedMessage message) {
			return "hola";
		}
		
	}
}
