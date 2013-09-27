package com.kolbly.mortgagecomparer.db;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.Loggable;
import com.kolbly.java.util.Spaces;

/**
 * Represents a row of data in a database table
 * <p>
 * This is fairly simplistic for now. Can build it out later as needs demand
 */
public abstract class DbRow implements Loggable
{
	/** Default identity column name - Constant value: "_id" */
	public static final String IDENTITY = BaseColumns._ID;

	/** Database identity value */
	private int id = 0;

	/**
	 * Sets our object database identity value
	 * 
	 * @param id Database identity value
	 */
	public void setId(long id)
	{
		this.id = (int) id;
	}

	/**
	 * Gets our object database identity value
	 * 
	 * @return Database identity value
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Gets our database table name
	 * 
	 * @return Database table name
	 */
	public abstract String getTableName();

	/**
	 * Content values needed for inserting all member data into the database _id
	 * should be omitted or set to 0 for insert
	 */
	public abstract ContentValues getContentValues();

	/**
	 * Create a xml string suitable for debug logging 
	 * 
	 * @return String representation
	 */
	@Override
	public String toXml()
	{
		return this.toXml(0);

	}

	/**
	 * Create a xml string suitable for debug logging 
	 * 
	 * @param tab Tab spacing to use (1 tab == 3 spaces)
	 * @return String representation
	 */
	@Override
	public String toXml(int tab)
	{
		ContentValues cv = this.getContentValues();

		Set<Entry<String, Object>> set = cv.valueSet();
		Iterator<Entry<String, Object>> itr = set.iterator();
		StringBuffer sb = new StringBuffer();

		String className = ClassUtil.getShortName(this.getClass());
		String tableName = this.getTableName();
		
		try
		{
			String s = Spaces.repeat(TABSIZE * tab);
			sb.append(String.format("%n%s<%s : Table = %s>%n", s, className, tableName));

			while (itr.hasNext())
			{
				Map.Entry<String, Object> me = (Map.Entry<String, Object>) itr.next();
				String key = me.getKey().toString();
				Object value = me.getValue();

				sb.append(String.format("%s   <%s>%s</%s>%n", s, key, value, key));
			}

			sb.append(String.format("%s</%s>%n", s, className));

		}
		catch (Exception ex)
		{
			sb = new StringBuffer(ExceptionUtils.getStackTrace(ex));
		}

		return sb.toString();
	}

	/**
	 * Get the name of the database identity column
	 * 
	 * @return Identity column name
	 */
	public String getIdentityCol()
	{
		return DbRow.IDENTITY;
	}
	

}
