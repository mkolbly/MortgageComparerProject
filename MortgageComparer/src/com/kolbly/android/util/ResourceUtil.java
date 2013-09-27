package com.kolbly.android.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.kolbly.java.util.ClassUtil;

/**
 * Android Resources utility class
 * 
 * @author mkolbly
 */
public final class ResourceUtil
{
	private static final String TAG = ClassUtil.getShortName(ResourceUtil.class);

	/** Buffer size for reading resource files */
	private static final int BUF_SIZE = 1024;

	/**
	 * Private ctor for utility class
	 */
	private ResourceUtil()
	{
	}

	/**
	 * Get the contents of a raw resource text file
	 * 
	 * @param context - Context of resource to be read
	 * @param resourceId - Resource ID of text file
	 * @return - String containing contents of resource text file or null on
	 *         error
	 */
	public static String getResourceText(Context context, int resourceId)
	{
		// We must have a context
		if (context == null)
		{
			throw new IllegalArgumentException("context may not be null");
		}

		// Check for valid resourceId
		if (resourceId <= 0)
		{
			throw new IllegalArgumentException("resourceId must be > 0");
		}

		Resources res = context.getResources();
		String fileContents = null;

		InputStream in = res.openRawResource(resourceId);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(BUF_SIZE);
		byte[] buffer = new byte[BUF_SIZE];
		int length = 0;

		// Fill up our buffer
		try
		{
			while ((length = in.read(buffer)) != -1)
			{
				baos.write(buffer, 0, length);
			}
		}
		catch (IOException ex)
		{
			Log.e(TAG, ex.getMessage(), ex);
			
			// Return null on any exception
			return null;
		}

		// Convert our buffer to a UTF-8 String return value
		try
		{
			fileContents = baos.toString("UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			Log.e(TAG, ex.getMessage(), ex);
			
			// Return null on any exception
			return null;
		}

		return fileContents;
	}

}
