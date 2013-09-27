package com.kolbly.java.util;

/**
 * Format helper 
 *
 * @author mkolbly
 */
public final class Spaces
{
	/**
	 * Private ctor for utility class
	 */
	private Spaces() {}
	
	/**
	 * Return a String composed of the given number of spaces
	 * 
	 * @param times How many spaces to return
	 * @return String of spaces
	 */
	public static String repeat(int times)
	{
		StringBuffer b = new StringBuffer();

		for (int i = 0; i < times; i++)
		{
			b.append(" ");
		}

		return b.toString();
	}

}
