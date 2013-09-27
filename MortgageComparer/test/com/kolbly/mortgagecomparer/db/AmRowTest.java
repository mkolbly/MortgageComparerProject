package com.kolbly.mortgagecomparer.db;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;
import com.kolbly.android.util.date.DateFormat;
import com.kolbly.android.util.date.SystemDateFormat;

@RunWith(RobolectricTestRunner.class)
public class AmRowTest extends MCTest
{
	public AmRowTest() throws Exception
	{
		super();
	}

	@Before
	public void setUp()
	{
		SystemDateFormat.overrideSystemDateFormat(DateFormat.MMDDYYYY);
	}

	@After
	public void tearDown()
	{
		SystemDateFormat.instance().clear();

	}

	@Test
	public void test()
	{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(1966, Calendar.FEBRUARY, 19);

		AmRow row = new AmRow(1, cal.getTime(), 30000, 500, 400, 100, 9000, 4000, 29600);

		assertEquals("1", row.getPaymentNumberString());
		assertEquals("02/19/66", row.getPaymentDateString());
		assertEquals("30000.00", row.getBeginningBalanceString());
		assertEquals("500.00", row.getPaymentString());
		assertEquals("400.00", row.getPrincipalPaidString());
		assertEquals("100.00", row.getInterestPaidString());
		assertEquals("9000.00", row.getCumulativePrincipalPaidString());
		assertEquals("4000.00", row.getCumulativeInterestPaidString());
		assertEquals("29600.00", row.getEndingBalanceString());
		
		String buf = row.toString();
		assertEquals("1,02/19/66,30000.00,500.00,400.00,100.00,9000.00,4000.00,29600.00", buf);
		
	}

	@Test
	public void test2()
	{
		SystemDateFormat.overrideSystemDateFormat(DateFormat.DDMMYYYY);
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(1966, Calendar.FEBRUARY, 19);

		AmRow row = new AmRow(1, cal.getTime(), 30000, 500, 400, 100, 9000, 4000, 29600);

		assertEquals("1", row.getPaymentNumberString());
		assertEquals("19/02/66", row.getPaymentDateString());
		assertEquals("30000.00", row.getBeginningBalanceString());
		assertEquals("500.00", row.getPaymentString());
		assertEquals("400.00", row.getPrincipalPaidString());
		assertEquals("100.00", row.getInterestPaidString());
		assertEquals("9000.00", row.getCumulativePrincipalPaidString());
		assertEquals("4000.00", row.getCumulativeInterestPaidString());
		assertEquals("29600.00", row.getEndingBalanceString());
	}
	
	@Test
	public void test3()
	{
		SystemDateFormat.overrideSystemDateFormat(DateFormat.YYYYMMDD);
		
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(1966, Calendar.FEBRUARY, 19);

		AmRow row = new AmRow(1, cal.getTime(), 30000, 500, 400, 100, 9000, 4000, 29600);

		assertEquals("1", row.getPaymentNumberString());
		assertEquals("66/02/19", row.getPaymentDateString());
		assertEquals("30000.00", row.getBeginningBalanceString());
		assertEquals("500.00", row.getPaymentString());
		assertEquals("400.00", row.getPrincipalPaidString());
		assertEquals("100.00", row.getInterestPaidString());
		assertEquals("9000.00", row.getCumulativePrincipalPaidString());
		assertEquals("4000.00", row.getCumulativeInterestPaidString());
		assertEquals("29600.00", row.getEndingBalanceString());
	}	
	

	
}
