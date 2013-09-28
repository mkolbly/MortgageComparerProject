package com.kolbly.java.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;

/**
 * Tests for our Interval 
 * <p>
 * 
 * TODO: Add lots more corner test cases
 * 
 * @author mkolbly
 */
@RunWith(RobolectricTestRunner.class)
public class IntervalTest extends MCTest
{

	public IntervalTest() throws Exception
	{
		super();
	}

	
	@Test
	public void test1()
	{
		Calendar c1 = new GregorianCalendar(2013, 6, 1);
		Calendar c2 = new GregorianCalendar(2014, 9, 1);
		
		
		Interval i1 = new Interval(c1, c2);
		
		assertEquals(1, i1.getYearsPart());
		assertEquals(3, i1.getMonthsPart());
	}

	@Test
	public void test2()
	{
		Calendar c1 = new GregorianCalendar(2013, 9, 1);
		Calendar c2 = new GregorianCalendar(2014, 6, 1);
		
		
		Interval i1 = new Interval(c1, c2);
		
		assertEquals(0, i1.getYearsPart());
		assertEquals(9, i1.getMonthsPart());
	}
}
