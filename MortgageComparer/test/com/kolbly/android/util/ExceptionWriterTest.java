package com.kolbly.android.util;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowEnvironment;

import android.os.Environment;

import com.kolbly.android.test.MCTest;

@RunWith(RobolectricTestRunner.class)
public class ExceptionWriterTest extends MCTest
{

	public ExceptionWriterTest() throws Exception
	{
		super();
	}

	@Test
	public void test()
	{
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
		
		ExceptionWriter ew = new ExceptionWriter();
		
		Exception ex = new Exception("Very bad exception");
		
		boolean bSuccess = ew.write("Failed", ex);
		
		assertTrue(bSuccess);
	
	}
	

	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void testWriteNull()
	{
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
		
		ExceptionWriter ew = new ExceptionWriter();
		
		Exception ex = new Exception("Very bad exception");
		
		ew.write(null, ex);
	}

	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testWriteNull2()
	{
		exception.expect(IllegalArgumentException.class);
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
		
		Exception ex = new Exception("Very bad exception");
		
		ExceptionWriter ew = new ExceptionWriter();
		ew.write(null, ex);
	}
	
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testWriteNullException()
	{
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
		
		ExceptionWriter ew = new ExceptionWriter();
				
		ew.write("Oops", null);
	}	
	
	
	@Test
	public void testStorageState()
	{
		ExceptionWriter ew = new ExceptionWriter();
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
		assertTrue(ew.isReadable());
		assertTrue(ew.isWritable());
	
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_UNMOUNTED);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_REMOVED);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED_READ_ONLY);
		assertTrue(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_NOFS);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_BAD_REMOVAL);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_CHECKING);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_SHARED);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_UNMOUNTABLE);
		assertFalse(ew.isReadable());
		assertFalse(ew.isWritable());

	}
	
}
