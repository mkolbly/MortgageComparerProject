package com.kolbly.android.util.date;

/**
 * Event thrown when the user has changed the date format in the OS Settings
 * 
 * @author mkolbly
 */
public class SystemDateFormatEvent
{
	/** Current date format set in OS settings */
	private DateFormat myFormat;
	
	@SuppressWarnings("unused")
	private SystemDateFormatEvent(){}
	
	/**
	 * Create with the latest date format as set in the OS settings
	 * 
	 * @param format Current date format
	 */
	public SystemDateFormatEvent(DateFormat format)
	{
		this.myFormat = format;
	}
	
	/**
	 * Get current date format as set in the OS
	 * 
	 * @return Current date format as set in the OS settings
	 */
	public DateFormat getFormat()
	{
		return this.myFormat;
	}
}
