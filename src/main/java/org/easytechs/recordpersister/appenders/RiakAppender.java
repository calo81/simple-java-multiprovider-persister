package org.easytechs.recordpersister.appenders;

import org.easytechs.recordpersister.appenders.redis.KeyValue;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;

public class RiakAppender extends AbstractAppender<KeyValue>{

	private Bucket myBucket;
	private IRiakClient riakClient;
	
	public RiakAppender(String host, int port, String bucket) throws Exception{
		riakClient = RiakFactory.pbcClient(host,port);
		myBucket = riakClient.fetchBucket(bucket).execute();
	}
	@Override
	public void close() {
		riakClient.shutdown();
	}

	@Override
	protected void doAppend(KeyValue record) throws Exception {
		myBucket.store(record.getKey(), record.getValue()).execute();
	}

}
