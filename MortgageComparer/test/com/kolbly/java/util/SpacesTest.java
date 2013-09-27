package com.kolbly.java.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;

@RunWith(RobolectricTestRunner.class)
public class SpacesTest extends MCTest
{

	public SpacesTest() throws Exception
	{
		super();
	}

	@Test
	public void test()
	{
		ClassUtil.callPrivateConstructor(Spaces.class);
		
		assertEquals("", Spaces.repeat(0));
		assertEquals(" ", Spaces.repeat(1));
		assertEquals("  ", Spaces.repeat(2));
		assertEquals("   ", Spaces.repeat(3));
	}

}
