package com.kolbly.mortgagecomparer.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.database.Cursor;

import com.kolbly.android.util.DateUtil;
import com.kolbly.global.G;
import com.kolbly.java.util.StrUtil;

/**
 * Loan data
 */
public class Loan extends DbRow
{
	/** Bundle key to pass around the loan id between Activities and fragments */
	public static final String KEY_LOAN_ID = "KEY_LOAN_ID";
	
	private static final int NUM_MONTHS = 12;
	
	/** Database table name where this object is stored */
	public static final String TABLENAME = "Loan";

	/** Original loan amount */
	private double myLoanAmount;

	/** Interest rate (percentage, i.e. 4.75 = 4.75 percent or .0475) */
	private double myInterestRatePct;

	/** Length of loan (months) */
	private int myLoanTerm;
	
	/** Excess monthly payment (amount over the minimum monthly payment) */
	private double myExtraPayment = 0;

	/** User given loan name */
	private String myName = "";

	/** Original loan date */
	private Date myLoanStartDate = new Date(0);	

	/** True if any data has changed after instantiation */
	@SuppressWarnings("unused")
	private boolean myDirtyFlag = false;								// NOSONAR									

	@SuppressWarnings("unused")
	private Loan(){}
	
	
	/**
	 * Loan Ctor from another loan
	 * <p>
	 * Loan is created/duplicated except for id which is set to 0
	 * 
	 * @param l Existing loan data
	 */
	public Loan(Loan l)
	{
		this.setId(0);
		this.myLoanAmount = l.myLoanAmount;
		this.myInterestRatePct = l.myInterestRatePct;
		this.myLoanTerm = l.myLoanTerm;
		this.myExtraPayment = l.myExtraPayment;
		this.myName = l.myName;
		this.myLoanStartDate = new Date(l.myLoanStartDate.getTime());
		this.myDirtyFlag = true;
	}
	
	/**
	 * Ctor
	 *
	 * @param loanAmount Loan amount
	 * @param interestRatePct Interest rate (percentage) i.e. 4.75 percent ==
	 *           .0475 decimal
	 * @param loanTermMonths Length of loan (months)
	 */
	public Loan(double loanAmount, double interestRatePct, int loanTermMonths)
	{
		this.myLoanAmount = loanAmount;
		this.myInterestRatePct = interestRatePct;
		this.myLoanTerm = loanTermMonths;
		
		this.myName = createDefaultName();
				
		this.myLoanStartDate = Loan.getFirstDayOfNextMonth(new Date());
	}
	

	/**
	 * Ctor for creating a Loan from a database cursor
	 * 
	 * @param c Database cursor already set to desired row
	 */
	public Loan(Cursor c)
	{
		int i = 0;
		
		this.setId(c.getInt(i++));
		this.myLoanAmount = c.getDouble(i++);
		this.myInterestRatePct = c.getDouble(i++);
		this.myLoanTerm = c.getInt(i++);
		this.myExtraPayment = c.getDouble(i++);
		this.myName = c.getString(i++);
		this.myLoanStartDate = new Date(c.getLong(i++));
	}


	/**
	 * Get how long this loan is in months
	 * 
	 * @return Length of loan (months)
	 */
	public int getLoanTerm()
	{
		return this.myLoanTerm;
	}

	/**
	 * Get the months part of the loan term when loan term is defined by (years &
	 * months)
	 * 
	 * @return Months part of loan term [1..11]
	 */
	public int getMonths()
	{
		return this.myLoanTerm % NUM_MONTHS;															
	}

	/**
	 * Convenience method to return the months portion of the loan
	 * 
	 * @see Loan#getMonths()
	 * 
	 * @return Months part of loan term 
	 */
	public String getMonthsString()
	{
		int months = this.getMonths();
		
		return String.valueOf(months);
	}
	
	/**
	 * Update the months part of the current loan term
	 * <p>
	 * Important note: The years portion of the current loan term remains unchanged
	 * 
	 * @param month New month portion of loan term
	 */
	public void setMonths(int month)
	{
		this.myDirtyFlag = true;
		
		int year = this.getYears();
		
		this.myLoanTerm = (NUM_MONTHS * year) + month;									
	}
	
	
	/**
	 * Get how long this loan is in years
	 * 
	 * @return Years part of loan term
	 */
	public int getYears()
	{
		if (this.myLoanTerm == 0)
		{
			return 0;
		}
		else
		{
			return (int) (this.myLoanTerm / NUM_MONTHS);										
		}
	}

	/**
	 * Convenience method to return the years portion of the loan
	 * 
	 * @see Loan#getYears()
	 * 
	 * @return Years part of loan term 
	 */
	public String getYearsString()
	{
		int years = this.getYears();
		
		return String.valueOf(years);
	}
	
	/**
	 * Update the loan term years value
	 * <p>
	 * Important note: Months remains unchanged
	 * 
	 * @param years New loan length years value
	 */
	public void setYears(int years)
	{		
		this.myDirtyFlag = true;
		
		int months = this.getMonths();
		
		// Set new total loan term (months) - months remainder remains unchanged from before
		
		int newLoanTerm = (years * NUM_MONTHS) + months;
		
		this.myLoanTerm = newLoanTerm;
	}
	
	
	/**
	 * @return Amount of this loan
	 */
	public double getLoanAmount()
	{
		return this.myLoanAmount;
	}
	
	/**
	 * Get the loan amount in string format
	 * 
	 * @return Loan amount string
	 */
	public String getLoanAmountString()
	{
		return String.format(G.getLocale(), "%.2f", this.myLoanAmount);			// NOSONAR
	}

	/**
	 * Set the loan amount
	 * 
	 * @param amount Loan amount
	 */
	public void setLoanAmount(double amount)
	{
		this.myDirtyFlag = true;
		
		this.myLoanAmount = amount;	
	}
	
	/**
	 * Get the loan start date
	 * 
	 * @return Loan starting date
	 */
	public Date getLoanStartDate()
	{
		return new Date(this.myLoanStartDate.getTime());
	}

	/**
	 * Get the loan start date localized string
	 * 
	 * @return Loan starting date localized string
	 */
	public String getLoanStartDateString()
	{
		return DateUtil.formatDateString(this.myLoanStartDate);	
	}
	
	/**
	 * Set the loan start date
	 * 
	 * @param date Loan start date
	 */
	public void setLoanStartDate(Date date)
	{
		this.myLoanStartDate = new Date(date.getTime());
		
	}
	
	/**
	 * @return Monthly decimal interest rate (i.e. .0475 == 4.75 percent)
	 */
	public double getMonthlyInterestRate()
	{
		return this.myInterestRatePct / (NUM_MONTHS * 100);				// NOSONAR						
	}

	/**
	 * @return Annual interest rate percentage (i.e. 4.75 percentage)
	 */
	public double getInterestRatePct()
	{
		return this.myInterestRatePct;
	}

	/**
	 * Set the loan interest rate precentage (i.e. 4.75%)
	 * 
	 * @param pct Interest rate percentage
	 */
	public void setInterestRatePct(double pct)
	{
		this.myDirtyFlag = true;
		
		this.myInterestRatePct = pct;
	}
	
	/**
	 * @return Annual interest rate percentage (i.e. 4.75 percentage)
	 */
	public String getInterestRatePctString()
	{
		return String.format(com.kolbly.global.G.getLocale(), "%.2f", this.myInterestRatePct);
	}

	/**
	 * Get the actual monthly payment
	 * <p>
	 * Note: This may or may not be the minimum monthly payment
	 */
	public String getMonthlyPaymentString()
	{
		double monthlyPayment = this.getMonthlyPayment();

		return String.format(G.getLocale(), "%.2f", monthlyPayment);
	}

	/**
	 * @return Monthly payment amount
	 */
	public double getMonthlyPayment()
	{
		return this.getMinimumPayment() + this.myExtraPayment;
	}
	
	/* (non-Javadoc)
	 * @see com.kolbly.mortgagecomparer.db.DbTable#getContentValues()
	 */
	@Override
	public ContentValues getContentValues()
	{
		ContentValues cv = new ContentValues();

		cv.put(IDENTITY, this.getId());
		cv.put("loanAmount", this.myLoanAmount);
		cv.put("interestRatePct", this.myInterestRatePct);
		cv.put("loanTermMonths", this.myLoanTerm);
		cv.put("extraPayment", this.myExtraPayment);		
		cv.put("name", this.myName);
		cv.put("loanStartDate", this.myLoanStartDate.getTime());

		return cv;
	}

	
	/**
	 * Set how much excess payment to apply each month to the loan in addition to
	 * the minimum monthly amount
	 * <p>
	 * Side effect: Will adjust monthly payment accordingly
	 * 
	 * @param extraPayment
	 */
	public void setExtraPayment(double extraPayment)
	{
		this.myExtraPayment = extraPayment;
	}

	/**
	 * How much extra per month is being paid on this loan above the minimum
	 * 
	 * @return Excess payment each month for this loan
	 */
	public double getExtraPayment()
	{
		return this.myExtraPayment;
	}

	/**
	 * How much extra per month is being paid on this loan above the minimum
	 * 
	 * @return Excess payment each month for this loan
	 */
	public String getExtraPaymentString()
	{
		double extraPayment = this.getExtraPayment();

		return String.format(G.getLocale(), "%.2f", extraPayment);
	}

	/**
	 * Calculate the minimum monthly payment for this loan
	 * 
	 * @return This loan's monthly minimum payment
	 */
	public double getMinimumPayment()
	{
		double monthlyMinimumPayment = Loan.getMonthlyMinimumPayment(this.myLoanAmount, this.myInterestRatePct,
			this.myLoanTerm);

		return monthlyMinimumPayment;
	}

	/**
	 * Calculate the minimum monthly payment for this loan
	 * 
	 * @return This loan's monthly minimum payment
	 */
	public String getMinimumPaymentString()
	{
		double minimumPayment = this.getMinimumPayment();

		return String.format(G.getLocale(), "%.2f", minimumPayment);
	}

	/* (non-Javadoc)
	 * @see com.kolbly.mortgagecomparer.db.DbTable#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return TABLENAME;
	}

	/**
	 * Calculate the minimum monthly payment necessary for the given parameters
	 * 
	 * @param principal Starting loan amount
	 * @param interestRatePct Interest rate (percentage, i.e. 4.75 == .0475)
	 * @param months Loan duration in months
	 * @return Minimum monthly payment
	 */
	public static double getMonthlyMinimumPayment(double principal, double interestRatePct, int months)
	{
		// Principal - initial amount of loan
		double p = principal;

		// Monthly interest in decimal form
		double j = interestRatePct / (NUM_MONTHS * 100); 					// NOSONAR

		// Number of months over which loan is amortized
		double n = months;

		// Monthly payment
		double monthlyMinimumPayment = p * (j / (1 - Math.pow(1 + j, -n)));

		return monthlyMinimumPayment;
	}

	
	/**
	 * Create a default loan name from existing loan values
	 * 
	 * @return Default loan name
	 */
	private String createDefaultName()
	{
		return String.format(G.getLocale(), "%.0f %.2f", this.myLoanAmount, this.myInterestRatePct);		
	}
	
	/**
	 * Get the name of this loan - if user has not explicitly set a name, then
	 * just use the interest rate and the database identifier
	 * 
	 * @return Name of loan
	 */
	public String getName()
	{
		if (StrUtil.isNullOrEmpty(this.myName))
		{
			this.myName = this.createDefaultName();
		}
		
		return this.myName;
	}

	/**
	 * Sets the name of this loan
	 * 
	 * @param loanName Name of loan
	 */
	public void setName(String loanName)
	{
		this.myName = loanName;
	}

	/**
	 * Get the first day of next month Date
	 * 
	 * @param now Current time
	 * @return First day of next month
	 */
	public static Date getFirstDayOfNextMonth(Date now)
	{
		Calendar cal = GregorianCalendar.getInstance();
		
		cal.setTime(now);
		cal.add(GregorianCalendar.MONTH, 1);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		Date firstOfNextMonth = cal.getTime();

		return firstOfNextMonth;
	}
	
}
