package com.kolbly.mortgagecomparer.db;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.kolbly.android.util.DateUtil;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Our User object
 * 
 * This is very simple for now and only contains a name and birth date
 * 
 * @author mkolbly
 */
public class User extends DbRow
{
	/** Database table name where this object is stored */
	public static final String TABLENAME = "User";
		
	/** Default user name to be used until manually set by the consumer */
	private static final String DEFAULT_USERNAME = "Borrower";
	
	/** Default birth date to be used until manually set by the consumer */
	private static final Date DEFAULT_BIRTH_DATE;
	
	/** User's name */
	private String myUserName = null;
	
	/** User's birth date */
	private Date myBirthDate = null;
	
	static
	{		
		final int year = 1966;
		final int month = Calendar.FEBRUARY;
		final int day = 19;
		DEFAULT_BIRTH_DATE = new GregorianCalendar(year, month, day).getTime();
	}
	
	/**
	 * Create the user with given parameters
	 * 
	 * @param userName User's name
	 * @param birthDate User's birth date
	 */
	public User(String userName, Date birthDate)
	{
		this.myUserName = userName;
		this.myBirthDate = new Date(birthDate.getTime());
	}

	/**
	 * Ctor from cursor - data will be retrieved from the current cursor position
	 * 
	 * @param c Cursor to create this object from
	 */
	public User(Cursor c)
	{
		int i = 0;
		
		this.setId(c.getInt(i++));
		
		this.myUserName = c.getString(i++);
		this.myBirthDate = new Date(c.getLong(i++));
	}

	/* (non-Javadoc)
	 * @see com.kolbly.mortgagecomparer.db.DbRow#getContentValues()
	 */
	@Override
	public ContentValues getContentValues()
	{
		ContentValues cv = new ContentValues();
	
		cv.put(IDENTITY, this.getId());
		cv.put("userName", this.myUserName);
		cv.put("birthDate",  this.myBirthDate.getTime());
		
		return cv;
	}

	/* (non-Javadoc)
	 * @see com.kolbly.mortgagecomparer.db.DbRow#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return TABLENAME;
	}
	
	
	/**
	 * Sets the user name
	 * 
	 * @param name User's name
	 */
	public void setUserName(String name)
	{
		this.myUserName = name;
	}
	
	/**
	 * Gets this user's name
	 * 
	 * @return User's name
	 */
	public String getUserName()
	{
		return this.myUserName;
	}
	
	/**
	 * Gets the user's birth date
	 * 
	 * @return User's birth date
	 */
	public Date getBirthDate()
	{
		return new Date(this.myBirthDate.getTime());
	}
	
	/**
	 * Get the user's birth date
	 * 
	 * @return User's birth date
	 */
	public String getBirthDateString()
	{
		Date birthDate = this.getBirthDate();
				
		return DateUtil.formatDateString(birthDate);
	}
	
	
	/**
	 * Sets the user's birthday
	 * 
	 * @param birthDate User's birth date
	 */
	public void setBirthDate(Date birthDate)
	{
		if (birthDate != null)
		{
			this.myBirthDate = new Date(birthDate.getTime());
		}
		else
		{
			this.myBirthDate = null;
		}
	}
	
	/**
	 * Convenience function for determining if the given user exists and has
	 * a birth date set
	 * 
	 * @param user User to test
	 * @return true if user exists and their birth date is set
	 */
	public static boolean hasBirthDate(User user)
	{
		return (user != null) && (user.myBirthDate != null);
	}
	
	/**
	 * Create a default user to be used when one is not already stored in the 
	 * database 
	 * 
	 * @return Default user
	 */
	public static User createDefaultUser()
	{
		return new User(DEFAULT_USERNAME, DEFAULT_BIRTH_DATE);
	}
}
