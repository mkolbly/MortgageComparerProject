package com.kolbly.mortgagecomparer.activities.loanactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kolbly.mortgagecomparer.db.Loan;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class LoanActivitySectionsPagerAdapter extends FragmentPagerAdapter
{
	/** Activity this pager adapter is managing */
	private final LoanActivity myLoanActivity;

	public LoanActivitySectionsPagerAdapter(LoanActivity loanActivity, FragmentManager fm)
	{
		super(fm);
		this.myLoanActivity = loanActivity;
	}

	/**
	 * Called to instantiate the fragment for the given page.
	 * 
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 * 
	 * @param position Tab position of loan to get the Fragment for
	 */
	@Override
	public Fragment getItem(int position)
	{
		Loan loan = this.myLoanActivity.getAllLoans().get(position);
				
		Fragment fragment = new LoanActivityFragment();

		Bundle args = new Bundle();
		args.putInt(Loan.IDENTITY, loan.getId());
		fragment.setArguments(args);
		
		return fragment;
	}

	/**
	 * Get how many tabs to display, i.e. how many loans we have
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount()
	{
		int numLoans = this.myLoanActivity.getAllLoans().size();
		
		return numLoans;
	}

	/**
	 * Get the name of the tab, i.e. the name of the loan being displayed
	 * 
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle(int position)
	{
		String name = this.myLoanActivity.getAllLoans().get(position).getName();
		
		return name;
	}
}