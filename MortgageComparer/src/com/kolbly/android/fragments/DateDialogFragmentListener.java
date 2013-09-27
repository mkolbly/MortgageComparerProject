package com.kolbly.android.fragments;

/**
 * DateDialogFragment listener for when date has been set
 * 
 * @author mkolbly
 */
public interface DateDialogFragmentListener
{
	/**
	 * Callback for when date has been set by DateDialogFragment
	 * 
	 * @param year Year
	 * @param monthOfYear Month
	 * @param dayOfMonth Day of Month
	 */
	void onDateSet(int year, int monthOfYear, int dayOfMonth);
}
