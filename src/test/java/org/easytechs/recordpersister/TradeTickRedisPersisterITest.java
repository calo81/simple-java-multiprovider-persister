package org.easytechs.recordpersister;

import org.easytechs.recordpersister.appenders.RedisAppender;
import org.easytechs.recordpersister.appenders.redis.KeyValue;
import org.easytechs.recordpersister.transformers.ToStringTransformerInValueKey;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TradeTickRedisPersisterITest {
	private GenericPersister<TradeTick> testObj;
	
	@BeforeMethod
	public void setup(){
		testObj = new GenericPersister<>();
		RedisAppender appender = new RedisAppender("localhost");
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
	
	private static class TickRecordGenerator implements RecordGenerator<KeyValue>{

		@Override
		public KeyValue generate(NormalizedMessage message) {
			return new KeyValue("ticks", message.getValue("value"));
		}	
	}
}
