package com.kolbly.mortgagecomparer.db;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;
import com.kolbly.android.util.date.DateFormat;
import com.kolbly.android.util.date.SystemDateFormat;

@RunWith(RobolectricTestRunner.class)
public class AmortizationTest extends MCTest
{

	public AmortizationTest() throws Exception
	{
		super();
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testStandardLoan() throws IOException
	{
		SystemDateFormat.overrideSystemDateFormat(DateFormat.MMDDYYYY);

		Calendar loanStartDate = new GregorianCalendar(2013, GregorianCalendar.OCTOBER, 1);

		double loanAmount = 346500;
		double interestRatePct = 3.25;
		int loanTermMonths = 30 * 12;

		Loan loan = new Loan(loanAmount, interestRatePct, loanTermMonths);
		loan.setName("loan1");
		loan.setLoanStartDate(loanStartDate.getTime());

		Amortization a = new Amortization(loan);

		List<AmRow> rows = a.getAmortizationData();
		AmRow r = rows.get(0);

		assertEquals(1, r.getPaymentNumber());
		assertEquals("1", r.getPaymentNumberString());

		assertEquals(loanStartDate.getTime(), r.getPaymentDate());
		assertEquals(346500, r.getBeginningBalance(), .01);
		assertEquals(1507.99, r.getPayment(), .01);
		assertEquals(569.55, r.getPrincipalPaid(), .01);
		assertEquals(938.44, r.getInterestPaid(), .01);
		assertEquals(569.55, r.getCumulativePrincipalPaid(), .01);
		assertEquals(938.44, r.getCumulativeInterestPaid(), .01);
		assertEquals(345930.45, r.getEndingBalance(), .01);

		r = rows.get(189);
		assertEquals(190, r.getPaymentNumber());
		assertEquals(new GregorianCalendar(2029, GregorianCalendar.JULY, 1).getTime(), r.getPaymentDate());
		assertEquals(206177.18, r.getBeginningBalance(), .01);
		assertEquals(1507.99, r.getPayment(), .01);
		assertEquals(949.59, r.getPrincipalPaid(), .01);
		assertEquals(558.4, r.getInterestPaid(), .01);
		assertEquals(141272.42, r.getCumulativePrincipalPaid(), .01);
		assertEquals(145245.66, r.getCumulativeInterestPaid(), .01);
		assertEquals(205227.58, r.getEndingBalance(), .01);

		r = rows.get(359);
		assertEquals(360, r.getPaymentNumber());
		assertEquals(new GregorianCalendar(2043, GregorianCalendar.SEPTEMBER, 1).getTime(), r.getPaymentDate());
		assertEquals(1503.92, r.getBeginningBalance(), .01);
		assertEquals(1507.99, r.getPayment(), .01);
		assertEquals(1503.92, r.getPrincipalPaid(), .01);
		assertEquals(4.07, r.getInterestPaid(), .01);
		assertEquals(346500, r.getCumulativePrincipalPaid(), .01);
		assertEquals(196376.36, r.getCumulativeInterestPaid(), .01);
		assertEquals(0, r.getEndingBalance(), .01);

	}

	public void writeAmortizationFile(Amortization am, String file) throws IOException
	{
		File f = new File(file);

		f.createNewFile();

		FileWriter fw = new FileWriter(f, false);
		BufferedWriter bw = new BufferedWriter(fw);

		//bw.append(AmRow.getHeader());
		bw.newLine();

		List<AmRow> rows = am.getAmortizationData();

		for (AmRow r : rows)
		{
			bw.append(r.toString());
			bw.newLine();
		}

		bw.close();

	}

}
