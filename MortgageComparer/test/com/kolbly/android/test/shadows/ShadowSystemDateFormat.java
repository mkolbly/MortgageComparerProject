//package com.kolbly.android.test.shadows;
//
//import org.robolectric.annotation.Implementation;
//import org.robolectric.annotation.Implements;
//import org.robolectric.annotation.RealObject;
//
//import com.kolbly.android.util.date.DateFormat;
//import com.kolbly.android.util.date.SystemDateFormat;
//
///**
// * Shadow for our date formatting class - gives us the ability to set the
// * default OS date formatting for our unit tests
// * 
// * @author mkolbly
// */
//@Implements(SystemDateFormat.class)
//public class ShadowSystemDateFormat
//{
//	@RealObject private SystemDateFormat realObject;
//	
//	private static DateFormat myOverrideDateFormat = DateFormat.MMDDYYYY;
//	
//	public void __constructor__()
//	{
//		
//	}
//	
//	public static void setFormat(DateFormat format)
//	{
//		myOverrideDateFormat = format;
//	}
//	
//	/**
//	 * Override system value with our shadow's value
//	 * 
//	 * @return Date format manually set in shadow
//	 */
//	@Implementation
//	public DateFormat getFormat()
//	{
//		return myOverrideDateFormat;
//	}
//	
//	/**
//	 * Override system value with our shadow's value
//	 * 
//	 * @param refresh true if you want to force a refresh of the current value
//	 * 	from the OS - nonfunctional for this shadow
//	 * @return Date format manually set in shadow
//	 */
//	@Implementation
//	public DateFormat getFormat(boolean refresh)
//	{
//		// Skip requerying the OS
//		return myOverrideDateFormat;
//	}
//}
