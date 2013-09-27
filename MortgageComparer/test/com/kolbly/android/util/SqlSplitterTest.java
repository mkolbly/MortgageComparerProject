package com.kolbly.android.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;
import com.kolbly.android.util.SqlSplitter;

import android.content.Context;


/**
 * Note: To access Resources from the JUnit test project, extend
 * ActivityTestCase, then use: Resources res =
 * this.getInstrumentation().getContext().getResources();
 * 
 * To access Resources from the Android project, extend AndroidTestCase, then
 * use: Resources res = this.getContext().getResources();
 * 
 * @author mkolbly
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class SqlSplitterTest extends MCTest
{
	public SqlSplitterTest() throws Exception
	{
		super();
	}

	private Context myContext = Robolectric.getShadowApplication().getApplicationContext();

	
	@Test(expected=IllegalArgumentException.class)
	public void testNullContext()
	{
		new SqlSplitter(null, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalResourceId()
	{
		new SqlSplitter(myContext, 0);
	}
	
	/**
	 * Test the SqlSplitter iterable interface
	 */
	@Test
	public void testSqlSplitter()
	{
		int resourceId = com.kolbly.mortgagecomparer.test.R.raw.test_sql_splitter_1;
		SqlSplitter s = new SqlSplitter(myContext, resourceId);

		ArrayList<String> results = new ArrayList<String>();
		for (String sql : s)
		{
			results.add(sql);
		}

		String sql1 = "CREATE TABLE IF NOT EXISTS [Foo1] (\r\n" +
			           "   _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
					     "   Version INTEGER NOT NULL\r\n" + 
			           ")";

		String sql2 = "CREATE TABLE IF NOT EXISTS [Contacts]\r\n" + 
		              "(\r\n" + 
					     "   _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + 
		              "   Id TEXT\r\n" + 
					     ")";

		assertTrue(results.size() == 2);
		
		assertEquals(sql1, results.get(0));
		assertEquals(sql2, results.get(1));
		
		// Call iterator a 2nd time so that all iterator branches get covered
		for (String sql : s)
		{
			assertTrue(sql.length() > 0);
		}
		
	}

	/**
	 * Test SqlSplitter.splitSqlStatements()
	 */
	@Test
	public void testSplitSqlStatements() throws IOException
	{
		String sql = "select * from foo;\n" + "select * from foo2;\n" + "select * from foo3;\n";

		List<String> sqlArray = SqlSplitter.splitSqlStatements(sql);

		assertEquals("select * from foo", sqlArray.get(0));
		assertEquals("select * from foo2", sqlArray.get(1));
		assertEquals("select * from foo3", sqlArray.get(2));

		sql = "select * from foo ; \n select * from foo2; ";
		sqlArray = SqlSplitter.splitSqlStatements(sql);

		assertEquals("select * from foo ", sqlArray.get(0));
		assertEquals(" select * from foo2; ", sqlArray.get(1));
	}

	@Test
	public void testSplitNullSql()
	{
		List<String> sqlArray = SqlSplitter.splitSqlStatements(null);
		
		assertEquals(0, sqlArray.size());
	}
	
	@Test
	public void testSplitEmptySql()
	{
		List<String> sqlArray = SqlSplitter.splitSqlStatements("");
		
		assertEquals(0, sqlArray.size());	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSplitSqlStatementsNullContext()
	{
		SqlSplitter.splitSqlStatements(null,  1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSplitSqlStatementsInvalidResourceId()
	{
		SqlSplitter.splitSqlStatements(this.myContext,  0);
	}
	
	@Test
	public void testInvalidResource()
	{
		int invalidResourceId = 20000;
		ArrayList<String> a = (ArrayList<String>) SqlSplitter.splitSqlStatements(this.myContext, invalidResourceId);
		
		assertEquals(0, a.size());
	}
	
	@Test
	public void testEmptyResourceFile()
	{
		int resourceId = com.kolbly.mortgagecomparer.test.R.raw.test_empty_text_file;
		
		ArrayList<String> a = (ArrayList<String>) SqlSplitter.splitSqlStatements(this.myContext, resourceId);
	
		assertEquals(0, a.size());
	}
	

	
	
}
