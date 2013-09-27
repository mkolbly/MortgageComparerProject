package com.kolbly.android.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;

import android.os.Environment;
import android.util.Log;

import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.StrUtil;

/**
 * Simple utility class to write out application exceptions to a file on the SD
 * card
 * 
 * @author mkolbly
 */
public class ExceptionWriter
{
	private static final String TAG = ClassUtil.getShortName(ExceptionWriter.class);
	
	/** File to be written to */
	private File myFile = null;

	/** Exception file written to when one is not explicitly stated in constructor */
	public static final String DEFAULT_EXCEPTION_FILE = "MortgageComparerExceptions.log";
	
	/** Encoding used to write file */
	private static final String ENCODING = "UTF-8";
	
	/**
	 * Create an ExceptionWriter that will write to the SD Card in the default
	 * location specified by DEFAULT_EXCEPTION_FILE
	 */
	public ExceptionWriter()
	{		
		this(Environment.getExternalStorageDirectory().getAbsolutePath(), DEFAULT_EXCEPTION_FILE);
	}

	/**
	 * Create an ExceptionWriter for the following parameters
	 * 
	 * @param dirPath Exception log directory path
	 * @param fileName Exception log filename (Can optionally include partial path)
	 */
	public ExceptionWriter(String dirPath, String fileName)
	{
		this.myFile = new File(dirPath, fileName);
	}
	
	
	/**
	 * Writes a single log entry to the file
	 * 
	 * @param txt Text to write
	 * @param ex Exception to write
	 * @return true on success
	 */
	public boolean write(String txt, Throwable ex)
	{
		if (StrUtil.isNullOrEmpty(txt))
		{
			throw new IllegalArgumentException("Invalid txt");
		}

		if (ex == null)
		{
			throw new IllegalArgumentException("Invalid ex");
		}
		
		boolean bSuccess = true;

		try
		{
			File parentFile = this.myFile.getParentFile();
			parentFile.mkdirs();			// NOSONAR

			FileOutputStream f = new FileOutputStream(this.myFile, true);
			OutputStreamWriter w = new OutputStreamWriter(f, ENCODING);

			String stackTrace = ExceptionUtils.getStackTrace(ex);

			Locale locale = G.getLocale();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", locale);
			Date now = new Date();
			String timestamp = sdf.format(now);

			w.append(String.format("%s | %s%n%s%n", timestamp, txt, stackTrace));

			w.close();
		}
		catch (Exception e)
		{
			bSuccess = false;
			Log.e(TAG, "Failed to write to logfile", e);
		}

		return bSuccess;
	}

	/**
	 * Is the external media writable
	 * 
	 * @return true if writable
	 */
	public boolean isWritable()
	{
		return StorageState.getExternalStorageState().isWritable();
	}

	/**
	 * Is the external media readable
	 * 
	 * @return true if readable
	 */
	public boolean isReadable()
	{
		return StorageState.getExternalStorageState().isReadable();
	}

}
