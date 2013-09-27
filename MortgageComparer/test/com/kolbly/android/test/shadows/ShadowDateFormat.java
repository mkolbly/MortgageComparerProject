//package com.kolbly.android.test.shadows;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.robolectric.annotation.Implementation;
//import org.robolectric.annotation.Implements;
//import org.robolectric.annotation.RealObject;
//
//import android.content.Context;
//
//
//
//@Implements(android.text.format.DateFormat.class)
//public class ShadowDateFormat
//{
//	@RealObject private android.text.format.DateFormat realDateFormat;
//	
//	private static char[] myDateFormatOrder = new char[] {'m', 'd', 'y'};
//	
//	public void __constructor__()
//	{
//		
//	}
//	
//	/**
//	 * This function blows up Robolectric's test so we'll fake it here instead
//	 * 
//	 * @param context Context
//	 * @return {'M', 'd', 'y'}, {'d', 'M', 'y'} or {'y', 'M', 'd'}  
//	 * 
//	 * @see android.text.format.DateFormat#getDateFormatOrder(Context)
//	 */
//	@Implementation
//	public static char[] getDateFormatOrder(Context context)
//	{
//		return myDateFormatOrder;
//	}
//	
//	
//	/**
//	 * Set the formatOrder that this class will return
//	 * 
//	 * @param formatOrder {'M', 'd', 'y'}, {'d', 'M', 'y'} or {'y', 'M', 'd'}
//	 * 
//	 * @see android.text.format.DateFormat#getDateFormatOrder(Context)
//	 */
//	public static void setDateFormatOrder(char[] formatOrder)
//	{	
//		// Make sure a valid setting is set
//		List<String> validStrings = Arrays.asList(new String[] {"Mdy", "dMy", "yMd"});
//		
//		String formatOrderString = new String(formatOrder);
//		
//		if (! validStrings.contains(formatOrderString))
//		{
//			throw new IllegalArgumentException("Invalid formatOrder : " +  formatOrderString);
//		}
//		
//		myDateFormatOrder = formatOrder;
//	}
//	
//}
