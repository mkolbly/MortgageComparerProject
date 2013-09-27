package com.kolbly.android.fragments;

/**
 * Accompanying event to the SetLoanNameDialog Dialog
 * <p>
 * Once the user selects a loan name from the SetLoanNameDialog, then it will
 * send this event to the Bus for any listeners to consume
 * 
 * @author mkolbly
 */
public class SetLoanNameDialogEvent
{
	private int myLoanId;
	private String myLoanName;
	
	/**
	 * Ctor
	 * 
	 * @param loanId Loan id database identifier
	 * @param loanName Loan name set in the SetLoanNameDialog Dialog
	 */
	public SetLoanNameDialogEvent(int loanId, String loanName)
	{
		this.myLoanId = loanId;
		this.myLoanName = loanName;
	}
	
	
	/**
	 * Get the loan name set in the SetLoanNameDialog Dialog
	 * 
	 * @return Loan name
	 */
	public String getLoanName()
	{
		return this.myLoanName;
	}
	
	
	/**
	 * Get the database identifier for this loan
	 * 
	 * @return Loan id
	 */
	public int getLoanId()
	{
		return this.myLoanId;
	}

}
