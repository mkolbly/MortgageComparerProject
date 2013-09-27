package com.kolbly.java.util;

/**
 * Convenience interface to enforce data formatting for logging purposes
 * 
 * @author mkolbly
 */
public interface Loggable
{
	/** Tab size used for indentation */
	int TABSIZE = 3;
	
	/**
	 * Create a xml string suitable for debug logging 
	 * 
	 * @return String xml representation
	 */
	String toXml();
	
	/**
	 * Create a xml string suitable for debug logging 
	 * 
	 * @param tab Tab spacing to use (1 tab == 3 spaces)
	 * @return String xml representation
	 */
	String toXml(int tab);
	
}
