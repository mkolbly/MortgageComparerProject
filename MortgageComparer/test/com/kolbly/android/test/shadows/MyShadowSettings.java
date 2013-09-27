package com.kolbly.android.test.shadows;
//package com.kolbly.android.test;
//
//import org.robolectric.annotation.Implementation;
//import org.robolectric.annotation.Implements;
//import org.robolectric.annotation.RealObject;
//
//import android.content.ContentResolver;
//import android.provider.Settings;
//import android.provider.Settings.SettingNotFoundException;
//
///**
// * Unsuccessful Attempt at shadowing Settings.System class
// * 
// * TODO: Figure out why this is sometimes thorwing an AbstractMethodError when
// * the unit test calls getLong() and it's not defined here.  Also, why is the
// * Robolectric's implementation of Settings.System apparently completely 
// * unaffected by settings set with ShadowSettings.set24HourTimeFormat(false)
// * The results of Settings.System.getLong(cr, Settings.System.TIME_12_24) is
// * never changed.
// * <p>
// * TODO: Figure out how to access a @RealObject static method from the 
// * Shadowed static method!  Haven't found it on the internet, perhaps it can't
// * be done!
// * <p>
// * Note: Mothball this for now - take it up later
// */
//@Implements(Settings.System.class)
//public class MyShadowSettings // extends Settings.NameValueTable
//{
////	@RealObject
////	private Settings.System realObject;
////
////	public void __constructor__()
////	{
////
////	}
//
////	public MyShadowSettings()
////	{
////		super();
////	}
//
//	@Implementation
//	public static int getInt(ContentResolver cr, String name, int def)
//	{
//		int value = 0;
//
//		int iValue = Settings.System.getInt(cr, name, 15);
//		
//		long longValue = Settings.System.getLong(cr, name, 14);
//
//		value = (int) longValue;
//
//		return value;
//	}
//
//}
