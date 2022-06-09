package it.finanze.sanita.fse2.ms.gtwstatuscheckms.utility;

import java.util.Calendar;
import java.util.Date;

public final class DateUtility {

	public static Date setDateTo2359(final Date date) {
		if (date == null) {
			return null;
		}
		final Date result = (Date) date.clone();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(result);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		result.setTime(cal.getTime().getTime());

		return result;
	}

	/**
	 * Setta il time della data di riferimento alle 00:00:00:000.
	 * 
	 * @param date
	 * @return
	 */
	public static Date setDateTo0000(final Date date) {
		if (date == null) {
			return null;
		}
		final Date result = (Date) date.clone();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(result);
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 000);
		result.setTime(cal.getTime().getTime());

		return result;
	}
}
