package org.easytechs.recordpersister.replayers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.easytechs.recordpersister.DataRetriever;
import org.easytechs.recordpersister.MessageDenormalizer;
import org.easytechs.recordpersister.NormalizedMessage;
import org.easytechs.recordpersister.Replayer;
import org.easytechs.recordpersister.replayers.date.DateExtractor;


public class RecordDateReplayer<T extends Object> implements Replayer<T> {

	private DateExtractor<T> dateExtractor;

	private MessageDenormalizer<T> normalizedInverseTransformer;

	private DataRetriever<?> dataRetriever;

	private DateComparator dateComparator = new DateComparator();
	
	private final int AMOUNT_TO_RETRIEVE = 100000;

	private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

	@Override
	public void replay(final ReplayCallback<T> callback) {
		List<T> objects = getReplayData();
		Collections.sort(objects,dateComparator);	
		for(final T object:objects){
			long messageDate = dateExtractor.extractDate(object);
			long nowDate = new Date().getTime();
			long messageDate2 = calculateDayIndpendentTime(messageDate);
			long nowDate2 = calculateDayIndpendentTime(nowDate);
			scheduledExecutor.schedule(new Runnable() {				
				@Override
				public void run() {
					callback.replay(object);					
				}
			}, Math.abs(nowDate2-messageDate2), TimeUnit.MILLISECONDS);
			
		}
	}

	private long calculateDayIndpendentTime(long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		//We set any old common day month and year
		calendar.set(1981,12,14);
		return  calendar.getTimeInMillis();
	}

	private List<T> getReplayData() {
		List<NormalizedMessage> messages = dataRetriever.retrieve(AMOUNT_TO_RETRIEVE);
		List<T> objects = new ArrayList<>();
		for(NormalizedMessage message : messages){
			objects.add(normalizedInverseTransformer.transform(message));
		}
		return objects;
	}

	private class DateComparator implements Comparator<T>{
		@Override
		public int compare(T o1, T o2) {
			Long date1 = dateExtractor.extractDate(o1);
			Long date2 = dateExtractor.extractDate(o2);
			return date1.compareTo(date2);
		}		
	}
	
	public void setDateExtractor(DateExtractor<T> dateExtractor) {
		this.dateExtractor=dateExtractor;
	}

	public void setNormalizedTransformer(MessageDenormalizer<T> normalizedInverseTransformer) {
		this.normalizedInverseTransformer=normalizedInverseTransformer;
	}

	public void setDataRetriever(DataRetriever<?> dataRetriever) {
		this.dataRetriever=dataRetriever;
	}
}
