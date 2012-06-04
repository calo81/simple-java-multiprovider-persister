package org.easytechs.recordpersister.retrievers;

import java.util.List;

import redis.clients.jedis.Jedis;

public class RedisRetriever extends AbstractDataRetriever<String> {

	/**
	 */
	private Jedis jedis;
	/**
	 */
	private String key;
	public RedisRetriever(String host, String key) {
		this.key=key;
		jedis = new Jedis(host);
		jedis.connect();
	}
	@Override
	protected List<String> getElements(int howMany) {
		  return jedis.lrange(key,0,howMany);
	}
}
