package com.kolbly.android.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;

/**
 * Tests for the ResourceUtil class
 * 
 * 
 * @Config(manifest=Config.DEFAULT) - uses manifest specified in 
 * 	org.robolectric.Config.properties file
 * 
 * @Config(manifest="TestManifest1.xml") - uses manifest in this test project
 * 
 * @Config(manifest="../MortgageComparer/AndroidManifest.xml") - uses manifest in 
 * 	MortgageComparer project.  Side effect is that any resources are also loaded
 * 	from there so any test resources are not available
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest="AndroidManifest.xml")
public class ResourceUtilTest extends MCTest
{	
			
	public ResourceUtilTest() throws Exception
	{
		super();
	}

	private Context myContext = Robolectric.getShadowApplication().getApplicationContext();
	
	/**
	 * Test reading a 1-line text file given the context and resource ID
	 */	
	@Test
	public void testOneLinerFromContext() throws Exception
	{		
		int resourceId = com.kolbly.mortgagecomparer.test.R.raw.test_resource_util_test_1; 
		
		String s = ResourceUtil.getResourceText(myContext, resourceId);
				
		assertEquals("abc", s);
	}
	

	
	/**
	 * Test reading a 2-line text file given the context and resource ID
	 */
	@Test
	public void testTwoLinerFromContext() throws Exception
	{
		
		int resourceId = com.kolbly.mortgagecomparer.test.R.raw.test_resource_util_test_2;
		
		String s = ResourceUtil.getResourceText(myContext, resourceId);
				
		assertEquals("abc\r\ndef", s);
	}


	@Test
	public void testCtor()
	{
		assertTrue(ClassUtil.callPrivateConstructor(ResourceUtil.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetResourceTextNoContext() throws IOException
	{
		ResourceUtil.getResourceText(null, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetResourceTextInvalidResourceId() throws IOException
	{
		ResourceUtil.getResourceText(myContext, 0);
	}
	
	
}
