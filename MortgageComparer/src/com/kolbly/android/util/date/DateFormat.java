package com.kolbly.android.util.date;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Date format as defined in the Android OS System settings
 * 
 * @author mkolbly
 */
public enum DateFormat
{
	MMDDYYYY("mm/dd/yyyy", "MM/dd/yyyy"),
	DDMMYYYY("dd/mm/yyyy", "dd/MM/yyyy"),
	YYYYMMDD("yyyy/mm/dd", "yyyy/MM/dd")	
	;
	
	
	// Our StorageState lookup map to keep track of which String maps to what DateFormat
	private static final Map<String, DateFormat> LOOKUPMAP = new HashMap<String, DateFormat>();
	
	/** Android format - as defined by the OS DateFormat setting */
	private String myFormat;
	
	/** Format String suitable for use by Date formatting functions */
	private String myFormatString;
	
	
	/**
	 * Populate LOOKUPMAP
	 */
	static
	{
		for (DateFormat df : EnumSet.allOf(DateFormat.class))
		{
			LOOKUPMAP.put(df.myFormat, df);
		}
	}
	
	
	/**
	 * Ctor
	 * 
	 * @param format Date format from Android's OS Setting
	 * @param formatString Date formatting string for use in formatting in date
	 * 	formatting functions 
	 */
	private DateFormat(String format, String formatString)
	{
		myFormat = format;
		myFormatString = formatString;
	}
	
	
	/**
	 * Get the OS date format setting
	 * 
	 * @return Android's OS date format setting
	 */
	public String getFormat()
	{
		return this.myFormat;
	}
	
	
	/**
	 * Get a formatting string 
	 *  
	 * @return Format string suitable for Java date string formatting
	 */
	public String getFormatString()
	{
		return this.myFormatString;
	}
	
	
	/**
	 * Lookup the DateFormat by it's String value
	 * 
	 * @param format String format value
	 * @return DateFormat enumerated value that corresponds with format
	 */
	public static DateFormat lookup(String format)
	{
		return DateFormat.LOOKUPMAP.get(format);
	}
	
	
	/**
	 * Lookup the OS Date format by the format order
	 * 
	 * @param formatOrder Char array containing the date format as 3 elements
	 * 	DATE, MONTH, and YEAR 
	 * 
	 * @return Corresponding format
	 */
	public static DateFormat lookup(char[] formatOrder)
	{
		switch (formatOrder[0])
		{
			case 'M':
				return DateFormat.MMDDYYYY;
			case 'd':
				return DateFormat.DDMMYYYY;
			case 'y':
				return DateFormat.YYYYMMDD;
			default:
				return null;
		}		
	}

	
}
