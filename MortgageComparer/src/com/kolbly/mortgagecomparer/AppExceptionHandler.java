package com.kolbly.mortgagecomparer;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;

import com.kolbly.android.util.ExceptionWriter;
import com.kolbly.java.util.ClassUtil;

/**
 * Our Global uncaught exception handler
 * 
 * @author mkolbly
 */
public final class AppExceptionHandler implements UncaughtExceptionHandler
{
	private static final String TAG = ClassUtil.getShortName(AppExceptionHandler.class);
	
	/** Our singleton instance */
	private static AppExceptionHandler myInstance = new AppExceptionHandler();
	
	/** System default uncaught exception handler */
	private UncaughtExceptionHandler myDefaultSystemUEH;
	
	/** Application context */
	private Context myContext;
		
	/**
	 * Private ctor for utility class
	 */
	private AppExceptionHandler()
	{
	}
	
	/**
	 * Initialize our exception handling
	 * <p>
	 * We save the existing system default exception handler for later use by our
	 * custom one we're setting to take it's place.
	 * 
	 * @param context Application context
	 */
	private void initHandler(Context context)
	{
		this.myContext = context;
		this.myDefaultSystemUEH = Thread.getDefaultUncaughtExceptionHandler();
		
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
		

	/**
	 * The thread is being terminated by an uncaught exception. 
	 * <p>
	 * Customized application exception handling done here.  Note, the
	 * system's default uncaught exception handler also gets called so this
	 * remains compatable with ACRA exception reporting.  
	 * <p>
	 * Note : If you exit from here, i.e. System.exit(2), then the ACRA error
	 * reporting does not complete
	 * 
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 * 
	 * @param thread  the thread that has an uncaught exception 
	 * @param ex  the exception that was thrown  
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		Log.e(TAG, "Application uncaught exception in thread : " + thread.getName(), ex);
		
		// Do additional customized exception handling here
		
		// Write out our exception to the SD Card exception log file
		ExceptionWriter sdWriter = new ExceptionWriter();
		sdWriter.write("Application exception", ex);
		
		// Write out our exception to an internal exception log file
		String dirPath = this.myContext.getFilesDir().getAbsolutePath();
		String fileName = ExceptionWriter.DEFAULT_EXCEPTION_FILE;
		ExceptionWriter internalWriter = new ExceptionWriter(dirPath, fileName);
		internalWriter.write("Application exception",  ex);
		
		
		// It's important to re-throw a critical exception to the os
		myDefaultSystemUEH.uncaughtException(thread,  ex);
			
	}
	
	/**
	 * Initialize our exception handling - this should be called in
	 * Application.onCreate()
	 * 
	 * @param context Application context
	 */
	public static void init(Context context)
	{
		AppExceptionHandler.myInstance.initHandler(context);
	}
}

