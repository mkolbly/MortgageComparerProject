package com.kolbly.mortgagecomparer.activities.amortizationactivity;			

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kolbly.java.util.ClassUtil;
import com.kolbly.mortgagecomparer.R;
import com.kolbly.mortgagecomparer.db.AmRow;
import com.kolbly.mortgagecomparer.db.Amortization;

/**
 * Amortization data adaptor for use with a ListView
 */
public class AmortizationActivityListViewAdapter extends BaseAdapter
{
	private static final String TAG = ClassUtil.getShortName(AmortizationActivityListViewAdapter.class);
	
	/** Set to true to show a header row */
	private static final boolean SHOW_HEADER = true;
	
	/** Our Context */
	private Context myContext;

	/** Amortization data rows */
	private List<AmRow> myData;

	/** Inflator for rows */
	private LayoutInflater myInflater;	
	
	public AmortizationActivityListViewAdapter(Context context, Amortization amortization)
	{
		this.myContext = context;
		this.myData = amortization.getAmortizationData();
		this.myInflater = LayoutInflater.from(this.myContext);
	}

	/**
	 * Get a count of how many rows of data for this amortization table
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount()
	{
		if (SHOW_HEADER)
		{
			// Add 1 here for header row
			return this.myData.size() + 1;
		}
		else
		{
			return this.myData.size();
		}
	}

	/**
	 * Unused
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position)
	{
		return null;
	}

	/**
	 * Get the data to display for this particular position
	 * 
	 * @param position Row data to display
	 * @return Amortization data row
	 */
	public AmRow getData(int position)
	{
		return (SHOW_HEADER) ? myData.get(position - 1) : myData.get(position);
	}
	
	/**
	 * Unused
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	
	/**
	 * Holds views so they don't have to be looked up each and every time
	 * a row needs to be retrieved and drawn
	 */
	static class ViewHolder
	{
		private TextView month;								// NOSONAR 
		private TextView date; 								// NOSONAR
		private TextView beginningBalance; 				// NOSONAR
		private TextView payment; 							// NOSONAR
		private TextView principal; 						// NOSONAR
		private TextView interest;							// NOSONAR
		private TextView cumulativePrincipal; 			// NOSONAR
		private TextView cumulativeInterest; 			// NOSONAR
		private TextView endingBalance; 					// NOSONAR
		
		public ViewHolder(View convertView)
		{
			this.month = (TextView)convertView.findViewById(R.id.am_month);
			this.date = (TextView)convertView.findViewById(R.id.am_date);
			this.beginningBalance = (TextView)convertView.findViewById(R.id.am_beginning_balance);
			this.payment = (TextView)convertView.findViewById(R.id.am_payment);
			this.principal = (TextView)convertView.findViewById(R.id.am_principal);
			this.interest = (TextView)convertView.findViewById(R.id.am_interest);
			this.cumulativePrincipal = (TextView)convertView.findViewById(R.id.am_cumulative_principal);
			this.cumulativeInterest = (TextView)convertView.findViewById(R.id.cumulative_interest);
			this.endingBalance = (TextView)convertView.findViewById(R.id.am_ending_balance);
		}
	}
	

	/**
	 * Get the view to be displayed at the given position
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Log.d(TAG, "Getting position = " + position);	
		
		View view = convertView;
		ViewHolder viewHolder;
		
		if (view == null)
		{
			// Create a new view and stuff container View info into it's Tag holder
			
			// The first row will be a header row
			int layoutXml = (position == 0) ? R.layout.amortization_activity_listview_header_row : 
				R.layout.amortization_activity_listview_row;
			
			view = myInflater.inflate(layoutXml, null);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		}
		else
		{
			// Get our ViewHolder back for fast access to TextViews
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if ((position == 0) && (SHOW_HEADER))
		{
			// First row is a header, so set all values with header texts
			Resources res = myContext.getResources();
			
			viewHolder.month.setText("#\n\n");
			viewHolder.date.setText(res.getString(R.string.Date) + "\n");
			viewHolder.beginningBalance.setText(res.getString(R.string.Beginning_Balance_Hdr));
			viewHolder.payment.setText(res.getString(R.string.Payment) + "\n");
			viewHolder.principal.setText(res.getString(R.string.Principal) + "\n");
			viewHolder.interest.setText(res.getString(R.string.Interest) + "\n");
			viewHolder.cumulativePrincipal.setText(res.getString(R.string.Cumulative_Principal_Hdr));
			viewHolder.cumulativeInterest.setText(res.getString(R.string.Cumulative_Interest_Hdr));
			viewHolder.endingBalance.setText(res.getString(R.string.Ending_Balance_Hdr));
		}
		else
		{
			// Otherwise row is a data row - bind data at this position
			AmRow data = getData(position); 
			
			// Bind data efficiently with the holder without having to find each view
			viewHolder.month.setText(data.getPaymentNumberString());
			viewHolder.date.setText(data.getPaymentDateString());
			viewHolder.beginningBalance.setText(data.getBeginningBalanceString());
			viewHolder.payment.setText(data.getPaymentString());
			viewHolder.principal.setText(data.getPrincipalPaidString());
			viewHolder.interest.setText(data.getInterestPaidString());
			viewHolder.cumulativePrincipal.setText(data.getCumulativePrincipalPaidString());
			viewHolder.cumulativeInterest.setText(data.getCumulativeInterestPaidString());
			viewHolder.endingBalance.setText(data.getEndingBalanceString());
		}
		
		return view;
	}

}
