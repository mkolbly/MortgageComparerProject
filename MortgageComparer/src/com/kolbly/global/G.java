package com.kolbly.global;

import java.util.Locale;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Globals!
 * <p>
 * Sometimes convenient globals are a good thing - we'll keep ours here
 * <p>
 * Note: Important to call G.init() early on in the Application instantiation
 * 
 * @author mkolbly
 */
public final class G
{
	/** Always convenient to have an application context handy */
	private static Context myContext = null;
	
	/** Our current locale */
	private static Locale myLocale = null;
	

	
	/**
	 * Private ctor for our utility class
	 */
	private G() {}
		
	/**
	 * Initialize our Globals with an application or unit testing context 
	 * <p>
	 * Call this from the Application class's onCreate method
	 * 
	 * @see android.app.Application#onCreate()
	 * 
	 * @param context Application context
	 */
	public static void init(Context context)
	{
		myContext = context;
	}
	
	
	/**
	 * This is readily available throughout any android app, but in order to 
	 * future-proof our app, we'll always grab our locale here.  This enables us
	 * to potentially listen for any locale changes that might happen and 
	 * respond accordingly without having to restart our app.  This may seem 
	 * like overkill, but having a single place to get this is simple and saves
	 * a bunch of potential aggravation in the future.
	 * 
	 * @return Current locale
	 */
	public static Locale getLocale()
	{
		if (G.myLocale == null)
		{
			G.myLocale = getAppContext().getResources().getConfiguration().locale;
		}
		
		return G.myLocale;
	}
	
	/**
	 * Get our application context
	 * 
	 * @return Application context
	 */
	public static Context getAppContext()
	{
		return myContext;
	}
	
	
	/**
	 * Holder for debug build value
	 */
	private static final class DebugBuildHolder
	{
		private static final boolean VALUE = 
			((myContext.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
	}
	
	/**
	 * Determine if this is a debug build from AndroidManfiest.xml
	 * 
	 * @return true if this is a debug build
	 */
	public static boolean isDebugBuild()
	{
		return DebugBuildHolder.VALUE;
	}
}
