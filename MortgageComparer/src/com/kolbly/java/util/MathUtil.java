package com.kolbly.java.util;

/**
 * Math utilities
 * 
 * @author mkolbly
 */
public final class MathUtil
{
	/** Double comparison tolerance */
	private static final double EPSILON = 0.00001d;
	
	/**
	 * Private ctor for utility class
	 */
	private MathUtil() {}
	
	/**
	 * Compare two doubles within a certain tolerance of EPSILON
	 * 
	 * @param a Comparison double #1
	 * @param b Comparison double #2
	 * @return true if they're within EPSILON of each other
	 */
	public static boolean areEqual(double a, double b)
	{
		return Math.abs(a - b) < EPSILON;
	}
}
