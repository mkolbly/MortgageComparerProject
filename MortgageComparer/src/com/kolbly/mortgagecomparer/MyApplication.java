package com.kolbly.mortgagecomparer;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ACRAConfigurationException;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;

/**
 * Our main application class
 * <p>
 * Program Features
 * <p>
 * - Customized uncaught exception handling <br>
 * - Error reporting via ACRA (Application Crash Reports for Android) <br>
 * - Splash screen with background task implementation
 * 
 */

@ReportsCrashes(formKey = "")
public class MyApplication extends Application
{
	private static final String TAG = ClassUtil.getShortName(MyApplication.class);
	
	public MyApplication()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		// Initialize Android Crash Report for Android
		this.setupAcra();

		// Setup our handler for uncaught application exceptions
		AppExceptionHandler.init(this);

		// Initialize our Globals
		G.init(this);
	}
	
	/**
	 * Setup ACRA crash reporting
	 * 
	 * ACRA Notes:
	 * <p>
	 * ACRA (Application Crash Reports for Android) is an open source crash
	 * reporting library for Android. @see https://github.com/ACRA
	 * <p>
	 * In the release mode, we setup ACRA to use Cloudant for our error
	 * reporting database
	 * 
	 * @see https://github.com/ACRA/acra/wiki/AdvancedUsage#dialog for crash
	 *      dialog options
	 * 
	 *      Cloudant (cloudant.com) hosts free ACRA databases. Cloudant Database
	 *      params: formUri =
	 *      "http://192.168.99.35:5984/acra-mortgage-comparer/_design/acra-storage/_update/report"
	 *      , formUriBasicAuthLogin = "ietymenecessomsellyielan",
	 * 
	 *      Local ACRA Database params: formUri =
	 *      "http://192.168.99.35:5984/acra-mortgage-comparer/_design/acra-storage/_update/report"
	 *      , formUriBasicAuthLogin = "reporter", formUriBasicAuthPassword = "k******e",
	 */
	private void setupAcra()
	{
		try
		{	
			ApplicationInfo appInfo = getApplicationInfo();
			
			boolean isDebugBuild = ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == 
				ApplicationInfo.FLAG_DEBUGGABLE);		
			
			ACRAConfiguration c = ACRA.getNewDefaultConfig(this); 

			c.setFormKey("");
			c.setReportType(org.acra.sender.HttpSender.Type.JSON);
			c.setHttpMethod(org.acra.sender.HttpSender.Method.PUT);
			c.setResDialogText(R.string.crash_dialog_text);
			c.setMode(ReportingInteractionMode.DIALOG);
			c.setResToastText(R.string.crash_toast_text);
			c.setResDialogIcon(android.R.drawable.ic_dialog_info);
			c.setResDialogTitle(R.string.crash_dialog_title);
			c.setResDialogCommentPrompt(R.string.crash_dialog_comment_prompt);
			c.setResDialogOkToast(R.string.crash_dialog_ok_toast);
			c.setLogcatArguments(new String[] { "-t", "300", "-v", "time" });

			if (isDebugBuild)
			{
				c.setFormUri("http://192.168.99.35:5984/acra-mortgage-comparer/_design/acra-storage/_update/report");
				c.setFormUriBasicAuthLogin("reporter");
				c.setFormUriBasicAuthPassword("key4l1me");
			}
			else
			{
				c.setFormUri("https://mkolbly.cloudant.com/acra-mortgage-comparer/");
				c.setFormUriBasicAuthLogin("ietymenecessomsellyielan");
				c.setFormUriBasicAuthPassword("KoNjxFgaNWf6jk0jxxmgsQHs");			
			}
			
			ACRA.setConfig(c);
			ACRA.init(this);
		}
		catch (ACRAConfigurationException ex)
		{
			Log.e(TAG, "Failed to setup ACRA", ex); 
		}

	}

}
