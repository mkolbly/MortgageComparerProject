package com.kolbly.global;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.util.Log;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "AndroidManifest333.xml")
public class GTest extends MCTest
{
	private static final String TAG = ClassUtil.getShortName(GTest.class);
	
	public GTest() throws Exception
	{
		super();
	}

	
	@Test
	public void test()
	{
		assertTrue(G.getLocale() != null);
		
		assertTrue(G.getAppContext() != null);
	}
	
	@Test
	public void testCtor()
	{
		assertTrue(ClassUtil.callPrivateConstructor(G.class));
		
	}
	
	@Test
	public void testIsDebugBuild()
	{
		boolean isDebugBuild = G.isDebugBuild();		
		
		Log.d(TAG, "isDebugBuild = " + isDebugBuild);
	}

}
