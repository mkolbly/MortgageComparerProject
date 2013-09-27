//package com.kolbly.android.util.time;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;
//
//import android.content.ContentResolver;
//import android.content.res.Configuration;
//import android.provider.Settings;
//import android.util.Log;
//
//import com.kolbly.android.test.MCTest;
//import com.kolbly.android.test.MyShadowSettings;
//import com.kolbly.java.util.ClassUtil;
//
//@RunWith(RobolectricTestRunner.class)
//@Config(shadows = {MyShadowSettings.class})
//public class MyShadowSettingsTest extends MCTest
//{
//	public MyShadowSettingsTest() throws Exception
//	{
//		super();
//	}
//
//	private static final String TAG = ClassUtil.getShortName(MyShadowSettingsTest.class);
//
//	@Test
//	public void testMyShadowSettings()
//	{
//		ContentResolver cr = this.context.getContentResolver();
//		String name = Settings.System.TIME_12_24;
//		int def = 15;
//				
//		long lValue = Settings.System.getLong(cr, name, 15);
//		
//		
//		int iValue = Settings.System.getInt(cr, name, 14);
//		
//	
//		
//	}
//	
//
//	
//
//	
//}
