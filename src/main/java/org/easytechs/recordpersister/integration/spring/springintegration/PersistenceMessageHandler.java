package org.easytechs.recordpersister.integration.spring.springintegration;

import org.easytechs.recordpersister.Persister;
import org.springframework.integration.Message;


@SuppressWarnings({"unchecked","rawtypes"})
public class PersistenceMessageHandler {

	private Persister persister;
	
	public void handleMessageInternal(Message<?> message) throws Exception {
		persister.persist(message.getPayload());
	}
	public void setPersister(Persister<?> persister) {
		this.persister = persister;
	}
}
