package org.easytechs.recordpersister.utils;

import java.util.Calendar;

public class DateUtilsImpl implements DateUtils{

	@Override
	public int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}

	@Override
	public int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	@Override
	public Object getCurrentHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

}
