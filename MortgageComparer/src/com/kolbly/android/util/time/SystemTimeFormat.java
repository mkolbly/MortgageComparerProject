package com.kolbly.android.util.time;

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
 * 
 * @author mkolbly
 */
public final class SystemTimeFormat
{
	private static final String TAG = ClassUtil.getShortName(SystemTimeFormat.class);
	
	private static final TimeFormat DEFAULT_TIME_FORMAT = TimeFormat.TIME_12;

	/** 
	 * Android Settings time format, either 12 or 24 
	 */
	private TimeFormat mySystemTimeFormat = null;
	
	/** Sync object for system format funcs */
	private final Object myTimeLock = new Object();

	/** Observer to listen for system 24-hour format changes (TIME_12_24) */
	private ContentObserver myTimeFormatChangedObserver = null;
	
	private final ContentResolver myContentResolver;
	
	/**
	 * Our singleton instance creator / holder 
	 */
	private static class SystemTimeFormatHolder
	{
		private static final SystemTimeFormat INSTANCE = SystemTimeFormat.create(G.getAppContext());
	}
	
	/**
	 * Get this singleton instance
	 * 
	 * @return Singleton instance
	 */
	public static SystemTimeFormat instance()
	{
		return SystemTimeFormatHolder.INSTANCE; 
	}	
	
	/**
	 * Convenience static Ctor 
	 * 
	 * @param context Application context
	 * @return a new instance of this object
	 */
	public static SystemTimeFormat create(Context context)
	{
		return new SystemTimeFormat(context);
	}
	
	/**
	 * Ctor
	 * 
	 * @param context Application context
	 */
	private SystemTimeFormat(Context context)
	{
		this.myContentResolver = context.getContentResolver();
	}

	/**
	 * Retrieve the current 24-hour format user preference that they've set in
	 * Android device OS settings
	 * 
	 * @return 12 or 24
	 */
	public TimeFormat getSystemTimeFormat()
	{
		return this.getSystemTimeFormat(false);
	}

	/**
	 * Retrieve the current 24-hour format user preference that they've set in
	 * Android device OS settings
	 * 
	 * @param refresh - true if you want to force a refresh of the current value
	 *           from the OS
	 * @return 12 or 24
	 */
	public TimeFormat getSystemTimeFormat(boolean refresh)
	{
		synchronized (myTimeLock)
		{
			if ((mySystemTimeFormat == null) || (refresh))
			{
				int tf = Settings.System.getInt(this.myContentResolver, Settings.System.TIME_12_24, DEFAULT_TIME_FORMAT.getFormat());
				
				mySystemTimeFormat = TimeFormat.lookup(tf);
				
				if (mySystemTimeFormat == null)
				{
					Log.e(TAG, "Failed to get TimeFormat from system setting : " + tf);
					
					mySystemTimeFormat = DEFAULT_TIME_FORMAT;
				}
			}

			return mySystemTimeFormat;
		}
	}

	/**
	 * Removes content observers listening for system time format changes
	 */
	public void unregisterForTimeFormatChanges()
	{
		synchronized (myTimeLock)
		{
			if (myTimeFormatChangedObserver != null)
			{
				this.myContentResolver.unregisterContentObserver(myTimeFormatChangedObserver);
				myTimeFormatChangedObserver = null;
			}
		}
	}

	
	/**
	 * Register for time format changes by the user
	 */
	public void registerForTimeFormatChanges()
	{
		this.unregisterForTimeFormatChanges();

		synchronized (myTimeLock)
		{
			// Content observer for time format changes
			myTimeFormatChangedObserver = new ContentObserver(null)
			{
				@Override
				public void onChange(boolean selfChange)
				{
					mySystemTimeFormat = null;
					notifyListeners();
				}
			};

			Uri uri = Settings.System.getUriFor(Settings.System.TIME_12_24);
			this.myContentResolver.registerContentObserver(uri, false, myTimeFormatChangedObserver);
		}
	}


	/**
	 * Notify any listeners that the date and/or time formatting has been changed
	 */
	private void notifyListeners()
	{
		TimeFormat tf = this.getSystemTimeFormat(true);
		
		Bus.postEvent(new SystemTimeFormatEvent(tf));
	}

	/**
	 * Clear up cached settings so next time will query the OS for fresh data 
	 */
	public void clear()
	{
		synchronized (myTimeLock)
		{
			mySystemTimeFormat = null;
		}
	}
}
