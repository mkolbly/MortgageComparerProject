package com.kolbly.android.util.time;

import android.annotation.SuppressLint;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * System's current time format
 * 
 * @author mkolbly
 */
public enum TimeFormat
{
	/** 12 Hour time format */
	TIME_12(12),
	
	/** 24 Hour time format */
	TIME_24(24)
	;
	
	// Our StorageState lookup map to keep track of which String maps to what DateFormat
	@SuppressLint("UseSparseArrays")
	private static final Map<Integer, TimeFormat> LOOKUPMAP = new HashMap<Integer, TimeFormat>();
	
	private int myFormat;
	
	/**
	 * Populate LOOKUPMAP
	 */
	static
	{
		for (TimeFormat tf : EnumSet.allOf(TimeFormat.class))
		{
			LOOKUPMAP.put(tf.myFormat, tf);
		}
	}
	
	/**
	 * Ctor
	 * 
	 * @param format Time format - either 12 or 24
	 */
	private TimeFormat(int format)
	{
		myFormat = format;
	}
	
	/**
	 * Get the OS time format setting
	 * 
	 * @return Android's OS time format setting
	 */
	public int getFormat()
	{
		return this.myFormat;
	}
	
	/**
	 * Lookup the TimeFormat by it's integer value
	 * 
	 * @param format integer format value
	 * @return DateFormat enumerated value that corresponds with format
	 */
	public static TimeFormat lookup(int format)
	{
		return TimeFormat.LOOKUPMAP.get(format);
	}
}
