package com.kolbly.mortgagecomparer.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.kolbly.android.test.MCTest;
import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.MathUtil;

/**
 * Notes:
 * <p>
 * Uses manifest in MortgageComparer project. Side effect is that any resources
 * are also loaded from there so any test resources are not available
 * 
 * @Config(manifest="../MortgageComparer/AndroidManifest.xml")
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "../MortgageComparer/AndroidManifest.xml")
public class LoanTest extends MCTest
{
	public LoanTest() throws Exception
	{
		super();
	}

	@SuppressWarnings("unused")
	private static final String TAG = ClassUtil.getShortName(LoanTest.class);

	@Before
	public void setUp() throws Exception
	{

		List<Loan> allLoans = dm.getAllLoan();

		for (Loan loan : allLoans)
		{
			dm.delete(loan);
		}

		// Stuff some loan data in our database
		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
		cal.set(2020, Calendar.JANUARY, 1);
		Date loanStartDate = cal.getTime();

		Loan l1 = new Loan(100000, 3.5, 12 * 30);
		l1.setLoanStartDate(loanStartDate);

		Loan l2 = new Loan(150000, 4.5, 12 * 30);
		l2.setLoanStartDate(loanStartDate);

		Loan l3 = new Loan(200000, 5.5, 12 * 30);
		l3.setLoanStartDate(loanStartDate);

		dm.save(l1);
		dm.save(l2);
		dm.save(l3);

	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testSave()
	{
		Loan loan = new Loan(10000, 7.5, 12 * 30);
		dm.save(loan);

		List<Loan> loans = dm.getAllLoan();
		assertEquals(4, loans.size());
	}

	@Test
	public void testPrivateCtor()
	{
		assertTrue(ClassUtil.callPrivateConstructor(Loan.class));
	}

	/**
	 * 
	 * Important note: Commenting out test this test:
	 * <p>
	 * assertEquals(l1.getMonthsString(), l2.getMonthsString())
	 * <p>
	 * Loan.getLoanStartDateString() calls
	 * android.text.format.DateFormat.getDateFormatOrder(Context) which blows up
	 * the Robolectric test runner with the following exception: <br>
	 * java.lang.RuntimeException: really, more than 64 qualifiers?!? <br>
	 * at org.robolectric.res.ResBunch.pick(ResBunch.java:45) <br>
	 * at org.robolectric.res.ResBunch.getValue(ResBunch.java:40) <br>
	 * ... <br>
	 * <p>
	 * This may be fixed in the next version of Robolectric but we'll hold off on
	 * upgrading until it's at least released
	 * 
	 */
	@Test
	public void testLoanCtor()
	{
		Loan l1 = dm.getAllLoan().get(0);

		Loan l2 = new Loan(l1);

		assertTrue(MathUtil.areEqual(l1.getLoanAmount(), l2.getLoanAmount()));
		assertEquals(l1.getLoanAmountString(), l2.getLoanAmountString());

		assertEquals(l1.getLoanTerm(), l2.getLoanTerm());
		assertEquals(l1.getName(), l2.getName());

		assertEquals(l1.getLoanStartDate(), l2.getLoanStartDate());

		// Leave commented out - see comments in method description
		// assertEquals(l1.getLoanStartDateString(), l2.getLoanStartDateString());

		assertEquals(l1.getMonths(), l2.getMonths());
		assertEquals(l1.getMonthsString(), l2.getMonthsString());

		assertEquals(l1.getYears(), l2.getYears());
		assertEquals(l1.getYearsString(), l2.getYearsString());

		assertTrue(MathUtil.areEqual(l1.getInterestRatePct(), l2.getInterestRatePct()));
		assertEquals(l1.getInterestRatePctString(), l2.getInterestRatePctString());

		assertTrue(MathUtil.areEqual(l1.getExtraPayment(), l2.getExtraPayment()));
		assertEquals(l1.getExtraPaymentString(), l2.getExtraPaymentString());

		assertTrue(MathUtil.areEqual(l1.getMonthlyPayment(), l2.getMonthlyPayment()));
		assertEquals(l1.getMonthlyPaymentString(), l2.getMonthlyPaymentString());

		assertTrue(MathUtil.areEqual(l1.getMinimumPayment(), l2.getMinimumPayment()));
		assertEquals(l1.getMinimumPaymentString(), l2.getMinimumPaymentString());

		assertTrue(MathUtil.areEqual(l1.getMonthlyInterestRate(), l2.getMonthlyInterestRate()));

	}

	@Test
	public void testSetters() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		Loan l1 = dm.getAllLoan().get(0);
		l1.setYears(5);
		l1.setMonths(4);
		l1.setInterestRatePct(4.5);
		l1.setName(null);
		l1.setLoanAmount(50001.24);
		l1.setExtraPayment(24.92);

		Loan l2 = new Loan(l1);

		assertEquals(5, l2.getYears());
		assertEquals(4, l2.getMonths());
		assertTrue(MathUtil.areEqual(4.5d, l2.getInterestRatePct()));

		String defaultName = (String) ClassUtil.callPrivateMethod(l2, "createDefaultName");
		assertEquals(defaultName, l2.getName());

		assertTrue(MathUtil.areEqual(50001.24, l2.getLoanAmount()));
		assertTrue(MathUtil.areEqual(24.92, l1.getExtraPayment()));

	}

	@Test
	public void testToXml()
	{
		Calendar cal = GregorianCalendar.getInstance(G.getLocale());
		cal.set(2020, Calendar.JANUARY, 1, 0, 0);
		Date loanStartDate = cal.getTime();

		Loan loan = new Loan(10000, 7.5, 12 * 30);
		loan.setName("Some Loan");
		loan.setExtraPayment(24.92);
		loan.setLoanStartDate(loanStartDate);

		String buf = loan.toXml();
		buf = buf.trim();

		assertTrue(buf.startsWith("<Loan : Table = Loan>"));
		assertTrue(buf.contains("<loanAmount>10000.0</loanAmount>"));
		assertTrue(buf.contains("<_id>0</_id>"));
		assertTrue(buf.contains("<extraPayment>24.92</extraPayment>"));
		assertTrue(buf.contains("<name>Some Loan</name>"));
		assertTrue(buf.contains("<loanTermMonths>360</loanTermMonths>"));
		assertTrue(buf.contains("<interestRatePct>7.5</interestRatePct>"));
		assertTrue(buf.endsWith("</Loan>"));
	}

}
