package org.easytechs.recordpersister.recordgenerators;

import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.appenders.cassandra.CassandraRow;
import org.easytechs.recordpersister.recordgenerators.CassandraRowToStringBasedGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class CassandraRowToStringBasedGeneratorUTest {
	private CassandraRowToStringBasedGenerator testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new CassandraRowToStringBasedGenerator();
	}
	
	@Test
	public void shouldGenerateRecord(){
		NormalizedMessage message = new NormalizedMessage();
		message.addValue("value", "hhhttt");
		CassandraRow row =  testObj.generate(message);
		Assert.assertEquals(row.getKey() , Integer.toHexString(System.identityHashCode(message)));
		Assert.assertEquals(row.getColumns().get(0).getValue(), "hhhttt".getBytes());
	}
}
