package com.kolbly.java.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Extremely lightweight time interval
 * 
 * @author mkolbly
 */
public class Interval
{
	/** Beginning time instant for our interval */
	private Calendar myStartTime;

	/** Ending time instant for our interval */
	private Calendar myEndTime;

	/**
	 * Create a time interval given the two calendar time instants
	 * 
	 * @param beginTime Starting time for this interval
	 * @param endTime Ending time for this interval
	 */
	public Interval(Calendar StartTime, Calendar endTime)
	{
		this.myStartTime = StartTime;
		this.myEndTime = endTime;
	}

	/**
	 * Create a time interval given the two time instants
	 * 
	 * @param beginTime Starting time for this interval (Milliseconds from
	 *           1/1/1970)
	 * @param endTime Ending time for this interval (Milliseconds from 1/1/1970)
	 */
	public Interval(long StartTime, long endTime)
	{
		myStartTime = new GregorianCalendar();
		myStartTime.setTimeInMillis(StartTime);

		myEndTime = new GregorianCalendar();
		myEndTime.setTimeInMillis(endTime);

	}

	/**
	 * Get the years part of this time interval
	 * 
	 * @return Years part of this time interval
	 */
	public int getYearsPart()
	{
		int totalMonths = getMonthsBetween(this.myStartTime, this.myEndTime);
		
		int years = (int)(totalMonths / 12);
		
		return years;
	}
	
	/**
	 * Get the months part of this time interval
	 * 
	 * @return Months part of this time interval (0-11)
	 */
	public int getMonthsPart()
	{
		int totalMonths = getMonthsBetween(this.myStartTime, this.myEndTime);
	
		int months = totalMonths % 12;
		
		return months;
	}
	
	/**
	 * Get the total number of whole months between the two given time instants
	 * 
	 * @param startTime Starting time instant
	 * @param endTime Ending time instant
	 * @return Number of months between the two times
	 */
	private static int getMonthsBetween(Calendar startTime, Calendar endTime)
	{
		int monthsBetween = 0;
		
		// Grab years difference 
		monthsBetween = (endTime.get(Calendar.YEAR) - startTime.get(Calendar.YEAR)) * 12;
		
		// Add in months difference
		monthsBetween += (endTime.get(Calendar.MONTH) - startTime.get(Calendar.MONTH));
		
		return monthsBetween;
	}

}
