package com.kolbly.mortgagecomparer.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kolbly.android.util.DbUtil;
import com.kolbly.android.util.SqlSplitter;
import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;

/**
 * Database helper class
 * 
 * @author mkolbly
 */
public class MyDbOpenHelper extends SQLiteOpenHelper
{
	private static final String TAG = ClassUtil.getShortName(MyDbOpenHelper.class);

	/** Current database version */
	public static final int DATABASE_VERSION = 1;

	/** Default database name */
	private static final String DEFAULT_DATABASE_NAME = "loandb.sqlite";

	/** Keep Context this around for resource access */
	private Context myContext;											// NOSONAR

	/** Raw resource filename prefix containing sql statements to create the db */
	private static final String DATABASE_RAW_NAME_PREFIX = "database_";

	/** Database name */
	private String myDatabaseName = null;
	
	/**
	 * Main constructor program should use
	 * 
	 * @param context to use to open or create the database
	 */
	public MyDbOpenHelper(Context context)
	{
		this(context, DEFAULT_DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Convenience constructor for testing or just more control
	 * 
	 * @param context to use to open or create the database
	 * @param name of the database file, or null for an in-memory database
	 * @param factory to use for creating cursor objects, or null for the default
	 * @param version number of the database (starting at 1)
	 */
	public MyDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, name, factory, version);

		this.myDatabaseName = name;
		this.myContext = context;
	}

	/**
	 * Create database tables
	 * <p>
	 * Execute all sql contained in raw sql text files. Sql text files can have
	 * multiple sql statements separated by a semicolon and EOL. Sql files follow
	 * naming convention "db_version_<#>" and are executed in numerical order up
	 * to the current DATABASE_VERSION
	 */
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.beginTransaction();
		
		try
		{
			for (int i = 1; i <= DATABASE_VERSION; i++)
			{
				execSqlResource(db, i);
			}

			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}

	/**
	 * Upgrade database
	 * <p>
	 * Upgrade sql contained in raw resource text sql files. Sql statements in
	 * the resource files are separated by a semicolon and EOL.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.i(TAG, String.format("Upgrading database version from %d -> %d", oldVersion, newVersion));

		if (G.isDebugBuild())
		{
			// For dev purposes only
			DbUtil.deleteAllTables(db);
			this.onCreate(db);
			return;
		}
		
		db.beginTransaction();
		
		try
		{
			for (int i = oldVersion; i <= newVersion; ++i)
			{
				execSqlResource(db, i);
			}

			db.setTransactionSuccessful();
		}
		finally
		{
			db.endTransaction();
		}
	}

	/**
	 * Exec sql raw resource file for given database version
	 * <p>
	 * Note: Raw sql is contained in raw resource files /res/raw/database_#
	 * 
	 * @param db - open database
	 * @param dbVersion - database version to execute
	 */
	private void execSqlResource(SQLiteDatabase db, int dbVersion)
	{
		Resources r = this.myContext.getResources();

		String sqlTextFile = DATABASE_RAW_NAME_PREFIX + dbVersion;
		int resourceId = r.getIdentifier(sqlTextFile, "raw", this.myContext.getPackageName());

		if (resourceId == 0)
		{
			throw new SQLiteException("Failed to find raw resource SQL File : " + sqlTextFile);
		}

		String info = String.format(G.getLocale(), "Executing Sql from raw resource : %s, Version : %d, Resource Id : %d", sqlTextFile,
			dbVersion, resourceId);
		Log.i(TAG, info);

		SqlSplitter ss = new SqlSplitter(this.myContext, resourceId);

		for (String sql : ss)
		{
			Log.i(TAG, "Executing SQL : \n" + sql);
			db.execSQL(sql);
		}
	}	
	
	/**
	 * Gets the database name
	 * 
	 * @return Database name
	 */
	public String getDbName()
	{
		return this.myDatabaseName;
	}
	
	
}
