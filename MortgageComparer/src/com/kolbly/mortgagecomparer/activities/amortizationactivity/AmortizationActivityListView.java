package com.kolbly.mortgagecomparer.activities.amortizationactivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.kolbly.mortgagecomparer.R;
import com.kolbly.mortgagecomparer.db.Amortization;
import com.kolbly.mortgagecomparer.db.DataManager;
import com.kolbly.mortgagecomparer.db.Loan;
import com.kolbly.mortgagecomparer.db.User;

/**
 * Display Amortization data in this ListView
 * 
 * TODO: Display some kind of loading dialog or spinner - or speed up the
 * 	loading process
 */
public class AmortizationActivityListView extends Activity
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amortization_activity_listview);
		
		// Loan being amortized
		Loan loan = this.getLoan();
		
		// User (Borrower) of loan
		User user = DataManager.instance().getUser();
		
		// Amortization loan data
		Amortization amortization = new Amortization(loan, user);
		
		// Bind our ListView with amortization data using our custom adapter
		ListView amortizationListView = (ListView) this.findViewById(R.id.amortization_listview);
		AmortizationActivityListViewAdapter adapter = new AmortizationActivityListViewAdapter(this, amortization);
		amortizationListView.setAdapter(adapter);
	}

	/**
	 * Get the Loan to be described by this Fragment as passed in by the View
	 * arguments Bundle
	 * 
	 * @return Loan on this page
	 * @throws Exception
	 */
	private Loan getLoan()
	{
		Loan loan = null;

		Bundle bundle = getIntent().getExtras();
		if (bundle == null)
		{
			throw new IllegalArgumentException("Failed to get loan id - no Bundle arguments");
		}

		if (!bundle.containsKey(Loan.KEY_LOAN_ID))
		{
			throw new IllegalArgumentException("Failed to get loan id - Bundle arguments doesn't contain key : "
				+ Loan.KEY_LOAN_ID);
		}

		int id = bundle.getInt(Loan.KEY_LOAN_ID);

		DataManager dm = DataManager.instance();
		loan = dm.getLoan(id);

		return loan;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return false;
	}
}
