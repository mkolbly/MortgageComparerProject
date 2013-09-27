//package com.kolbly.mortgagecomparer.activities.amortizationactivity.mothball;
//
//import java.util.List;
//
//import android.app.Activity;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//import com.kolbly.mortgagecomparer.R;
//import com.kolbly.mortgagecomparer.db.AmRow;
//import com.kolbly.mortgagecomparer.db.Amortization;
//import com.kolbly.mortgagecomparer.db.DataManager;
//import com.kolbly.mortgagecomparer.db.Loan;
//import com.kolbly.mortgagecomparer.db.User;
//
///**
// * Amortization activity that displays amortization table data using a
// * TableLayout object
// * <p>
// * Note: Though this is the easiest to format (being a Table which automatically
// * keeps the columns aligned), the TableLayout object cannot be used with an
// * adapter.
// * <p>
// * Mothballed for now - too slow
// * TODO: Speed this up for future consideration
// */
//public class AmortizationActivityTableLayout extends Activity
//{
//	/** Loan being amortized */
//	private Loan myLoan;
//	
//	/** User (Borrower) of loan */
//	private User myUser;
//	
//	/** Amortization loan data */
//	private Amortization myAmortization;
//	
//	/** Amortization row data */
//	private List<AmRow> myData;
//
//	/** Table to populate with amortization data */
//	private TableLayout myTable;
//	
//	/** true to add a header line to the table */
//	private static final boolean DISPLAY_HEADER = true;
//		
//
//	/* (non-Javadoc)
//	 * @see android.app.Activity#onCreate(android.os.Bundle)
//	 */
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.amortization_activity_tablelayout);
//
//		myLoan = this.getLoan();
//		myUser = DataManager.instance().getUser();
//		myAmortization = new Amortization(this.myLoan, this.myUser);
//		myData = myAmortization.getAmortizationData();
//
//		View view = this.findViewById(R.id.am_data_table);
//		
//		myTable = (TableLayout) view;
//
//		this.fillData();
//	}
//
//	/**
//	 * Create the amortization table's header row
//	 * 
//	 * @return Header row
//	 */
//	private TableRow createHeaderRow()
//	{
//		TextView tv;
//		Resources res = getResources();
//
//		LayoutInflater lf = LayoutInflater.from(this);
//
//		TableRow r = (TableRow) lf.inflate(R.layout.amortization_activity_tablelayout_header_row, null);
//
//		// Payment month
//		tv = (TextView) r.findViewById(R.id.am_month);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Month) + "\n");
//		
//		// Date
//		tv = (TextView) r.findViewById(R.id.am_date);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Date) + "\n");
//		
//		// Beginning Balance
//		tv = (TextView) r.findViewById(R.id.am_beginning_balance);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Beginning_Balance_Hdr));
//		
//		// Payment
//		tv = (TextView) r.findViewById(R.id.am_payment);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Payment) + "\n");
//		
//		// Principal
//		tv = (TextView) r.findViewById(R.id.am_principal);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Principal) + "\n");
//		
//		// Interest
//		tv = (TextView) r.findViewById(R.id.am_interest);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Interest) + "\n");
//		
//		// Cumulative Principal
//		tv = (TextView) r.findViewById(R.id.am_cumulative_principal);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Cumulative_Principal_Hdr));
//		
//		// Cumulative Interest
//		tv = (TextView) r.findViewById(R.id.cumulative_interest);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Cumulative_Interest_Hdr));
//		
//		// Ending Balance
//		tv = (TextView) r.findViewById(R.id.am_ending_balance);
//		setLayout(tv);
//		tv.setText(res.getString(R.string.Ending_Balance_Hdr));
//	
//		return r;
//	}
//
//
//	
//	/**
//	 * Create an amortization TableRow row out of AmRow data
//	 * 
//	 * @param data Amortization data row
//	 * @return Amortization table row
//	 */
//	private TableRow createTableRow(AmRow data)
//	{
//		TextView tv;
//		
//		LayoutInflater lf = LayoutInflater.from(this);
//				
//		TableRow r = (TableRow) lf.inflate(R.layout.amortization_activity_tablelayout_row, null);
//		
//		// Payment month
//		tv = (TextView) r.findViewById(R.id.am_month);
//		setLayout(tv);
//		tv.setText(data.getPaymentNumberString());
//		
//		// Date
//		tv = (TextView) r.findViewById(R.id.am_date);
//		setLayout(tv);
//		tv.setText(data.getPaymentDateString());
//		
//		// Beginning Balance
//		tv = (TextView) r.findViewById(R.id.am_beginning_balance);
//		setLayout(tv);
//		tv.setText(data.getBeginningBalanceString());
//		
//		// Payment
//		tv = (TextView) r.findViewById(R.id.am_payment);
//		setLayout(tv);
//		tv.setText(data.getPaymentString());
//		
//		// Principal
//		tv = (TextView) r.findViewById(R.id.am_principal);
//		setLayout(tv);
//		tv.setText(data.getPrincipalPaidString());
//		
//		// Interest
//		tv = (TextView) r.findViewById(R.id.am_interest);
//		setLayout(tv);
//		tv.setText(data.getInterestPaidString());
//		
//		// Cumulative Principal
//		tv = (TextView) r.findViewById(R.id.am_cumulative_principal);
//		setLayout(tv);
//		tv.setText(data.getCumulativePrincipalPaidString());
//		
//		// Cumulative Interest
//		tv = (TextView) r.findViewById(R.id.cumulative_interest);
//		setLayout(tv);
//		tv.setText(data.getCumulativeInterestPaidString());
//		
//		// Ending Balance
//		tv = (TextView) r.findViewById(R.id.am_ending_balance);
//		setLayout(tv);
//		tv.setText(data.getEndingBalanceString());
//		
//		return r;
//	}
//	
//	/**
//	 * Fill out our amortization table data 
//	 */
//	private void fillData()
//	{
//		// First row is table headers
//		if (DISPLAY_HEADER)
//		{
//			TableRow headerRow = this.createHeaderRow();
//			this.myTable.addView(headerRow);
//		}
//		
//		// Fill rest of table with amortization data
//		for (int i = 0; i < myData.size(); i++)
//		{
//			AmRow data = myData.get(i);
//			TableRow row = createTableRow(data);
//			
//			this.myTable.addView(row);
//		}
//
//	}
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
//	/**
//	 * Set some properties of each TextView object in our table
//	 * 
//	 * @param tv Textview box occupying each square of our table
//	 */
//	private void setLayout(TextView tv)
//	{		
////		// android:layout_width, android:layoutHeight, android:layout_weight
////		int w = ViewGroup.LayoutParams.WRAP_CONTENT;
////		int h = ViewGroup.LayoutParams.WRAP_CONTENT;
////		float initWeight = 0f;
////		TableRow.LayoutParams lp = new TableRow.LayoutParams(w, h, initWeight);
////		
////		// android:layout_marginBottom, android:layout_marginLeft, android:_marginRight
////		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
////		lp.setMargins(margin, 0, margin, margin);
////		
////		tv.setLayoutParams(lp);
////		
////		// android:background
////		int black = getResources().getColor(R.color.black);
////		tv.setBackgroundColor(black);
////		
////		// android:maxLines
////		tv.setMaxLines(1);
////		
////		// android:paddingLeft, android:paddingTop, android:paddingRight, android:paddingBottom
////		int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
////		tv.setPadding(padding, padding, padding, padding);
//	}
//	
//}
