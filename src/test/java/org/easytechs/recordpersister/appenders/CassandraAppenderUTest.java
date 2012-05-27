package org.easytechs.recordpersister.appenders;

import java.util.ArrayList;
import java.util.Date;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.log4j.Logger;
import org.easytechs.recordpersister.AbstractTimedTest;
import org.easytechs.recordpersister.IndexedRunnable;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.CassandraAppender;
import org.easytechs.recordpersister.appenders.cassandra.CassandraRow;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CassandraAppenderUTest extends AbstractTimedTest{
	private static final Logger LOG = Logger.getLogger(CassandraAppenderUTest.class);

	private static final String UTF8 = "UTF8";
	private static final String HOST = "localhost";
	private static final int PORT = 9160;
	private static final ConsistencyLevel CL = ConsistencyLevel.ONE;
	private static final String KEYSPACE = "DEMO";
	private static final String COLUMN_PARENT = "RawTicks";

	private CassandraAppender testObj;

	@Mock
	private RecordGenerator<CassandraRow> recordGenerator;

	@BeforeMethod
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		testObj = new CassandraAppender(HOST, PORT, KEYSPACE, COLUMN_PARENT);
		testObj.setRecordGenerator(recordGenerator);
	}

	@Test
	public void shouldStoreSimpleRequest() throws Exception {
		doTimed(new IndexedRunnable() {
			
			@Override
			public void run(int i) throws Exception{
				NormalizedMessage message = new NormalizedMessage();
				Mockito.when(recordGenerator.generate(message)).thenReturn(createCassandraRow(i));
				testObj.append(message);
			}
		}, 2000);
		testObj.close();
	}

	private CassandraRow createCassandraRow(int key) throws Exception {
		CassandraRow row = new CassandraRow();
		row.setKey(String.valueOf(key));
		row.setColumns(new ArrayList<Column>());
		row.getColumns().add(createColumn(String.valueOf(key)));
		return row;
	}

	private Column createColumn(String value) throws Exception {
		Column column = new Column();
		column.setName("name".getBytes(UTF8));
		column.setValue(("Carlo Scarioni "+value).getBytes(UTF8));
		column.setTimestamp(new Date().getTime());
		return column;
	}
}
