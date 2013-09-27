//package com.kolbly.mortgagecomparer.activities.amortizationactivity.mothball;
//
//import java.util.List;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.kolbly.java.util.ClassUtil;
//import com.kolbly.mortgagecomparer.db.AmRow;
//import com.kolbly.mortgagecomparer.db.Amortization;
//
///**
// * A custom adapter for displaying amortization data in a GridView
// */
//public class AmortizationActivityGridViewAdapter extends BaseAdapter
//{
//	private static final String TAG = ClassUtil.getShortName(AmortizationActivityGridViewAdapter.class);
//	
//	// Context
//	private Context myContext;
//
//	// Amortization data
//	private Amortization myAmortization;
//
//	// Amortization data rows
//	private List<AmRow> myData;
//
//	
//	public AmortizationActivityGridViewAdapter(Context context, Amortization amortization)
//	{
//		this.myContext = context;
//		this.myAmortization = amortization;
//		this.myData = this.myAmortization.getAmortizationData();		
//	}
//
//	@Override
//	public int getCount()
//	{
//		// Add 1 here for header row
//		// todo add header
//		
//		return this.myData.size() * AmortizationActivityGridView.NUM_COLUMNS;
//	}
//
//	@Override
//	public Object getItem(int position)
//	{
//		return null;
//	}
//
//	@Override
//	public long getItemId(int position)
//	{
//		return 0;
//	}
//	
//
//
//	/* (non-Javadoc)
//	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
//	 */
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent)
//	{
//		int column = (position % AmortizationActivityGridView.NUM_COLUMNS);
//		int row = (position / AmortizationActivityGridView.NUM_COLUMNS);
//		
//		Log.d(TAG, String.format("Getting position %d, row %d, column %d", position, row, column));
//		
//		if (convertView == null)
//		{	
//			convertView = new TextView(this.myContext);
//		}
//
//	
//		AmRow data = myData.get(row);
//		TextView tv = (TextView)convertView;
//		
//		switch (column)
//		{
//			case 0: tv.setText(data.getPaymentNumberString()); break;
//			case 1: tv.setText(data.getPaymentDateString()); break;
//			case 2: tv.setText(data.getBeginningBalanceString()); break;
//			case 3: tv.setText(data.getPaymentString()); break;
//			case 4: tv.setText(data.getPrincipalPaidString()); break;
//			case 5: tv.setText(data.getInterestPaidString()); break;
//			case 6: tv.setText(data.getCumulativePrincipalPaidString()); break;
//			case 7: tv.setText(data.getCumulativeInterestPaidString()); break;
//			case 8: tv.setText(data.getEndingBalanceString()); break;
//		}
//		
//		return convertView;
//	}
//
//}
