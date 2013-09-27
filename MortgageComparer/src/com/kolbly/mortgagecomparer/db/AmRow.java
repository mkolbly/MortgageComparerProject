package com.kolbly.mortgagecomparer.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.kolbly.android.util.date.DateFormat;
import com.kolbly.android.util.date.SystemDateFormat;
import com.kolbly.global.G;

/**
 * A row of amortization schedule data for a loan
 * 
 * @author mkolbly
 */
public class AmRow
{
	/** This month's payment number */
	private int myPaymentNumber; 					
	
	/** Payment date */
	private Date myPaymentDate; 	
	
	/** Beginning balance this month */
	private double myBeginningBalance; 
	
	/** Payment this month */
	private double myPayment; 	
	
	/** Payment this month */
	private double myPrincipalPaid; 	
	
	/** Interest paid this month */
	private double myInterestPaid; 			
	
	/** Cumulative principal paid on entire loan up to this month */
	private double myCumulativePrincipalPaid; 	
	
	/** Cumulative interest paid on entire loan up to this month */
	private double myCumulativeInterestPaid; 	
	
	/** Loan balance this month */
	private double myEndingBalance; 				

	/** Date format to be used for date columns */ 
	private final SimpleDateFormat myDateFormat;
	
	/** Locale to be used in formatting */
	private final Locale myLocale = G.getLocale();
	
	private AmRow()
	{
		DateFormat df = SystemDateFormat.instance().getFormat(); 
		
		switch (df)
		{
			case DDMMYYYY:
				myDateFormat = new SimpleDateFormat("dd/MM/yy", myLocale);
				break;
			case YYYYMMDD:
				myDateFormat = new SimpleDateFormat("yy/MM/dd", myLocale);
				break;
			case MMDDYYYY:
			default:
				myDateFormat = new SimpleDateFormat("MM/dd/yy", myLocale);
				break;
		}		
	}
	

	
	/**
	 * Ctor
	 * 
	 * @param paymentNumber This month's payment number
	 * @param paymentDate Payment date
	 * @param beginningBalance Beginning balance this month
	 * @param payment Payment this month
	 * @param principalPaid Payment this month
	 * @param interestPaid Interest paid this month
	 * @param cumulativePrincipalPaid Cumulative principal paid on entire loan up to this month
	 * @param cumulativeInterestPaid Cumulative interest paid on entire loan up to this month
	 * @param endingBalance Loan balance this month
	 */
	public AmRow(int paymentNumber, Date paymentDate, double beginningBalance,
		double payment, double principalPaid, double interestPaid, double cumulativePrincipalPaid,
		double cumulativeInterestPaid, double endingBalance)
	{
		this();
		
		this.myPaymentNumber = paymentNumber;
		this.myPaymentDate = new Date(paymentDate.getTime()); 				
		this.myBeginningBalance = beginningBalance; 			
		this.myPayment = payment; 							
		this.myPrincipalPaid = principalPaid; 					
		this.myInterestPaid = interestPaid; 					
		this.myCumulativePrincipalPaid = cumulativePrincipalPaid; 
		this.myCumulativeInterestPaid = cumulativeInterestPaid; 		
		this.myEndingBalance = endingBalance; 					
	}

	
	/**
	 * Get the payment number
	 * 
	 * @return Payment number
	 */
	public int getPaymentNumber()
	{
		return this.myPaymentNumber;
	}
	
	/**
	 * Get the payment number
	 * 
	 * @return Payment number
	 */
	public String getPaymentNumberString()
	{
		return String.valueOf(myPaymentNumber);
	}

	/**
	 * Get the payment date
	 * 
	 * @return Payment date
	 */
	public Date getPaymentDate()
	{
		return new Date(this.myPaymentDate.getTime());
	}
	
	/**
	 * Get the payment date
	 * 
	 * @return Payment date
	 */
	public String getPaymentDateString()
	{
		return myDateFormat.format(this.myPaymentDate);	
	}

	
	/**
	 * Get the beginning loan balance this month
	 * 
	 * @return Beginning loan balance this month
	 */
	public double getBeginningBalance()
	{
		return this.myBeginningBalance;
	}
	
	/**
	 * Get the beginning loan balance this month
	 * 
	 * @return Beginning loan balance this month
	 */
	public String getBeginningBalanceString()
	{
		return String.format(myLocale, "%.2f", myBeginningBalance);			// NOSONAR
	}

	/**
	 * Get this month's payment
	 * 
	 * @return This month's payment
	 */
	public double getPayment()
	{
		return this.myPayment;
	}
	
	/**
	 * Get this month's payment
	 * 
	 * @return This month's payment
	 */
	public String getPaymentString()
	{
		return String.format(myLocale, "%.2f", this.myPayment);
	}

	
	/**
	 * Get principal paid this month
	 * 
	 * @return Principal paid this month
	 */
	public double getPrincipalPaid()
	{
		return this.myPrincipalPaid;
	}
	
	
	/**
	 * Get principal paid this month
	 * 
	 * @return Principal paid this month
	 */
	public String getPrincipalPaidString()
	{
		return String.format(myLocale, "%.2f", this.myPrincipalPaid);
	}

	/**
	 * Get the interest paid this month
	 * 
	 * @return Interest paid this month
	 */
	public double getInterestPaid()
	{
		return this.myInterestPaid;
	}
	
	/**
	 * Get the interest paid this month
	 * 
	 * @return Interest paid this month
	 */
	public String getInterestPaidString()
	{
		return String.format(myLocale, "%.2f", this.myInterestPaid);
	}

	/**
	 * Get the cumulative principal paid up to now
	 * 
	 * @return Cumulative principal paid
	 */
	public double getCumulativePrincipalPaid()
	{
		return this.myCumulativePrincipalPaid;
	}
	
	/**
	 * Get the cumulative principal paid up to now
	 * 
	 * @return Cumulative principal paid
	 */
	public String getCumulativePrincipalPaidString()
	{
		return String.format(myLocale, "%.2f", this.myCumulativePrincipalPaid);
	}

	/**
	 * Get the cumulative interest paid up to now
	 * 
	 * @return Cumulative interest paid
	 */
	public double getCumulativeInterestPaid()
	{
		return this.myCumulativeInterestPaid;
	}
	
	/**
	 * Get the cumulative interest paid up to now
	 * 
	 * @return Cumulative interest paid
	 */
	public String getCumulativeInterestPaidString()
	{
		return String.format(myLocale, "%.2f", this.myCumulativeInterestPaid);
	}

	/**
	 * Get the ending balance this month
	 * 
	 * @return Ending loan balance this month
	 */
	public double getEndingBalance()
	{
		return this.myEndingBalance;
	}
	
	/**
	 * Get the ending balance this month
	 * 
	 * @return Ending loan balance this month
	 */
	public String getEndingBalanceString()
	{
		return String.format(myLocale, "%.2f", this.myEndingBalance);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format(myLocale, "%d,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", this.myPaymentNumber,
			myDateFormat.format(this.myPaymentDate), this.myBeginningBalance, this.myPayment, this.myPrincipalPaid, this.myInterestPaid,
			this.myCumulativePrincipalPaid, this.myCumulativeInterestPaid, this.myEndingBalance);
	}
}
