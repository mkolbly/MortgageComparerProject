package com.kolbly.mortgagecomparer.activities.loanactivity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kolbly.mortgagecomparer.R;
import com.kolbly.mortgagecomparer.activities.about.AboutDialog;
import com.kolbly.mortgagecomparer.db.DataManager;
import com.kolbly.mortgagecomparer.db.Loan;
import com.kolbly.mortgagecomparer.db.User;

/**
 * Loan activity
 * 
 * @author mkolbly
 */
public class LoanActivity extends ActionBarActivity implements ActionBar.TabListener
{
	/** Bundle key for loan id to initially show */
	private static final String KEY_STARTING_LOAN_ID = "KEY_STARTING_LOAN_ID";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
	 * keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private LoanActivitySectionsPagerAdapter mySectionsPagerAdapter; // NOSONAR

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager myViewPager;

	/** All loans in the database - 1 loan per fragment */
	private List<Loan> myLoans = null;

	/** User data common to all our loan fragments */
	private User myUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Load the user data from db
		this.myUser = DataManager.instance().getUser();

		setContentView(R.layout.loan_activity);

		// Set up the action bar.
		final ActionBar actionBar = this.getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for Loan
		mySectionsPagerAdapter = new LoanActivitySectionsPagerAdapter(this, getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		myViewPager = (ViewPager) findViewById(R.id.am_data);
		myViewPager.setAdapter(mySectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		myViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mySectionsPagerAdapter.getCount(); i++)
		{
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.

			Tab tab = actionBar.newTab();
			tab.setText(mySectionsPagerAdapter.getPageTitle(i));
			tab.setTabListener(this);

			actionBar.addTab(tab);
		}

		// If given, set the initial tab to the given loan
		Bundle bundle = getIntent().getExtras();
		if ((bundle != null) && (bundle.containsKey(KEY_STARTING_LOAN_ID)))
		{
			int loanId = bundle.getInt(KEY_STARTING_LOAN_ID);

			int index = getLoanIndex(loanId);

			if (index > -1)
			{
				Tab tab = actionBar.getTabAt(index);
				actionBar.selectTab(tab);
			}
		}

	}

	/**
	 * Get the singular user object we're using for this Activity and associated
	 * child fragments
	 * <p>
	 * Cached data is used if exists
	 * 
	 * @return singular User object from the database
	 */
	public User getUser()
	{
		return this.getUser(false);
	}

	/**
	 * Get the singular user object we're using for this Activity and associated
	 * child fragments
	 * 
	 * @param bForceRefresh true if a database query is forced
	 * @return singular User object from the database
	 */
	public User getUser(boolean bForceRefresh)
	{
		if ((this.myUser == null) || (bForceRefresh))
		{
			this.myUser = DataManager.instance().getUser();
		}

		return this.myUser;
	}

	/**
	 * Get all loans currently in the database
	 * <p>
	 * Cached data is used if exists
	 * 
	 * @return List of loans
	 */
	public List<Loan> getAllLoans()
	{
		return getAllLoans(false);
	}

	/**
	 * Get all loans currently in the database
	 * 
	 * @param bForceRefresh true if a database query is forced
	 * @return List of loans
	 */
	public List<Loan> getAllLoans(boolean bForceRefresh)
	{
		if ((this.myLoans == null) || (bForceRefresh))
		{
			DataManager dm = DataManager.instance();
			this.myLoans = dm.getAllLoan();
		}

		if (this.myLoans.size() < 1)
		{
			this.myLoans = this.createSomeLoanData();
		}

		return this.myLoans;
	}

	/**
	 * Get the loan index in our all loans data - this will also correspond to
	 * the which tab the loan will be showing on
	 * 
	 * @param loanId Loan id to find
	 * @return Index in our loan data where this particular loan is
	 */
	public int getLoanIndex(int loanId)
	{
		List<Loan> loans = this.getAllLoans();

		for (int i = 0; i < loans.size(); i++)
		{
			Loan l = loans.get(i);
			if (l.getId() == loanId)
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * Create some initial loans to put in the database if there aren't any there
	 * 
	 * @return List of loans
	 */
	public List<Loan> createSomeLoanData()
	{
		Loan l1 = new Loan(335773, 3.25, 12 * 30); // NOSONAR
		l1.setName("Wells Fargo");
		l1.setExtraPayment(500);
		
		Loan l2 = new Loan(150000, 4.5, 12 * 30); // NOSONAR
		Loan l3 = new Loan(200000, 4.5, 12 * 30); // NOSONAR		

		DataManager dm = DataManager.instance();

		dm.save(l1);
		dm.save(l2);
		dm.save(l3);

		return dm.getAllLoan();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loan_activity_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Switch to the new loan
	 * 
	 * @see android.support.v7.app.ActionBar.TabListener#onTabSelected(android.support.v7.app.ActionBar.Tab,
	 *      android.support.v4.app.FragmentTransaction)
	 */
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		myViewPager.setCurrentItem(tab.getPosition());
	}

	/**
	 * @see android.support.v7.app.ActionBar.TabListener#onTabUnselected(android.support.v7.app.ActionBar.Tab, android.support.v4.app.FragmentTransaction)
	 */
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		// Purposefully empty
	}

	/**
	 * @see android.support.v7.app.ActionBar.TabListener#onTabReselected(android.support.v7.app.ActionBar.Tab, android.support.v4.app.FragmentTransaction)
	 */
	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		// Purposefully empty
	}

	/**
	 * Delete this loan
	 */
	private void deleteLoan()
	{
		// Must have at least one loan on hand
		if (this.myLoans.size() < 2)
		{
			String warning = getResources().getString(R.string.Cant_delete_last_loan);
			Toast t = Toast.makeText(this, warning, Toast.LENGTH_LONG);
			t.show();
			return;
		}

		// Remove the loan from the database
		ActionBar ab = this.getSupportActionBar();
		int index = ab.getSelectedNavigationIndex();
		Loan loan = this.myLoans.get(index);
		DataManager.instance().delete(loan);

		restart();
	}

	/**
	 * Restart this activity
	 */
	public void restart()
	{
		this.restart(-1);
	}

	/**
	 * Restart this activity and specify which loan to display first
	 * 
	 * @param loanId Loan ID of loan to display
	 */
	public void restart(int loanId)
	{
		Intent intent = getIntent();

		// If loanId is given, stuff that info in the intent 
		if (loanId > -1)
		{
			Bundle bundle = new Bundle();
			bundle.putInt(KEY_STARTING_LOAN_ID, loanId);
			intent.putExtras(bundle);
		}

		finish();
		startActivity(intent);
	}

	/**
	 * Create a new loan using the currently visible loan's values
	 */
	private void createLoan()
	{
		ActionBar ab = this.getSupportActionBar();
		int index = ab.getSelectedNavigationIndex();
		Loan currentLoan = this.myLoans.get(index);

		// "Copy of"
		String copyOf = getResources().getString(R.string.Copy_of);
		String newName = copyOf + " " + currentLoan.getName();

		Loan newLoan = new Loan(currentLoan);
		newLoan.setName(newName);

		// Store in db
		DataManager.instance().save(newLoan);

		// Restart this activity and set this current loan to this new loan
		int loanId = newLoan.getId();
		this.restart(loanId);
	}

	/**
	 * Respond to ActionBar menu items selected
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			// Action button - Delete this loan
			case R.id.action_delete:
				this.deleteLoan();
				return true;

			// Action button - Create new loan
			case R.id.action_new:
				this.createLoan();
				return true;

			// Action button - About this app
			case R.id.action_about:
				AboutDialog about = new AboutDialog(this);
				about.show();
				return true;

			default:
				// Do nothing
		}

		return super.onOptionsItemSelected(item);
	}

}
