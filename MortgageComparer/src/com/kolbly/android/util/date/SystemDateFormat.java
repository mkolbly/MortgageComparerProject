package com.kolbly.android.util.date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;
import com.squareup.otto.Bus;

/**
 * Android system settings utility class. Optionally, can be registered to
 * listen for settings changes and will reset its values so that the most recent
 * settings are used. The default is to read and store each setting value only
 * on its first use.
 */
public final class SystemDateFormat
{
	private static final String TAG = ClassUtil.getShortName(SystemDateFormat.class);
	
	private static final DateFormat DEFAULT_DATE_FORMAT = DateFormat.MMDDYYYY;
		
	/** Sync object for system date format funcs */
	private final Object myDateLock = new Object();

	/**
	 * Android Settings date format, "mm/dd/yyyy" or "dd/mm/yyyy" or "yyyy/mm/dd"
	 */
	private DateFormat mySystemDateFormat = null;

	/** Observer to listen for system date format changes */
	private ContentObserver myDateFormatChangedObserver = null;

	/** Application context */
	private final Context myContext;

	private final ContentResolver myContentResolver;
	
	/** Override DateFormat that will be used if set */
	private static DateFormat myOverrideSystemDateFormat = null;
	
	/**
	 * Set the override value for DateFormat returned from this class
	 * <p>
	 * This is useful in testing situations.  Ideally, this shouldn't be 
	 * necessary as we should be able to use a Shadow in Robolectric's framework,
	 * but the java.lang.RuntimeException: really, more than 64 qualifiers?!?
	 * exception kept happening when trying to shadow this class - see
	 * ShadowSystemDateFormat.java
	 * 
	 * @param format Format to use
	 */
	public static void overrideSystemDateFormat(DateFormat format)
	{
		myOverrideSystemDateFormat = format;
	}
	
	/**
	 * Our SystemDateFormat singleton instance creator / holder 
	 */
	private static class SystemDateFormatHolder
	{
		private static final SystemDateFormat INSTANCE = SystemDateFormat.create(G.getAppContext());
	}
	
	/**
	 * Get this singleton instance
	 * 
	 * @return Singleton instance
	 */
	public static SystemDateFormat instance()
	{
		return SystemDateFormatHolder.INSTANCE; 
	}
	
	/**
	 * Create our object with the given context
	 * <p>
	 * 
	 * @param context Application context 
	 * @return a new instance of this object
	 */
	public static SystemDateFormat create(Context context)
	{
		return new SystemDateFormat(context);
	}
	

	/**
	 * Private ctor for singleton
	 * 
	 * @param resolver Application context
	 */
	private SystemDateFormat(Context context)
	{
		this.myContext = context;
		this.myContentResolver = context.getContentResolver();
	}

	
	/**
	 * Retrieve the current date format user preference that they've set in
	 * Android device OS settings.
	 * <p>
	 * Note: This sends back the current setting without querying the OS again 
	 * for the setting
	 * 
	 * @return "mm/dd/yyyy" or "dd/mm/yyyy" or "yyyy/mm/dd"
	 */
	public DateFormat getFormat()
	{
		return this.getFormat(false);
	}

	/**
	 * Retrieve the current date format user preference that they've set in
	 * Android device OS settings
	 * 
	 * @param refresh - true if you want to force a refresh of the current value
	 *           from the OS
	 * @return "mm/dd/yyyy" or "dd/mm/yyyy" or "yyyy/mm/dd"
	 */
	public DateFormat getFormat(boolean refresh)
	{
		synchronized (myDateLock)
		{
			// Use overridden value if set
			if (SystemDateFormat.myOverrideSystemDateFormat != null)
			{
				return SystemDateFormat.myOverrideSystemDateFormat;
			}
			
			if ((mySystemDateFormat == null) || (refresh))
			{
				String dfSetting = Settings.System.getString(this.myContentResolver, Settings.System.DATE_FORMAT);
				
				mySystemDateFormat = DateFormat.lookup(dfSetting);
				
				// If we failed (typically because OS is returning non-documented
				// results), then try alternative lookup
				if (mySystemDateFormat == null)
				{
					char[] order = android.text.format.DateFormat.getDateFormatOrder(this.myContext);
					mySystemDateFormat = DateFormat.lookup(order);
				}
				
				if (mySystemDateFormat == null)
				{
					Log.e(TAG, "Failed to get DateFormat from System date format : " + dfSetting);
					mySystemDateFormat = DEFAULT_DATE_FORMAT;
				}
			}

			return mySystemDateFormat;
		}
	}
	
	/**
	 * Removes content observers listening for system date format changes
	 */
	public void unregisterForDateFormatChanges()
	{
		synchronized (myDateLock)
		{
			if (myDateFormatChangedObserver != null)
			{
				this.myContentResolver.unregisterContentObserver(myDateFormatChangedObserver);
				myDateFormatChangedObserver = null;
			}
		}
	}

	/**
	 * Register for date format changes
	 */
	public void registerForDateFormatChanges()
	{
		this.unregisterForDateFormatChanges();

		synchronized (myDateLock)
		{
			// Content observer for date format changes
			myDateFormatChangedObserver = new ContentObserver(null)
			{
				@Override
				public void onChange(boolean selfChange)
				{
					mySystemDateFormat = null;
					notifyListeners();
				}
			};

			Uri uri = Settings.System.getUriFor(Settings.System.DATE_FORMAT);
			this.myContentResolver.registerContentObserver(uri, false, myDateFormatChangedObserver);
		}
	}

	/**
	 * Notify any listeners that the date and/or time formatting has been changed
	 */
	private void notifyListeners()
	{
		DateFormat df = this.getFormat(true);
		
		Bus.getBus().post(new SystemDateFormatEvent(df));
	}

	/**
	 * Clears out the current date format cache which forces the methods to
	 * query the OS for the proper values next time thru 
	 */
	public void clear()
	{
		synchronized (myDateLock)
		{
			mySystemDateFormat = null;
			SystemDateFormat.overrideSystemDateFormat(null); 
		}
	}
	
}
