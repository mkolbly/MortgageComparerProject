package com.kolbly.android.test;
//package com.kolbly.android;
//
//import org.junit.runners.model.InitializationError;
//import org.robolectric.Robolectric;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.SdkEnvironment;
//import org.robolectric.annotation.Config;
//import org.robolectric.shadows.ShadowLog;
//
//import android.content.Context;
//
//import com.kolbly.mortgagecomparer.G;
//import com.kolbly.mortgagecomparer.S;
//import com.kolbly.mortgagecomparer.db.DataManager;
//
//
///**
// * Notes:
// * <p>
// * 
// * @Config(manifest="../MortgageComparer/AndroidManifest.xml") - uses manifest in 
// * 	MortgageComparer project.  Side effect is that any resources are also loaded
// * 	from there so any test resources are not available
// */
//@Config(manifest="../MortgageComparer/AndroidManifest.xml")
//public class MyTestRunner extends RobolectricTestRunner 
//{
//	public Context context;
//	public DataManager dm;
//	
//	public MyTestRunner(Class<?> testClass) throws InitializationError
//	{
//		super(testClass);
//		
//		ShadowLog.stream = System.out;
//
//		context = Robolectric.getShadowApplication().getApplicationContext();
//		S.init(context);
//		G.init(context);
//		
//		dm = DataManager.create(context, "test.db", null, 1);
//		
//	}
//
////	@Override 
////	protected void configureShadows(SdkEnvironment sdkEnvironment, Config config)
////	{
////		ShadowLog.stream = System.out;
////		
////		super.configureShadows(sdkEnvironment, config);
////		
////	}
//
//}
