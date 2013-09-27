package com.kolbly.android.util.date;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;

@RunWith(RobolectricTestRunner.class)
public class SystemDateFormatEventTest extends MCTest
{

	public SystemDateFormatEventTest() throws Exception
	{
		super();
	}

	
	@Test
	public void test()
	{
		assertTrue(ClassUtil.callPrivateConstructor(SystemDateFormatEvent.class));
		
		SystemDateFormatEvent event = new SystemDateFormatEvent(DateFormat.YYYYMMDD);
		
		assertEquals(DateFormat.YYYYMMDD, event.getFormat());
	}

}
