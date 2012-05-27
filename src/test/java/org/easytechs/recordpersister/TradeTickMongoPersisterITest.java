package org.easytechs.recordpersister;

import org.easytechs.recordpersister.appenders.MongoAppender;
import org.easytechs.recordpersister.transformers.ToStringTransformerInValueKey;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;

public class TradeTickMongoPersisterITest {
	private GenericPersister<TradeTick> testObj;
	
	@BeforeMethod
	public void setup()throws Exception{
		testObj = new GenericPersister<>();
		MongoAppender appender = new MongoAppender("localhost","27017","xxdb","ticks");
		appender.setRecordGenerator(new TickRecordGenerator());
		testObj.setNormalizedMessageTransformer(new ToStringTransformerInValueKey<TradeTick>());
		testObj.setAppender(appender);
	}
	
	@Test
	public void shouldPersistOneItem(){
		TradeTick tick = new TradeTick();
		tick.setSymbol("XX");
		tick.setValue("100.00");
		testObj.persist(tick);
	}
	
	private static class TickRecordGenerator implements RecordGenerator<BasicDBObject>{

		@Override
		public BasicDBObject generate(NormalizedMessage message) {
			BasicDBObject doc = new BasicDBObject();
			doc.put("tick", message.getValue("value"));
			return doc;
		}	
	}
}
