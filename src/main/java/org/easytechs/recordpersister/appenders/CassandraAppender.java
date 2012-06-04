package org.easytechs.recordpersister.appenders;

import java.nio.ByteBuffer;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.easytechs.recordpersister.appenders.cassandra.CassandraRow;



public class CassandraAppender extends AbstractAppender<CassandraRow>{


	/**
	 */
	private Cassandra.Client client;
	/**
	 */
	private ColumnParent columnParent ;
	/**
	 */
	private TTransport tr;
	private static final ConsistencyLevel CL = ConsistencyLevel.ANY;

	public CassandraAppender(String host, int port, String keyspace, String columnParent) throws Exception{
		tr = new TSocket(host, port);
		TFramedTransport tf = new TFramedTransport(tr);
		TProtocol proto = new TBinaryProtocol(tf);
		client = new Cassandra.Client(proto);
		tf.open();
		client.set_keyspace(keyspace);
		this.columnParent = new ColumnParent(columnParent);
	}

	@Override
	public void close() {
		tr.close();
	}

	@Override
	protected void doAppend(CassandraRow record) throws Exception{
			client.insert(ByteBuffer.wrap(record.getKey().getBytes()), columnParent, record.getColumns().get(0), CL);
	}
}
