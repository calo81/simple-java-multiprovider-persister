package org.easytechs.recordpersister.appenders.cassandra;

import java.util.List;

import org.apache.cassandra.thrift.Column;

public class CassandraRow {
	/**
	 */
	private String key;
	/**
	 */
	private List<Column> columns;
	/**
	 * @return
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
