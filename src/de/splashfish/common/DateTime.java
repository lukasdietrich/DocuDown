package de.splashfish.common;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 
 * @author lukas
 *
 */
public class DateTime {

	private static final DecimalFormat twoDigget 	= new DecimalFormat("00");
	private static final DecimalFormat threeDigget 	= new DecimalFormat("000");
	
	/**
	 * current formats a given {@link String} and includes the current time and date
	 * 
	 * @param format %n, %s, %m, %h will be replaced by milliseconds, seconds, minutes and hours, whereas
	 * %d, %M and %y will be replaced by day, month and year
	 * @return formatted string containing the current date and time
	 */
	public static String current(String format) {
		return forMillis(format, System.currentTimeMillis());
	}
	
	/**
	 * forMillis formats a given {@link String} and includes the time and date
	 * matching the given timestamp
	 * 
	 * @param format %n, %s, %m, %h will be replaced by milliseconds, seconds, minutes and hours, whereas
	 * %d, %M and %y will be replaced by day, month and year
	 * @param timestamp
	 * @return formatted string containing the matching date and time
	 */
	public static String forMillis(String format, long timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		
		// time
		format = format.replace("%n", threeDigget.format(cal.get(Calendar.MILLISECOND)));
		format = format.replace("%s", twoDigget.format(cal.get(Calendar.SECOND)));
		format = format.replace("%m", twoDigget.format(cal.get(Calendar.MINUTE)));
		format = format.replace("%h", twoDigget.format(cal.get(Calendar.HOUR_OF_DAY)));
		
		// date
		format = format.replace("%d", twoDigget.format(cal.get(Calendar.DAY_OF_MONTH)));
		format = format.replace("%M", twoDigget.format(cal.get(Calendar.MONTH)+1)); // because month is always one less than real
		format = format.replace("%y", String.valueOf(cal.get(Calendar.YEAR)));
		
		return format;
	}
	
}
