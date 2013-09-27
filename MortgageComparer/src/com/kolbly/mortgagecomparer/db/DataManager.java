package com.kolbly.mortgagecomparer.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;

/**
 * Application's data manager
 * <p>
 * A singleton instance to be used for all database access
 * 
 * @author mkolbly
 */
public final class DataManager
{
	private static final String TAG = ClassUtil.getShortName(DataManager.class);
	
	/** Database open helper */
	private MyDbOpenHelper myDbOpenHelper = null; 
	
	/** Our database */
	private SQLiteDatabase myDb = null;
	
	/** Application context */
	private Context myContext = null;
	
	/**
	 * Our DataManager singleton instance creator / holder 
	 */
	private static class DataManagerHolder
	{
		private static final DataManager INSTANCE = DataManager.create(G.getAppContext());
	}
	
	/**
	 * Our application singleton
	 * 
	 * @return Our singleton
	 */
	public static DataManager instance()
	{
		return DataManagerHolder.INSTANCE;
	}
	
	/**
	 * Create a DataManager with the given context
	 * 
	 * @see DataManager#DataManager(Context)
	 * 
	 * @param context Application context
	 * @return newly minted DataManager (non-singleton) 
	 */
	public static DataManager create(Context context)
	{
		return new DataManager(context); 
	}
	
	/**
	 * Private ctor - prevent default instantiation
	 */
	private DataManager() {}
	
	/**
	 * Private constructor
	 * 
	 * @param context Application context
	 */
	private DataManager(Context context)
	{
		this.myContext = context;
		this.myDbOpenHelper = new MyDbOpenHelper(context);
	}
	
	
	/**
	 * Private Convenience constructor for testing or just more control
	 * 
	 * @param context to use to open or create the database
	 * @param name of the database file, or null for an in-memory database
	 * @param factory to use for creating cursor objects, or null for the default
	 * @param version number of the database (starting at 1) 
	 */
	private DataManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		this.myContext = context;
		this.myDbOpenHelper = new MyDbOpenHelper(context, name, factory, version);
	}
	
	/**
	 * Create a DataManager 
	 * <p>
	 * Useful for unit testing
	 * 
	 * @see DataManager#DataManager(Context, String, android.database.sqlite.SQLiteDatabase.CursorFactory, int)
	 * 
	 * @param context to use to open or create the database
	 * @param name of the database file, or null for an in-memory database
	 * @param factory to use for creating cursor objects, or null for the default
	 * @param version number of the database (starting at 1)
	 * @return newly minted DataManager (non-singleton) 
	 */
	public static DataManager create(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		return new DataManager(context, name, factory, version);
	}
	
	/**
	 * Get the database name
	 * <p>
	 * Note: We're not using android.database.sqlite.getDatabaseName() since it
	 * only was available starting at API level 14
	 * 
	 * @see MyDbOpenHelper#getDbName()
	 */
	public String getDbName()
	{
		return this.myDbOpenHelper.getDbName();	
	}
	
	/**
	 * Get our writable database 
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#getWritableDatabase()
	 * 
	 * @return Writable database
	 */
	public SQLiteDatabase getDb()
	{
		if (this.myDb == null)
		{
			this.myDb = this.myDbOpenHelper.getWritableDatabase();
		}
		
		return this.myDb;
	}
	
	
	/**
	 * Retrieve the complete contents of the database data ApplicationError
	 * 
	 * @return List of All ApplicationError
	 */
	public List<ApplicationError> getAllApplicationError()
	{
		List<ApplicationError> list = new ArrayList<ApplicationError>();
		
		SQLiteDatabase db = this.getDb();
		
		String table = ApplicationError.TABLENAME; 
		String orderBy = ApplicationError.IDENTITY;
		Cursor c = db.query(table, null, null, null, null, null, orderBy);

		while (c.moveToNext())
		{
			ApplicationError ae = new ApplicationError(c);
			list.add(ae);
		}
		c.close();
		
		return list;		
	}

	
	/**
	 * Get all Loans from the database
	 * 
	 * @return List of all loans
	 */
	public List<Loan> getAllLoan()
	{
		List<Loan> list = new ArrayList<Loan>();
		
		SQLiteDatabase db = this.getDb();
		
		String table = Loan.TABLENAME; 
		String orderBy = Loan.IDENTITY;
		Cursor c = db.query(table, null, null, null, null, null, orderBy);
		
		while (c.moveToNext())
		{
			Loan l = new Loan(c);
			list.add(l);
		}
		c.close();

		return list;
	}
	
	
	/**
	 * Get the loan specified by the given database identifier 
	 * 
	 * @param id Database identifier
	 * @return Loan from database or null if not found
	 */
	public Loan getLoan(long id)
	{
		Loan loan = null;
	
		SQLiteDatabase db = this.getDb();
		
		String selection = Loan.IDENTITY + "=" + id;
		
		Cursor c = db.query(Loan.TABLENAME, null, selection, null, null, null, null);
		if (c.moveToFirst())
		{
			loan = new Loan(c);
		}
		else
		{
			Log.w(TAG, "Failed to retrieve loan for id : " + id);
		}
		
		c.close();
		
		return loan;
	}
	
	/**
	 * Get the first user in the database
	 * <p>
	 * For this version, there will be only 1 user so we'll always operate on 
	 * the first user in the User table
	 * 
	 * @return User from database or null if not found
	 */
	public User getUser()
	{
		User user = null;
		
		SQLiteDatabase db = this.getDb();
		
		Cursor c = db.query(User.TABLENAME, null, null, null, null, null, null);

		if (c.moveToFirst())
		{
			user = new User(c);
		}
		
		c.close();
	
		// If user not found in database, then create one
		if (user == null)
		{
			user = User.createDefaultUser();
			this.save(user);
		}
		
		return user;
	}
	
	
		
	/**
	 * Save the data to the database table
	 * <p>
	 * If the id == 0, then inserts a new row of data and then updates the
	 * data's id to the newly inserted identity value
	 * <p>
	 * If the id > 0, then updates the existing data in the database
	 * 
	 * @param data Database data to save
	 * @return true on success
	 */
	public boolean save(DbRow data)
	{		
		if (data.getId() == 0)
		{
			return this.insert(data);
		}
		else if (data.getId() > 0)
		{
			return this.update(data);
		}
		else
		{
			throw new IllegalArgumentException("Invalid data object : id = " + data.getId());		// NOSONAR
		}
	}
	
	
	/**
	 * Delete a single row in the database
	 * 
	 * @param data Database data to update
	 * @return true on success
	 */
	public boolean delete(DbRow data)
	{
		// Our identity needs to be > 0 for updating
		if (data.getId() < 1)
		{
			throw new IllegalArgumentException("Invalid data object : id = " + data.getId());
		}
		
		int id = data.getId();
		String table = null;
		String whereClause = null;
		int rowsAffected = 0;
		SQLiteDatabase db = this.getDb();
		
		db.beginTransaction();	
		try
		{
			table = data.getTableName(); 
			whereClause = String.format(G.getLocale(), "%s=%d", data.getIdentityCol(), id);
			
			rowsAffected = db.delete(table, whereClause, null);
						
			db.setTransactionSuccessful();
		}
		catch (SQLiteException ex)
		{
			String err = String.format(G.getLocale(), "delete() failed : table = %s : whereClause = %s : data = %n%s", 
				table, whereClause, data.toXml(1));	
			Log.e(TAG, err, ex);
			throw ex;
		}
		finally
		{
			db.endTransaction();
		}
		
		// 1 and only 1 row should have updated via this function
		return (rowsAffected == 1);
	}
	
	/**
	 * Update an existing row of data in the database
	 * 
	 * @param data Database data to update
	 * @return true on success
	 */
	public boolean update(DbRow data)
	{
		// Our identity needs to be > 0 for updating
		if (data.getId() < 1)
		{
			throw new IllegalArgumentException("Invalid data object : id = " + data.getId());
		}
		
		String table = null;
		String whereClause = null;
		int rowsAffected = 0;
		SQLiteDatabase db = this.getDb();
		
		db.beginTransaction();	
		try
		{	
			ContentValues values = data.getContentValues();
			table = data.getTableName(); 
			whereClause = DbRow.IDENTITY + "=" + data.getId();

			rowsAffected = db.update(table, values, whereClause, null);
						
			db.setTransactionSuccessful();
		}
		catch (SQLiteException ex)
		{
			String err = String.format("update() failed : table = %s : whereClause = %s : data = %n%s", 
				table, whereClause, data.toXml(1));	
			Log.e(TAG, err, ex);
			throw ex;
		}
		finally
		{
			db.endTransaction();
		}
		
		// 1 and only 1 row should have updated via this function
		return (rowsAffected == 1);
	}
	
	
	/**
	 * Insert a new row of data in the database
	 * <p>
	 * Side Effect: Updates the id of data with the newly inserted row identity
	 * 
	 * @param data Database data to insert
	 * @return true on success
	 */
	public boolean insert(DbRow data) 
	{
		String table = null;
		
		// Our id needs to be 0 for new data insertion
		if (data.getId() != 0)
		{
			throw new IllegalArgumentException("Invalid data object (id != 0) : id = " + data.getId());
		}
		
		long rowId = -1;
		SQLiteDatabase db = this.getDb();
		
		db.beginTransaction();	
		try
		{	
			ContentValues values = data.getContentValues();
			// On insert, make sure identity column is not given
			values.remove(data.getIdentityCol());
					
			table = data.getTableName(); 
			rowId = db.insertOrThrow(table,  null,  values);
	
			db.setTransactionSuccessful();
		}
		catch (SQLiteException ex)
		{
			String err = String.format("insert() failed : table = %s : data = %n%s", 
				table, data.toXml(1));	
			Log.e(TAG, err, ex);
			throw ex;
		}
		finally
		{
			db.endTransaction();
		}

		
		if (rowId != -1)
		{
			data.setId(rowId);
		}
		
		return (rowId != -1);
	}

	
	/**
	 * Close our database
	 * 
	 * @see android.database.sqlite.SQLiteDatabase#close()
	 */
	public void close()
	{
		if ((this.myDb != null) && (this.myDb.isOpen()))
		{
			this.myDb.close();
		}
		
		this.myDb = null;
	}
	
	/**
	 * Delete our application's database completely
	 * 
	 * @return true on success
	 */
	public boolean deleteDatabase()
	{
		this.close();
		
		String dbName = this.getDbName();
		
		return this.myContext.deleteDatabase(dbName);
	}
	
}
