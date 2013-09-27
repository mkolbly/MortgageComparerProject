package com.kolbly.android.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowEnvironment;

import android.os.Environment;

import com.kolbly.android.test.MCTest;
import com.kolbly.android.util.StorageState;

/**
 * Tests for enumerated type StorageState
 */
@RunWith(RobolectricTestRunner.class)
public class StorageStateTest extends MCTest
{

	public StorageStateTest() throws Exception
	{
		super();
	}

	@Test
	public void testValues()
	{	
		assertEquals(StorageState.MEDIA_BAD_REMOVAL, StorageState.lookup(Environment.MEDIA_BAD_REMOVAL));
		assertEquals(StorageState.MEDIA_CHECKING, StorageState.lookup(Environment.MEDIA_CHECKING));
		assertEquals(StorageState.MEDIA_MOUNTED, StorageState.lookup(Environment.MEDIA_MOUNTED));
		assertEquals(StorageState.MEDIA_MOUNTED_READ_ONLY, StorageState.lookup(Environment.MEDIA_MOUNTED_READ_ONLY));
		assertEquals(StorageState.MEDIA_NOFS, StorageState.lookup(Environment.MEDIA_NOFS));
		assertEquals(StorageState.MEDIA_REMOVED, StorageState.lookup(Environment.MEDIA_REMOVED));
		assertEquals(StorageState.MEDIA_SHARED, StorageState.lookup(Environment.MEDIA_SHARED));
		assertEquals(StorageState.MEDIA_UNMOUNTABLE, StorageState.lookup(Environment.MEDIA_UNMOUNTABLE));
		assertEquals(StorageState.MEDIA_UNMOUNTED, StorageState.lookup(Environment.MEDIA_UNMOUNTED));

		assertFalse(StorageState.MEDIA_BAD_REMOVAL.isReadable());
		assertFalse(StorageState.MEDIA_BAD_REMOVAL.isWritable());
		
		assertFalse(StorageState.MEDIA_CHECKING.isReadable());
		assertFalse(StorageState.MEDIA_CHECKING.isWritable());
		
		assertTrue(StorageState.MEDIA_MOUNTED.isReadable());
		assertTrue(StorageState.MEDIA_MOUNTED.isWritable());
		
		assertTrue(StorageState.MEDIA_MOUNTED_READ_ONLY.isReadable());
		assertFalse(StorageState.MEDIA_MOUNTED_READ_ONLY.isWritable());
		
		assertFalse(StorageState.MEDIA_NOFS.isReadable());
		assertFalse(StorageState.MEDIA_NOFS.isWritable());
		
		assertFalse(StorageState.MEDIA_REMOVED.isReadable());
		assertFalse(StorageState.MEDIA_REMOVED.isWritable());
		
		assertFalse(StorageState.MEDIA_SHARED.isReadable());
		assertFalse(StorageState.MEDIA_SHARED.isWritable());
		
		assertFalse(StorageState.MEDIA_UNMOUNTABLE.isReadable());
		assertFalse(StorageState.MEDIA_UNMOUNTABLE.isWritable());
		
		assertFalse(StorageState.MEDIA_UNMOUNTED.isReadable());
		assertFalse(StorageState.MEDIA_UNMOUNTED.isWritable());
		
	}

	@Test
	public void testToString()
	{
		assertEquals(Environment.MEDIA_BAD_REMOVAL, StorageState.MEDIA_BAD_REMOVAL.toString());

		assertEquals(Environment.MEDIA_CHECKING, StorageState.MEDIA_CHECKING.toString());

		assertEquals(Environment.MEDIA_MOUNTED, StorageState.MEDIA_MOUNTED.toString());

		assertEquals(Environment.MEDIA_MOUNTED_READ_ONLY, StorageState.MEDIA_MOUNTED_READ_ONLY.toString());

		assertEquals(Environment.MEDIA_NOFS, StorageState.MEDIA_NOFS.toString());

		assertEquals(Environment.MEDIA_REMOVED, StorageState.MEDIA_REMOVED.toString());

		assertEquals(Environment.MEDIA_SHARED, StorageState.MEDIA_SHARED.toString());

		assertEquals(Environment.MEDIA_UNMOUNTABLE, StorageState.MEDIA_UNMOUNTABLE.toString());

		assertEquals(Environment.MEDIA_UNMOUNTED, StorageState.MEDIA_UNMOUNTED.toString());

	}
	
	@Test
	public void testExternalStorageState()
	{
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_BAD_REMOVAL);
		assertEquals(Environment.MEDIA_BAD_REMOVAL, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_CHECKING);
		assertEquals(Environment.MEDIA_CHECKING, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED);
		assertEquals(Environment.MEDIA_MOUNTED, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_MOUNTED_READ_ONLY);
		assertEquals(Environment.MEDIA_MOUNTED_READ_ONLY, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_NOFS);
		assertEquals(Environment.MEDIA_NOFS, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_SHARED);
		assertEquals(Environment.MEDIA_SHARED, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_UNMOUNTABLE);
		assertEquals(Environment.MEDIA_UNMOUNTABLE, StorageState.getExternalStorageState().toString());
		
		ShadowEnvironment.setExternalStorageState(Environment.MEDIA_UNMOUNTED);
		assertEquals(Environment.MEDIA_UNMOUNTED, StorageState.getExternalStorageState().toString());
	}
}