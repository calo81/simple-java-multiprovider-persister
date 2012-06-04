package org.easytechs.recordpersister.retrievers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisHashRetriever extends
		AbstractDataRetriever<Map<String, String>> {
	/**
	 */
	private Jedis jedis;
	/**
	 */
	private String key;

	public RedisHashRetriever(String host, String key) {
		this.key = key;
		jedis = new Jedis(host);
		jedis.connect();
	}

	@Override
	protected List<Map<String, String>> getElements(int howMany) {
		List<String> ids = jedis.lrange(key, 0, howMany);
		List<Map<String, String>> values = new ArrayList<>();
		for (String id : ids) {
			values.add(jedis.hgetAll(id));
		}
		return values;
	}
}
