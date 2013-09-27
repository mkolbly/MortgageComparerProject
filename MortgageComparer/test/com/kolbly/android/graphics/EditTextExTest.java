package com.kolbly.android.graphics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;

@RunWith(RobolectricTestRunner.class)
public class EditTextExTest extends MCTest
{
	public  EditTextExTest() throws Exception
	{
		super();
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		EditTextEx et = new EditTextEx(this.context);
		
		et.setText(null);
		assertEquals(0, et.getDouble(), 0.01);
		
		et.setText("");
		assertEquals(0, et.getDouble(), 0.01);
		
		et.setText("0");
		assertEquals(0, et.getDouble(), 0.01);
		
		et.setText("0.0");
		assertEquals(0, et.getDouble(), 0.01);
		
		et.setText("1.15");
		assertEquals(1.15, et.getDouble(), 0.01);
	}



}
