package org.easytechs.recordpersister.retrievers;

import java.util.ArrayList;
import java.util.List;

import org.easytechs.recordpersister.DataRetriever;
import org.easytechs.recordpersister.MessageNormalizer;
import org.easytechs.recordpersister.NormalizedMessage;


public abstract class AbstractDataRetriever<T extends Object> implements DataRetriever<T> {

	/**
	 */
	private MessageNormalizer<T> transformer;

	@Override
	public List<NormalizedMessage> retrieve(int howMany) {
		List<NormalizedMessage> normalizedMessages = new ArrayList<>();
		for (T element : getElements(howMany)) {
			normalizedMessages.add(transformer.transform(element));
		}
		return normalizedMessages;
	}

	protected abstract List<T> getElements(int howMany);

	public void setTransformer(MessageNormalizer<T> transformer) {
		this.transformer = transformer;
	}

}
