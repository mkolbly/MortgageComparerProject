package com.kolbly.android.graphics;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Input filter by integer range - for use with EditText or EditTextEx
 * 
 * @author mkolbly
 */
public class RangeFilter implements InputFilter
{
	/** Minimum accepted integer */
	private int myMin;
	
	/** Maximum accepted integer */
	private int myMax;

	/**
	 * Ctor
	 * 
	 * @param min Minimum accepted integer
	 * @param max Maximum accepted integer
	 */
	public RangeFilter(int min, int max)
	{
		this.myMin = min;
		this.myMax = max;
	}

	/* (non-Javadoc)
	 * @see android.text.InputFilter#filter(java.lang.CharSequence, int, int, android.text.Spanned, int, int)
	 */
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
	{
		try
		{
			int input = Integer.parseInt(dest.toString() + source.toString());

			if ((input >= this.myMin) && (input <= this.myMax))
			{
				return null;
			}
		}
		catch (NumberFormatException nfe)
		{
			// Do nothing
		}

		return "";
	}

}
