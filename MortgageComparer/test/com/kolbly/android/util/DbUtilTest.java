package com.kolbly.android.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;

/**
 *
 * TODO: Figure out a better way to do SQLiteDatabase testing - Robolectric
 * has too many known bugs and limited test support there. Try Android's
 * built-in framework or another framework. 
 * 
 * @Config(manifest="../MortgageComparer/AndroidManifest.xml") - uses manifest in 
 * 	MortgageComparer project.  Side effect is that any resources are also loaded
 * 	from there so any test resources are not available
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest="../MortgageComparer/AndroidManifest.xml")
public class DbUtilTest extends MCTest
{
	public DbUtilTest() throws Exception
	{
		super();
	}


	private static final String TAG = ClassUtil.getShortName(DbUtilTest.class);
	
	@Test
	public void testGetAllTablenames() throws InterruptedException
	{	
		ClassUtil.callPrivateConstructor(DbUtil.class);
		
		SQLiteDatabase db = dm.getDb();
		
		List<String> tableNames = DbUtil.getAllTablenames(db);
		
		for (String table : tableNames)
		{
			Log.d(TAG, "Found table : " + table);
		}
				
		assertTrue(tableNames.contains("Loan"));
		assertTrue(tableNames.contains("User"));
		assertTrue(tableNames.contains("ApplicationError"));
		
	}
	
	
	/**
	 * We cannot test this due to shitty support of Android's built-in
	 * SQLite database in Robolectric.  We always get a "database is locked"
	 * SQLException for this test which does not happen on a real database
	 * <p>
	 * TODO: Build in a Shadow Database dropin for our tests that uses a real
	 * SQLite database that we can use in ALL of our unit tests
	 */
	@Test(expected=java.lang.RuntimeException.class)
	public void deleteAllTables()
	{
		SQLiteDatabase db = dm.getDb();
		
		DbUtil.deleteAllTables(db);
	
		List<String> tableNames = DbUtil.getAllTablenames(db);
		
		assertFalse(tableNames.contains("Loan"));
		assertFalse(tableNames.contains("User"));
		assertFalse(tableNames.contains("ApplicationError"));
	}
	
	
	
	
}
