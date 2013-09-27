package com.kolbly.android.util.time;

/**
 * Event thrown when the user has changed the time format in the OS Settings
 * 
 * @author mkolbly
 */
public class SystemTimeFormatEvent
{
	/** Current system time format from the OS settings */
	private TimeFormat myFormat;
	
	@SuppressWarnings("unused")
	private SystemTimeFormatEvent(){}
	
	/**
	 * Ctor with latest system time format
	 * 
	 * @param format Latest system time format as set in the OS
	 */
	public SystemTimeFormatEvent(TimeFormat format)
	{
		this.myFormat = format;
	}
	
	/**
	 * Get the current OS time setting
	 * 
	 * @return Current OS time setting
	 */
	public TimeFormat getFormat()
	{
		return this.myFormat;
	}
}
