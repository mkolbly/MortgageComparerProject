package com.kolbly.java.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;

@RunWith(RobolectricTestRunner.class)
public class MathUtilTest extends MCTest
{
	public MathUtilTest() throws Exception
	{
		super();
	}

	@Test
	public void testAreEqual()
	{
		ClassUtil.callPrivateConstructor(MathUtil.class);
		
		assertTrue(MathUtil.areEqual(5.01d, 5.01d));
		
		assertTrue(MathUtil.areEqual(5.0001d, 5.0001d));
		
		assertTrue(MathUtil.areEqual(5.00000001d, 5.00000002d));
		
		assertFalse(MathUtil.areEqual(5.001d, 5.002d));
	}

}
