package org.easytechs.recordpersister.recordgenerators;

import java.util.ArrayList;
import java.util.Date;

import org.apache.cassandra.thrift.Column;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.RecordGenerator;
import org.easytechs.recordpersister.appenders.cassandra.CassandraRow;


public class CassandraRowToStringBasedGenerator implements RecordGenerator<CassandraRow> {

	private static final String ENCODING = "UTF8";
	@Override
	public CassandraRow generate(NormalizedMessage message) {
		try {
			return tryToGenerateRow(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private CassandraRow tryToGenerateRow(NormalizedMessage message) throws Exception {
		CassandraRow row = new CassandraRow();
		row.setKey(Integer.toHexString(System.identityHashCode(message)));
		row.setColumns(new ArrayList<Column>());
		row.getColumns().add(createColumn(message.getValue("value")));
		return row;
	}
	
	private Column createColumn(String value) throws Exception {
		Column column = new Column();
		column.setName("value".getBytes(ENCODING));
		column.setValue((value).getBytes(ENCODING));
		column.setTimestamp(new Date().getTime());
		return column;
	}

}
