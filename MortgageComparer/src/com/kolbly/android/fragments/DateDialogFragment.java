package com.kolbly.android.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * Dialog Fragment for selecting a date via a DatePickerDialog.
 * <p>
 * This class requires the user to manually set the callback for when the date
 * is set and thus makes it suitable for calling from Fragments as well as
 * Activities
 * <p>
 * Note: Every fragment must have an empty constructor, so it can be
 * instantiated when restoring its activity's state. It is strongly recommended
 * that subclasses do not have other constructors with parameters, since these
 * constructors will not be called when the fragment is re-instantiated;
 * instead, arguments can be supplied by the caller with setARguments(Bundle)
 * and later retrieved by the Fragment with getArguments()
 * <p>
 * 
 * @author mkolbly
 */
public class DateDialogFragment extends DialogFragment
{	
	private Context myContext;
	private Calendar myDate;

	/**
	 * Our listener to notify once the date has been set
	 */
	private DateDialogFragmentListener myListener;
	
	/**
	 * The callback interface given to us by the default Android DatePickerDialog.
	 * We simply call our own listener when this one gets called.
	 * <p>
	 * We're doing it this way because a Fragment does not have it's own Context
	 * and we want to be able to create the dialog -and- set the callback listener
	 * from another fragment.
	 */
	private DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener()
	{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			myListener.onDateSet(year, monthOfYear, dayOfMonth);
		}
	};
	
	
	/**
	 * Create a DateDialogFragment object
	 * 
	 * @param context Context to use
	 * @param date Initial date to set the dialog to
	 * @return DateDialogFragment
	 */
	public static DateDialogFragment create(Context context, Calendar date)
	{
		DateDialogFragment dialog = new DateDialogFragment();
		
		dialog.setContext(context);
		dialog.setDate(date);
		
		return dialog;
	}


	/**
	 * Creates a custom dialog container which simply uses the DatePickerDialog
	 * initialized with our date
	 * 
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		int year = this.myDate.get(Calendar.YEAR);
		int month = this.myDate.get(Calendar.MONTH);
		int day = this.myDate.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(this.myContext, this.myDateSetListener, 
			year, month, day);
	}

	/**
	 * Set the context used for creating this dialog
	 * 
	 * @param context Context for dialog
	 */
	private void setContext(Context context)
	{
		this.myContext = context;
	}
	
	/**
	 * Set the initial date on this dialog
	 * 
	 * @param date Date to set on dialog
	 */
	private void setDate(Calendar date)
	{
		this.myDate = date;
	}
	
	/**
	 * Set the callback listener for when the user selects a date
	 * 
	 * @param listener Listener called when date is chosen
	 */
	public void setListener(DateDialogFragmentListener listener)
	{
		this.myListener = listener;
	}


}
