package org.easytechs.recordpersister.appenders;

import java.util.Map;

import org.jcouchdb.db.Database;

public class CouchDBAppender extends AbstractAppender<Map<String, String>>{

	private Database db;
	public CouchDBAppender(String host, String database){
		 db = new Database(host, database);
	    
	}
	@Override
	public void close() {
		
	}

	@Override
	protected void doAppend(Map<String, String> record) throws Exception {
		db.createDocument(record);
	}

}
