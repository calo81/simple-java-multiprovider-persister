package org.easytechs.recordpersister.appenders;

import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisHashAppender extends AbstractAppender<Map<String, String>> {

	/**
	 */
	private String listKey;

	/**
	 */
	private Jedis jedis;

	public RedisHashAppender(String host, String listKey) {
		this.listKey = listKey;
		jedis = new Jedis(host);
		jedis.connect();
	}

	@Override
	public void close() {
		jedis.disconnect();
	}

	@Override
	protected void doAppend(Map<String, String> record) throws Exception {
		String key = String.valueOf(record.hashCode());
		for (String field : record.keySet()) {
			jedis.hset(key, field, record.get(field));			
		}
		jedis.rpush(getListKey(), key);
	}
	
	/**
	 * @return
	 */
	private String getListKey(){
		return this.listKey;
	}
}
