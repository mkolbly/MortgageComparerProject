//package com.kolbly.java.util;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//
///**
// * Exception utilities
// * <p>
// * D'oh - found out org.apache.commons already has an extensive set of these.
// * We'll keep this around on the chance we decide to not use apache utilities
// * in the future.
// * 
// * Note : Use the org.apache.commons.ExceptionUtils instead
// */
//public final class ExceptionUtil
//{
//	/**
//	 * Private ctor for utility class
//	 */
//	private ExceptionUtil()
//	{
//	}
//
//	/**
//	 * Simply returns the stack trace of the given Throwable as a string
//	 * 
//	 * @param throwable Throwable to get the stack trace for
//	 * @return Stack trace for throwable
//	 */
//	public static String getStackTrace(Throwable throwable)
//	{
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw, true);
//		throwable.printStackTrace(pw);
//		return sw.getBuffer().toString();
//	}
//}
