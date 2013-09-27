package com.kolbly.mortgagecomparer.splashscreenactivity;		

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.kolbly.java.util.ClassUtil;
import com.kolbly.mortgagecomparer.R;
import com.kolbly.mortgagecomparer.activities.loanactivity.LoanActivity;

/**
 * This application's splash screen
 * <p>
 * Completely unnecessary for this app, but included as this is a reference app.
 * <p>
 * This shows how to do some background processing while showing our splash
 * screen, and/or show our splash screen for a minimal amount of time in 
 * conjunction with the background processing.
 * 
 * TODO: Make this do some cool animation or at the very least display a cool
 * logo
 * 
 * @author mkolbly
 */
public class MySplashScreenActivity extends Activity
{
	private static final String TAG = ClassUtil.getShortName(MySplashScreenActivity.class);

	/** Minimum time this splash screen will appear (milliseconds) */
	private static final int MIN_SPLASH_TIME = 100;

	/** After this splash screen, which activity should be loaded */
	private final Class<LoanActivity> myStartupActivityClass = LoanActivity.class;

	private String urlData;

	/** Define this if we're going to do a background task while showing this splash screen */
	private AsyncTask<Void, Void, Long> myBackgroundTask = null;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "Starting up splash screen");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen_activity);
		
		
		// This will do the background AsyncTask, then goto the startup activity
		if (myBackgroundTask != null)
		{
			myBackgroundTask.execute();
		}
		else
		{
			// This will simply goto the startup activity after showing this splash screen
			this.gotoStartupActivity(MIN_SPLASH_TIME);
		}
		
	}

	
	/**
	 * Goto our startup activity after the given delay
	 * 
	 * @param delay How long to wait before going to the startup activity
	 */
	private void gotoStartupActivity(final long delay)
	{
		final long startupDelay = (delay > 0) ? delay : 0;

		Log.d(TAG, String.format("Starting first activity after a delay of %d milliseconds", startupDelay));

		Runnable r = new Runnable()
		{
			/**
			 * Goto our startup activity
			 */
			@Override
			public void run()
			{
				Intent i = new Intent(MySplashScreenActivity.this, myStartupActivityClass);
				i.putExtra("somedata", urlData);
				startActivity(i);
				finish();
			}
		};

		Handler h = new Handler();
		h.postDelayed(r, startupDelay);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return false;
	}

	/**
	 * This is just an example of a task to complete in the background while the
	 * splash screen is showing. After the task is completed, the first activity
	 * is launched
	 */
	@SuppressWarnings("unused")
	private class FooBackgroundTask extends AsyncTask<Void, Void, Long>
	{
		/**
		 * Grab some data from a webpage 
		 * 
		 * @return How long in milliseconds this task took to do
		 */
		@Override
		protected Long doInBackground(Void... params)
		{
			Log.d(TAG, "Starting up background worker task"); 
						
			// Stopwatch used to time how long task took
			StopWatch sw = new StopWatch();

			try
			{
				sw.start();
				
				URL url = new URL("http://www.android.com");

				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

				InputStream in = new BufferedInputStream(urlConnection.getInputStream());

				String buf = IOUtils.toString(in, "UTF-8");
				
				urlData = buf;

			}
			catch (MalformedURLException e)
			{
				Log.e(TAG, e.getMessage(), e);
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage(), e);
			}
			finally
			{
				sw.stop();
			}

			long elapsedTime = sw.getTime();
			
			Log.d(TAG, "Finishing up background worker task - elapsed time = " + elapsedTime); 
			
			return elapsedTime;
		}

		/**
		 * Schedule the startup activity to begin after an appropriate wait time
		 * has elapsed.
		 * 
		 * @param runTime How long did this task take to complete
		 */
		@Override
		protected void onPostExecute(Long runTime)
		{
			super.onPostExecute(runTime);

			// Figure out how long before loading up the first activity
			long startupDelay = MIN_SPLASH_TIME - runTime;

			Log.d(TAG, "Performing post background task  - starting Startup Activity - startupDelay = " + startupDelay);
						
			gotoStartupActivity(startupDelay);
		}

	}
	
}
