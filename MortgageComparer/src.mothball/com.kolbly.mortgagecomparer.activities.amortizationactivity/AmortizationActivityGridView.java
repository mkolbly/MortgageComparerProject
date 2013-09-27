//package com.kolbly.mortgagecomparer.activities.amortizationactivity;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.widget.GridView;
//
//import com.kolbly.mortgagecomparer.R;
//import com.kolbly.mortgagecomparer.db.Amortization;
//import com.kolbly.mortgagecomparer.db.DataManager;
//import com.kolbly.mortgagecomparer.db.Loan;
//import com.kolbly.mortgagecomparer.db.User;
//
///**
// * Show amortization data in a GridView
// * <p>
// * Note: There is apparently no good way to scroll a GridView horizontally,
// * so we'll abandon this and use the ListView with fixed-width columns instead
// */
//public class AmortizationActivityGridView extends Activity
//{
//
//	public static final int NUM_COLUMNS = 9;
//				
//	/** Loan being amortized */
//	private Loan myLoan;
//	
//	/** User (Borrower) of loan */
//	private User myUser;
//	
//	/** Amortization loan data */
//	private Amortization myAmortization;
//	
//	/** View to populate with amortization data */
//	GridView myAmortizationGridView;
//
//	
//	/* (non-Javadoc)
//	 * @see android.app.Activity#onCreate(android.os.Bundle)
//	 */
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.amortization_activity_gridview);
//		
//		myAmortizationGridView = (GridView) this.findViewById(R.id.amortization_gridview);
//		
//		this.myLoan = this.getLoan();
//		this.myUser = DataManager.instance().getUser();
//		this.myAmortization = new Amortization(this.myLoan, this.myUser);
//		
//		AmortizationActivityGridViewAdapter adapter = new AmortizationActivityGridViewAdapter(this, this.myAmortization);
//		
//		myAmortizationGridView.setAdapter(adapter);
//		
//	}
//
//	
//	/* (non-Javadoc)
//	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
//	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		return false;
//	}
//
//	/**
//	 * Get the Loan to be described by this Fragment as passed in by the View
//	 * arguments Bundle
//	 * 
//	 * @return Loan on this page
//	 * @throws Exception
//	 */
//	private Loan getLoan()
//	{
//		Loan loan = null;
//
//		Bundle bundle = getIntent().getExtras();
//		if (bundle == null)
//		{
//			throw new IllegalArgumentException("Failed to get loan id - no Bundle arguments");
//		}
//
//		if (!bundle.containsKey(Loan.KEY_LOAN_ID))
//		{
//			throw new IllegalArgumentException("Failed to get loan id - Bundle arguments doesn't contain key : "
//				+ Loan.KEY_LOAN_ID);
//		}
//
//		int id = bundle.getInt(Loan.KEY_LOAN_ID);
//
//		DataManager dm = DataManager.instance();
//		loan = dm.getLoan(id);
//
//		return loan;
//	}
//
//}