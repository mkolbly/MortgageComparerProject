package com.kolbly.android.util.time;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowSettings;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Log;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

@RunWith(RobolectricTestRunner.class)
//@Config(shadows = {MyShadowSettings.class})
public class SystemTimeFormatTest extends MCTest
{
	private static final String TAG = ClassUtil.getShortName(SystemTimeFormatTest.class);
	
	public SystemTimeFormatTest() throws Exception
	{
		super();
	}

	@Before
	public void setUp() throws Exception
	{
		Bus.getBus().setEnforcer(ThreadEnforcer.ANY);
	
		Bus.getBus().register(this);
	}
	
	@After
	public void tearDown() throws Exception
	{
		Bus.getBus().unregister(this);
	}
	

	@Test
	public void testMyShadowSettings()
	{
		ContentResolver cr = this.context.getContentResolver();
		String name = Settings.System.TIME_12_24;
		int def = 15;
		
		
		int setting = Settings.System.getInt(cr, name, def);
		
		
		Log.d(TAG, "Got setting = " + setting);
		
	}
	
	@Test
	public void testGetOsFormat()
	{
		ShadowSettings.set24HourTimeFormat(false);
		
		SystemTimeFormat stf = SystemTimeFormat.instance();
		
		assertEquals(TimeFormat.TIME_12, stf.getSystemTimeFormat());
		
	
		ShadowSettings.set24HourTimeFormat(true);
		
		assertEquals(TimeFormat.TIME_24, stf.getSystemTimeFormat(true));
		
	}
	
	
	private TimeFormat mySystemTimeFormatEventReceived = null;
	
	/**
	 * Listener for the SystemDateFormatEvent 
	 */
	@Subscribe
	public void onTimeSet(SystemTimeFormatEvent event)
	{
		Log.d(TAG, "Got SystemTimeFormatEvent : " + event.getFormat().toString());
		
		synchronized(this)
		{
			mySystemTimeFormatEventReceived = event.getFormat(); 
			notifyAll();
		}
	}
	
	@Test(timeout = 60000)
	public void testSendSystemTimeFormatEvent() throws InterruptedException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		
		ShadowSettings.set24HourTimeFormat(true);
				
		SystemTimeFormat stf = SystemTimeFormat.instance();
		stf.clear();
		
		stf.registerForTimeFormatChanges();
		
		// Force our SystemTimeFormat to send out notification of a time format change
		ClassUtil.callPrivateMethod(stf, "notifyListeners"); 
		
		synchronized(this)
		{
			while (mySystemTimeFormatEventReceived == null)
			{
				wait();
			}
		}
		
		assertEquals(TimeFormat.TIME_24, mySystemTimeFormatEventReceived);
		
	}
	
	

	
}
