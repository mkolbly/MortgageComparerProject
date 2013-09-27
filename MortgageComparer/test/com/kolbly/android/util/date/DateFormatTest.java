package com.kolbly.android.util.date;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;

@RunWith(RobolectricTestRunner.class)
public class DateFormatTest extends MCTest
{

	public DateFormatTest() throws Exception
	{
		super();
	}

	@Test
	public void testLookup()
	{		
		assertEquals(DateFormat.MMDDYYYY, DateFormat.lookup("mm/dd/yyyy"));
		assertEquals(DateFormat.DDMMYYYY, DateFormat.lookup("dd/mm/yyyy"));
		assertEquals(DateFormat.YYYYMMDD, DateFormat.lookup("yyyy/mm/dd"));
	
		assertEquals(DateFormat.MMDDYYYY, DateFormat.lookup(new char[] {'M', 'd', 'y'}));
		assertEquals(DateFormat.DDMMYYYY, DateFormat.lookup(new char[] {'d', 'M', 'y'}));
		assertEquals(DateFormat.YYYYMMDD, DateFormat.lookup(new char[] {'y', 'M', 'd'}));	
	}
	
	@Test
	public void testLookupNull()
	{
		String nullString = null;
		
		assertNull(DateFormat.lookup(nullString));
	}
	
	@Test
	public void testFailedLookup()
	{
		assertNull(DateFormat.lookup(new char[] {'x', 'y', 'z'}));
	}
	
	@Test
	public void testFormatString()
	{
		assertEquals("MM/dd/yyyy", DateFormat.MMDDYYYY.getFormatString());
		assertEquals("dd/MM/yyyy", DateFormat.DDMMYYYY.getFormatString());
		assertEquals("yyyy/MM/dd", DateFormat.YYYYMMDD.getFormatString());
	}

	
	@Test
	public void testGetFormat()
	{
		assertEquals("mm/dd/yyyy", DateFormat.MMDDYYYY.getFormat());
		assertEquals("dd/mm/yyyy", DateFormat.DDMMYYYY.getFormat());
		assertEquals("yyyy/mm/dd", DateFormat.YYYYMMDD.getFormat());
	}
	
}
