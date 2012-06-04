package org.easytechs.recordpersister.appenders;

import org.easytechs.recordpersister.appenders.redis.KeyValue;

import redis.clients.jedis.Jedis;


public class RedisAppender extends AbstractAppender<KeyValue>{
	/**
	 */
	private Jedis jedis;
	public RedisAppender(String host) {
		jedis = new Jedis(host);
		jedis.connect();
	}

	@Override
	public void close() {
		jedis.disconnect();
	}

	@Override
	protected void doAppend(KeyValue record) throws Exception {
		jedis.rpush(record.getKey(), record.getValue());
	}
}
