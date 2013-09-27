package com.kolbly.android.util.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.util.Log;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

@RunWith(RobolectricTestRunner.class)
//@Config(shadows = {ShadowDateFormat.class})
public class SystemDateFormatTest extends MCTest
{
	private static final String TAG = ClassUtil.getShortName(SystemDateFormatTest.class);
		
	public SystemDateFormatTest() throws Exception
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
		
		SystemDateFormat.instance().clear();
	}
	
	/**
	 * Important note: There is a robolectric bug that throws an exception
	 * whenever android.text.format.DateFormat.getDateFormatOrder(Context) gets
	 * called from DateFormat.getOSFormat(). They claim it will be fixed in 2.2
	 * of Robolectric. Until 2.2 is released, keep using ShadowDateFormat
	 * class.
	 * <p>
	 * It may just be best to use ShadowDateFormat anyway as we can control
	 * the formats it spits out and there seems to be no way to do that with
	 * Robolectric
	 */
	@Test
	public void testGetOsFormat()
	{
//		SystemDateFormat sdf = SystemDateFormat.instance();
//		
//		sdf.clear();
//	
//		ShadowDateFormat.setDateFormatOrder(new char[] {'M', 'd', 'y'});
//		assertEquals(DateFormat.MMDDYYYY, sdf.getFormat());
//		
//		sdf.clear();
//		ShadowDateFormat.setDateFormatOrder(new char[] {'d', 'M', 'y'});
//		assertEquals(DateFormat.DDMMYYYY, sdf.getFormat());
//		
//		sdf.clear();
//		ShadowDateFormat.setDateFormatOrder(new char[] {'y', 'M', 'd'});
//		assertEquals(DateFormat.YYYYMMDD, sdf.getFormat(true));
	}
	
	
	/** DateFormat received from our listener */
	private DateFormat receivedListenerDateFormat = null;
	
	
	/**
	 * Listener for the SystemDateFormatEvent 
	 */
	@Subscribe
	public void onDateSet(SystemDateFormatEvent event)
	{
		Log.d(TAG, "Got SystemDateFormatEvent : " + event.getFormat().toString());
		
		synchronized(this)
		{
			receivedListenerDateFormat = event.getFormat();
			notifyAll();
		}
	}
	
	@Test(timeout = 30000)
	public void testRegistering() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException
	{
		SystemDateFormat.overrideSystemDateFormat(DateFormat.MMDDYYYY);
		
		SystemDateFormat sdf = SystemDateFormat.instance();
				
		sdf.registerForDateFormatChanges();
		
		
		// Force our SystemDateFormat to send out notification of a date format change
		ClassUtil.callPrivateMethod(sdf, "notifyListeners");
		
		synchronized(this)
		{
			while (receivedListenerDateFormat == null)
			{
				wait();
			}
		}
				
		assertEquals(DateFormat.MMDDYYYY, receivedListenerDateFormat);			
	}


	
	@Test
	public void testCreateNewSdf()
	{
		SystemDateFormat sdf = SystemDateFormat.create(context);
		
		assertNotNull(sdf);
	}
	

	
}
