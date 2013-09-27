package com.kolbly.android.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.kolbly.android.util.date.DateFormat;
import com.kolbly.android.util.date.SystemDateFormat;
import com.kolbly.global.G;

/**
 * Date utilities
 * 
 * @author mkolbly
 */
public final class DateUtil
{
	/**
	 * Private ctor for utility class
	 */
	private DateUtil() {};

	/**
	 * Get the first day of the next month following 'now'
	 * 
	 * @param now Date now - or starting day to retrieve the following first of
	 * 	the month date from
	 * 
	 * @return First of the month date following now
	 */
	public static Date getFirstDayOfNextMonth(Date now)
	{
		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
		
		cal.setTime(now);
		cal.add(GregorianCalendar.MONTH, 1);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date firstOfNextMonth = cal.getTime();

		return firstOfNextMonth;
	}
	
	
	/**
	 * Get the first day of the next month
	 * 
	 * @return First day of next month
	 */
	public static Date getFirstDayOfNextMonth()
	{
		Date now = new Date();
		
		return DateUtil.getFirstDayOfNextMonth(now);	
	}
	
	/**
	 * Get a formatted date string according to the OS settings
	 * 
	 * @param date Date to format
	 * 
	 * @return Formatted date string
	 */
	public static String formatDateString(Date date)
	{
		SystemDateFormat systemDateFormat = SystemDateFormat.instance();
		
		DateFormat osFormat = systemDateFormat.getFormat();
				
		String formatString = osFormat.getFormatString();  
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatString, G.getLocale());
	
		String startDateString = sdf.format(date);
	
		return startDateString;
	}
	
}
