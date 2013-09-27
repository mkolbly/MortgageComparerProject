package com.kolbly.android.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kolbly.java.util.ClassUtil;

/**
 * Database utilities
 * 
 * @author mkolbly
 */
public final class DbUtil
{
	private static final String TAG = ClassUtil.getShortName(DbUtil.class);
	
	/**
	 * Private ctor for utilityW class
	 */
	private DbUtil() {}
	
	/** System-defined tables in the sqlite database */
	private static final String[] SYSTEM_TABLES = {"android_metadata", "sqlite_sequence"};
	
	/**
	 * Drop all tables from the database
	 * 
	 * @param db Database to drop tables for
	 */
	public static void deleteAllTables(SQLiteDatabase db)
	{
		List<String> allUserTables = DbUtil.getAllTablenames(db);
				
		db.beginTransaction();
		try
		{
			for (String table : allUserTables)
			{
				Log.d(TAG, "Removing table : " + table);
				String sql = String.format("DROP TABLE If EXISTS %s", table);
				db.execSQL(sql);
			}
			
			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}
	
	
	/**
	 * Get a list of all user-defined tables in the given database
	 * 
	 * @param db Database to get tables for
	 * @return List of tables
	 */
	public static List<String> getAllTablenames(SQLiteDatabase db)
	{
		List<String> tables = new ArrayList<String>();
		List<String> systemTables = Arrays.asList(SYSTEM_TABLES);
		
		Cursor c = db.rawQuery("select name from sqlite_master where type='table'", null);
		
		while (c.moveToNext())
		{
			String table = c.getString(0);
			if (! systemTables.contains(table))
			{
				tables.add(table);
			}
		}
		
		c.close();
		
		return tables;
	}
}
