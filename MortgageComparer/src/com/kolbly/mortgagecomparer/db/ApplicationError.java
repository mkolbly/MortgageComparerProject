package com.kolbly.mortgagecomparer.db;

import org.apache.commons.lang3.exception.ExceptionUtils;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Unhandled application errors (Exceptions)
 * 
 * @author mkolbly
 */
public class ApplicationError extends DbRow
{
	/** Database table name where this object is stored */
	public static final String TABLENAME = "ApplicationError";
	
	private String message = null;
	private String stackTrace = null;
	private String threadName = null;
	private long threadId = -1;
	private int threadPriority = -1;
	private long timeStamp = -1;
	
	/**
	 * Ctor with all inputs
	 * 
	 * @param id Database identity value
	 * @param message Exception message
	 * @param stackTrace Exception stack trace
	 * @param threadName Thread name where exception happened
	 * @param threadId Thread id where exception happened
	 * @param threadPriority Thread priority where exception happened
	 * @param timeStamp When exception occurred
	 */
	public ApplicationError(int id, String message, String stackTrace, String threadName, long threadId,
		int threadPriority, long timeStamp)
	{
		this.setId(id);
		this.message = message;
		this.stackTrace = stackTrace;
		this.threadName = threadName;
		this.threadId = threadId;
		this.threadPriority = threadPriority;
		this.timeStamp = timeStamp;
	}

	/**
	 * Construct object from Cursor
	 * <p>
	 * Note : This only works when the Cursor query returns all columns in order
	 * of the table
	 * 
	 * @param c Cursor to load data from - already
	 */
	public ApplicationError(Cursor c)
	{
		this(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getInt(5), c.getLong(6)); // NOSONAR
	}

	/**
	 * Ctor from uncaught exception info that our application exception handler
	 * receives
	 * 
	 * @param thread Thread when application exception happened
	 * @param ex Exception thrown
	 */
	public ApplicationError(Thread thread, Throwable ex)
	{
		this(0, ex.getMessage(), ExceptionUtils.getStackTrace(ex), thread.getName(), thread.getId(),
			thread.getPriority(), System.currentTimeMillis());

	}
	
	
	/* (non-Javadoc)
	 * @see com.kolbly.mortgagecomparer.db.DbTable#getContentValues()
	 */
	@Override
	public ContentValues getContentValues()
	{
		ContentValues values = new ContentValues();

		values.put(IDENTITY, this.getId());
		values.put("message", this.message);
		values.put("stackTrace", this.stackTrace);
		values.put("threadName", this.threadName);
		values.put("threadId", this.threadId);
		values.put("threadPriority", this.threadPriority);
		values.put("timeStamp", this.timeStamp);

		return values;
	}

	/* (non-Javadoc)
	 * @see com.kolbly.mortgagecomparer.db.DbTable#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return TABLENAME;
	}

}
