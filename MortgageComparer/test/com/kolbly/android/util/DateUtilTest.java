package com.kolbly.android.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;
import com.kolbly.android.util.date.DateFormat;
import com.kolbly.android.util.date.SystemDateFormat;
import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;


@RunWith(RobolectricTestRunner.class)
//@Config(shadows = {ShadowSystemDateFormat.class})
//@Config(shadows = {ShadowDateFormat.class})
public class DateUtilTest extends MCTest
{
	public DateUtilTest() throws Exception
	{
		super();
	}

	@After
	public void tearDown()
	{
		SystemDateFormat.instance().clear();
	}
	
	
	@Test
	public void test()
	{
		ClassUtil.callPrivateConstructor(DateUtil.class);
	}

	@Test
	public void testGetFirstDayOfNextMonth()
	{
		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
		cal.set(2000, Calendar.MARCH, 5);
		Date now = cal.getTime();
		
		Date firstDay = DateUtil.getFirstDayOfNextMonth(now);
		
		cal.setTime(firstDay);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		
		assertEquals(2000, year);
		assertEquals(Calendar.APRIL, month);
		assertEquals(1, day);	
	}
	
	@Test
	public void testGetFirstDayOfNextMonthDefault()
	{
		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
		Date now = new Date();
		cal.setTime(now);
		
		Date firstDay = DateUtil.getFirstDayOfNextMonth();
		
		cal.setTime(firstDay);
		
		int day = cal.get(Calendar.DATE);
		
		assertEquals(1, day);
	
	}
	
	@Test
	public void testFormatDateString()
	{
		String formatted;
		
		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
		cal.set(2000, Calendar.MARCH, 5);
			
		SystemDateFormat.overrideSystemDateFormat(DateFormat.MMDDYYYY);	
		formatted = DateUtil.formatDateString(cal.getTime());		
		assertEquals("03/05/2000", formatted);
		
		SystemDateFormat.overrideSystemDateFormat(DateFormat.DDMMYYYY);
		formatted = DateUtil.formatDateString(cal.getTime());
		assertEquals("05/03/2000", formatted);
		

		SystemDateFormat.overrideSystemDateFormat(DateFormat.YYYYMMDD);
		formatted = DateUtil.formatDateString(cal.getTime());
		assertEquals("2000/03/05", formatted);
		
		
	}
	
	@Test
	public void testFormatDateString2()
	{
//		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
//		cal.set(2000, Calendar.MARCH, 5);
//				
//		ShadowSystemDateFormat.setFormat(DateFormat.DDMMYYYY);
//		
//		String formatted = DateUtil.formatDateString(cal.getTime());
//		
//		assertEquals("05/03/2000", formatted);
//		
	}
	
}
