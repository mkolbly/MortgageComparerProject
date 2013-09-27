package com.kolbly.android.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;

/**
 * External storage states <code>enum</code> that mirrors values returned from
 * <code>android.os.Environment.getExternalStorageState()</code>
 * 
 * @author mkolbly
 */
public enum StorageState
{
	/** Media was removed before it was unmounted */
	MEDIA_BAD_REMOVAL(Environment.MEDIA_BAD_REMOVAL, false, false),

	/** Media is present and being disk-checked */
	MEDIA_CHECKING(Environment.MEDIA_CHECKING, false, false),

	/** Media is present and mounted at its mount point with read/write access  */
	MEDIA_MOUNTED(Environment.MEDIA_MOUNTED, true, true),

	/** Media is present and mounted at its mount point with read only access */
	MEDIA_MOUNTED_READ_ONLY(Environment.MEDIA_MOUNTED_READ_ONLY, true, false),

	/** Media is present but is blank or is using an unsupported filesystem */
	MEDIA_NOFS(Environment.MEDIA_NOFS, false, false),

	/** Media is not present */
	MEDIA_REMOVED(Environment.MEDIA_REMOVED, false, false),

	/** Media is present not mounted, and shared via USB mass storage */
	MEDIA_SHARED(Environment.MEDIA_SHARED, false, false),

	/** Media is present but cannot be mounted */
	MEDIA_UNMOUNTABLE(Environment.MEDIA_UNMOUNTABLE, false, false),

	/** Media is present but cannot be mounted */
	MEDIA_UNMOUNTED(Environment.MEDIA_UNMOUNTED, false, false)
	;

	/** Storage state string value */
	private final String myStorageState;
	
	/** Is this storage state readable */
	private final boolean isReadableFlag;
	
	/** Is this storage state writable */
	private final boolean isWritableFlag;

	// Our StorageState lookup map to keep track of which String maps to what StorageState
	private static final Map<String, StorageState> LOOKUPMAP = new HashMap<String, StorageState>();

	/**
	 * Populate myLookupMap
	 */
	static
	{
		for (StorageState ss : EnumSet.allOf(StorageState.class))
		{
			LOOKUPMAP.put(ss.myStorageState, ss);
		}
	}

	/**
	 * Ctor
	 * 
	 * @param state String value of StorageState
	 * @param isReadable In this state, is media readable
	 * @param isWritable Is this state, is media writable
	 */
	private StorageState(String state, boolean isReadable, boolean isWritable)
	{
		this.myStorageState = state;
		this.isReadableFlag = isReadable;
		this.isWritableFlag = isWritable;
	}

	/**
	 * String value of StorageState
	 */
	@Override
	public String toString()
	{
		return this.myStorageState;
	}

	/**
	 * Does this storage state allow reading
	 * 
	 * @return true if readable
	 */
	public boolean isReadable()
	{
		return this.isReadableFlag;
	}
	
	/**
	 * Does this storage state allow writing
	 * 
	 * @return true if writable
	 */
	public boolean isWritable()
	{
		return this.isWritableFlag;
	}
	
	/**
	 * Lookup the StorageState by it's String value
	 * 
	 * @param state String state value
	 * @return StorageState enumerated value that corresponds with state
	 */
	public static StorageState lookup(String state)
	{
		return StorageState.LOOKUPMAP.get(state);
	}

	/**
	 * Gets the current state of the primary "external" storage device.
	 * 
	 * @see android.os.Environment#getExternalStorageState()
	 * 
	 * @return current external storage state
	 */
	public static StorageState getExternalStorageState()
	{
		String externalStorageState = Environment.getExternalStorageState();

		StorageState state = StorageState.lookup(externalStorageState);

		return state;
	}


}
