package com.kolbly.mortgagecomparer.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.kolbly.android.util.DateUtil;
import com.kolbly.global.G;
import com.kolbly.java.util.Interval;

/**
 * Loan amortization data
 * 
 * @author mkolbly
 */
public class Amortization
{
	/** Our loan being amortized */
	private Loan myLoan;
		
	/** Current payment date */
	private Date myPaymentDate = new Date(0);							
	
	/** Total principal paid so far */
	private double myCumulativePrincipalPaid = 0;
	
	/** Total interest paid so far */
	private double myCumulativeInterestPaid = 0;			
	
	/** Current principal of loan */
	private double myPrincipal = 0;				
	
	/** Current payment number */
	private int myPaymentNumber = 0;							

	/** Total payment made over loan lifetime */
	private double myTotalPayments = 0;						

	/** Our actual decimal interest rate (not represented as a percentage) */
	private final double myMonthlyInterestRate;	
	
	/** User's birth date - set to null when user hasn't set it yet */
	private Date myBirthDate = null;
	
	/**
	 * Should calculating our results (calc()) also calculate our amortization 
	 * table at the same time
	 */
	private static final boolean CALC_AMORTIZATION_TABLE = false;

	/** Amortization table rows */
	private List<AmRow> myAmortizationData = new ArrayList<AmRow>();
	
	
	/**
	 * Ctor
	 * 
	 * Create amortization data with given loan and no birth date
	 * 
	 * @param loan Loan to amortize
	 */
	public Amortization(Loan loan)
	{
		this(loan, (Date)null);		
	}
	
	/**
	 * Ctor
	 * 
	 * Create amortization data with given loan and user
	 * 
	 * @param loan Loan to amortize
	 * @param user User to factor in 
	 */
	public Amortization(Loan loan, User user)
	{
		this(loan, user.getBirthDate()); 
	}
	
	/**
	 * Ctor
	 * 
	 * Create amortization data with given loan and borrower's birth date
	 * 
	 * @param loan Loan to amortize
	 * @param birthDate Borrower's birth date
	 */
	public Amortization(Loan loan, Date birthDate)
	{
		this.myLoan = loan;
		this.myBirthDate = (birthDate != null) ? new Date(birthDate.getTime()) : null;
		
		this.myMonthlyInterestRate = this.myLoan.getMonthlyInterestRate();
	}


	/**
	 * Clear the calculations so the next time calc() is called it will 
	 * recalculate all the results
	 */
	public void clear()
	{
		this.myPaymentNumber = 0;
		
		this.myAmortizationData.clear();
	}	

	
	/**
	 * Calculate this amortization schedule and other resulting statistics
	 */
	public void calc()
	{
		// If we've already done the calculation, then abort
		if (this.myPaymentNumber > 0)
		{
			return;
		}
		
		// Clear any existing amortization rows
		this.myAmortizationData.clear();
		
		// Principal - start out at starting loan amount
		this.myPrincipal = this.myLoan.getLoanAmount();	
		
		// Start out payment number at 0
		this.myPaymentNumber = 0;		
		
		// Set payment date to beginning of loan
		this.myPaymentDate = this.myLoan.getLoanStartDate();
		
		// No principal paid yet
		this.myCumulativePrincipalPaid = 0;
		
		// No interest paid yet
		this.myCumulativeInterestPaid = 0;	
		
		// No payments made yet
		this.myTotalPayments = 0;									
		
		while (! this.isPaidOff())
		{
			this.makeMonthlyPayment();
		}
	}
	
	/**
	 * Get the amortization table data
	 * 
	 * @return Amortization table data 
	 */
	public List<AmRow> getAmortizationData()
	{
		if (this.myAmortizationData.size() == 0)
		{
			this.myAmortizationData = Amortization.getAmortizationData(this.myLoan);
		}
		
		return this.myAmortizationData;
	}
	
	
	/**
	 * Make a monthly payment on our loan
	 */
	private void makeMonthlyPayment()
	{
		this.myPaymentNumber++;
		
		// Current monthly interest payment
		double interestPayment = this.myPrincipal * this.myMonthlyInterestRate;				
		
		// This month's payment
		double thisMonthsPayment = this.myLoan.getMonthlyPayment(); 
		
		// Principal for this month
		double principalPayment = thisMonthsPayment - interestPayment;		

		// Adjust payment if we're almost paid off
		if (thisMonthsPayment > this.myPrincipal)
		{
			thisMonthsPayment = this.myPrincipal + interestPayment;
			principalPayment = this.myPrincipal;
		}

		// New balance of loan after monthly payment made
		double endingBalance = this.myPrincipal - principalPayment;			

		this.myCumulativePrincipalPaid += principalPayment;
		this.myCumulativeInterestPaid += interestPayment;

		// Store amortization table row if set to
		if (CALC_AMORTIZATION_TABLE)
		{
			AmRow r = new AmRow(this.myPaymentNumber, this.myPaymentDate, this.myPrincipal,
				thisMonthsPayment, principalPayment, interestPayment, this.myCumulativePrincipalPaid,
				this.myCumulativeInterestPaid, endingBalance);

			this.myAmortizationData.add(r);
		}

		this.myTotalPayments += thisMonthsPayment;

		this.myPrincipal = endingBalance;

		// Add a month	
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(this.myPaymentDate);
		calendar.add(Calendar.MONTH, 1);
	
		this.myPaymentDate = calendar.getTime();
	}
	
	
	/**
	 * Is the loan paid off yet?
	 * 
	 * @return true when loan is paid off
	 */
	private boolean isPaidOff()
	{
		return (this.myPrincipal < 0.01); 	// NOSONAR
	}
	
	
	/**
	 * Get total number of payments to payoff the loan
	 * 
	 * @return Total number of installment payments to payoff the loan
	 */
	public int getTotalNumberOfPayments()
	{
		this.calc();
		
		return this.myPaymentNumber;
	}
	

	/**
	 * Get Date when this loan will be paid off
	 * 
	 * @return Loan payoff date
	 */
	public Date getPayoffDate()
	{
		this.calc();

		return new Date(this.myPaymentDate.getTime());
	}
	
	/**
	 * Get Date when this loan will be paid off
	 * 
	 * @return Loan payoff date string
	 */
	public String getPayoffDateString()
	{
		Date payoffDate = this.getPayoffDate();
				
		return DateUtil.formatDateString(payoffDate);
	}
	
	/**
	 * Get Time interval necessary to pay off the loan
	 * 
	 * @return How long it takes to pay off the loan
	 */
	public Interval getPayoffInterval()
	{
		this.calc();
		
		long startInstant = this.myLoan.getLoanStartDate().getTime();
		long endInstant = this.getPayoffDate().getTime();
					
		Interval interval = new Interval(startInstant, endInstant);
		
		return interval;
	}
	
	/**
	 * Get the total payments (Principal & Interest) paid over the lifetime
	 * of the loan
	 * 
	 * @return Total payments made for the loan 
	 */
	public double getTotalPayments()
	{
		this.calc();
		
		return this.myTotalPayments;
	}

	/**
	 * Get the total payments (Principal & Interest) paid over the lifetime
	 * of the loan
	 * 
	 * @return Total payments made for the loan (String)
	 */
	public String getTotalPaymentsString()
	{
		double totalPayments = this.getTotalPayments();
		
		return String.format(G.getLocale(), "%.2f", totalPayments);
	}
	
	/**
	 * Get the total amount of interest paid over the lifetime of the loan
	 * 
	 * @return Total interest payments for loan lifetime
	 */
	public double getTotalInterestPaid()
	{
		this.calc();
		
		return this.myCumulativeInterestPaid;
	}
	
	/**
	 * Get the total amount of interest paid over the lifetime of the loan
	 * 
	 * @return Total interest payments for loan lifetime (String)
	 */
	public String getTotalInterestPaidString()
	{
		double totalInterestPaid = this.getTotalInterestPaid();
	
		return String.format(G.getLocale(), "%.2f", totalInterestPaid);
	}
	
	
	/**
	 * Get the total amount of principal paid over the lifetime of the loan
	 * 
	 * @return Total principal paid
	 */
	public double getTotalPrincipalPaid()
	{
		this.calc();
		
		return this.myCumulativePrincipalPaid;
	}
	
	
	/**
	 * Get the total amount of principal paid over the lifetime of the loan
	 * 
	 * @return Total principal paid (String)
	 */
	public String getTotalPrincipalPaidString()
	{
		double totalPrincipalPaid = this.getTotalPrincipalPaid();
		
		return String.format(G.getLocale(), "%.2f", totalPrincipalPaid);
	}
	
	/**
	 * Get the birth date of the borrower
	 * 
	 * @return Birth date of borrower
	 */
	public Date getBirthDate()
	{
		return new Date(this.myBirthDate.getTime());
	}
	
	/**
	 * Get the age of the borrower when the loan is paid off
	 * 
	 * TODO: Get rid of the entire Joda library and create our own TimeSpan class
	 * 	(should be easy).  Joda is not playing nice with Proguard
	 * 
	 * @return Joda time period (Age of user) or null if user's birth date is
	 * 	unknown
	 */
	public Interval getPaidOffAge()
	{
		this.calc();
		
		if (this.myBirthDate == null)
		{
			return null;
		}
		
		long startInstant = myBirthDate.getTime();
		long endInstant = this.getPayoffDate().getTime();
		
		Interval i = new Interval(startInstant, endInstant);
		
		return i;
	}

	
	/**
	 * Retrieve the amortization data as a separate step
	 * 
	 * @param loan Loan information to retrieve amortization data from
	 * 
	 * @return Amortization data
	 */
	public static List<AmRow> getAmortizationData(Loan loan)
	{
		Date startDate = loan.getLoanStartDate();
		double principal = loan.getLoanAmount();
		double interestRate = loan.getInterestRatePct();
		double monthlyPayment = loan.getMonthlyPayment();
		
		return Amortization.getAmortizationData(startDate, principal, interestRate, monthlyPayment);
	}

	/**
	 * Retrieve the amortization data as a standalone step
	 * 
	 * @param startDate Starting loan payment date
	 * @param loanAmount Starting loan amount
	 * @param interestRate Annual interest rate as a pct (i.e. 4.75 %)
	 * @param monthlyPayment User's monthly payment
	 * 
	 * @return Amortization data
	 */
	public static List<AmRow> getAmortizationData(Date startDate, double loanAmount, 
		double interestRate, double monthlyPayment)
	{
		List<AmRow> rows = new ArrayList<AmRow>();

		final int numMonths = 12;
		final int pct = 100;
		
		double monthlyInterestRate = interestRate / (numMonths * pct);
		
		double principal = loanAmount;
		
		// Start out payment number at 0
		int paymentNumber = 0;		
		
		// Set current payment date to beginning of loan
		Date paymentDate = startDate; 
		
		// No principal paid yet
		double cumulativePrincipalPaid = 0;
		
		// No interest paid yet
		double cumulativeInterestPaid = 0;	
		
		// While not paid off, make monthly payments
		while (principal > 0.00)
		{
			paymentNumber++;
			
			// Current monthly interest payment
			double interestPayment = principal * monthlyInterestRate;				
			
			// This month's payment
			double thisMonthsPayment = monthlyPayment; 
			
			// Principal for this month
			double principalPayment = thisMonthsPayment - interestPayment;		

			// Adjust payment if we're almost paid off
			if (thisMonthsPayment > principal)
			{
				thisMonthsPayment = principal + interestPayment;
				principalPayment = principal;
			}

			// New balance of loan after monthly payment made
			double endingBalance = principal - principalPayment;			

			cumulativePrincipalPaid += principalPayment;
			cumulativeInterestPaid += interestPayment;

			AmRow r = new AmRow(paymentNumber, paymentDate, principal,
				thisMonthsPayment, principalPayment, interestPayment, cumulativePrincipalPaid,
				cumulativeInterestPaid, endingBalance);
				
			rows.add(r);
			
			principal = endingBalance;

			// Add a month	
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(paymentDate);
			calendar.add(Calendar.MONTH, 1);
		
			paymentDate = calendar.getTime();
		}

		return rows;
	}
		
}
