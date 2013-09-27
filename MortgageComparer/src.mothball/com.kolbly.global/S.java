//package com.kolbly.global;
//
//import android.content.Context;
//
//import com.kolbly.android.util.date.SystemDateFormat;
//import com.kolbly.android.util.time.SystemTimeFormat;
//import com.kolbly.mortgagecomparer.db.DataManager;
//
//
///**
// * Singleton container
// * <p>
// * In order to make testing easier, we conveniently put all our classes here
// * that we would normally implement as singletons.  However we won't implement
// * any singletons just to make unit testing sane!
// * <p>
// * This could also be implemented in the Application class, but we'll use
// * this just to keep the Application class tidy
// * <p>
// * Important note - this must be initialized early in the Application onCreate()
// * routine.  We're requiring an init() instead of using the Application's stored
// * context for simpler unit testing
// * 
// * Note: Couldn't quite fit this in without creating just one cyclic redundancy
// * 
// * TODO: Possibly use this in the future, so we'll keep this around just as a
// * reminder
// * 
// * @author mkolbly
// */
//public final class S
//{	
//	/** Our application context */
//	private static Context myContext = null;
//	
//	
//	/**
//	 * Initialize this container full of singletons
//	 * <p>
//	 * This needs to be called early on in Application.onCreate()
//	 * 
//	 * @see android.app.Application#onCreate()
//	 * 
//	 * @param app Application context
//	 */
//	public static void init(Context context)
//	{
//		S.myContext = context;
//	}
//	
//	/**
//	 * Private ctor for utility class
//	 */
//	private S() 
//	{
//	}
//		
//	/**
//	 * Get our application context
//	 * 
//	 * @return Application context
//	 */
//	private static Context getContext()
//	{
//		return myContext;
//	}
//	
//	/**
//	 * Holder for SystemDateFormat singleton instance
//	 */
//	private static class SystemDateFormatHolder
//	{
//		private static final SystemDateFormat INSTANCE = SystemDateFormat.create(getContext()); 
//	}
//	
//	/**
//	 * Android system settings utility class.
//	 * 
//	 * @return DateTimeSystemSettings singleton
//	 */
//	public static SystemDateFormat getSystemDateFormat()
//	{
//		return SystemDateFormatHolder.INSTANCE;
//	}
//
//	/**
//	 * Holder for SystemTimeFormat singleton instance
//	 */
//	private static class SystemTimeFormatHolder
//	{
//		private static final SystemTimeFormat INSTANCE = SystemTimeFormat.create(getContext());
//	}
//	
//	/**
//	 * Android system settings utility class.
//	 * 
//	 * @return DateTimeSystemSettings singleton
//	 */
//	public static SystemTimeFormat getSystemTimeFormat()
//	{
//		return SystemTimeFormatHolder.INSTANCE;
//	}
//	
//	/**
//	 * Holder for DataManager singleton instance
//	 */
//	private static class DataManagerHolder
//	{
//		private static final DataManager INSTANCE = DataManager.create(getContext());
//	}
//	
//	/**
//	 * Our Data Manager singleton instance
//	 * 
//	 * @return Our DataManager singleton
//	 */
//	public static DataManager getDataManager()
//	{
//		return DataManagerHolder.INSTANCE;		
//	}
//		
//}
