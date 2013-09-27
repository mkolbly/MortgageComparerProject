package com.kolbly.java.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.SqlUtil;

@RunWith(RobolectricTestRunner.class)
public class SqlUtilTest extends MCTest
{
	public SqlUtilTest() throws Exception
	{
		super();
	}

	/**
	 * Tests SqlUtil.stripSingleLineComments()
	 */
	@Test
	public void testStripSingleLineComments()
	{
		String sql = "----------------------------------\n" + 
			"-- Some comments\n" + 
			"--------------------------------\r\n" +
		   "select * from foo\n" +
			"\n" +
		   "-- comment1\r\n" +
			"-- comment2\n";

		String strippedSql = SqlUtil.stripSingleLineComments(sql);

		assertEquals("select * from foo\n\n", strippedSql);
	}

	@Test
	public void testStripEmptySingleLineComments()
	{	
		assertEquals("", SqlUtil.stripSingleLineComments(""));
	}
	
	@Test
	public void testStripNullSingleLineComments()
	{	
		assertEquals(null, SqlUtil.stripSingleLineComments(null));
	}
	
	@Test
	public void testCtor()
	{
		assertTrue(ClassUtil.callPrivateConstructor(SqlUtil.class));
	}
	
	
	
}
