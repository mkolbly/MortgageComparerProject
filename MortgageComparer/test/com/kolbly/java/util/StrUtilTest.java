package com.kolbly.java.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.ContentValues;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.StrUtil;

@RunWith(RobolectricTestRunner.class)
public class StrUtilTest extends MCTest
{
	public StrUtilTest() throws Exception
	{
		super();
	}

		
	
	@Test
	public void testToXml()
	{
		ContentValues cv = new ContentValues();
		cv.put("key1", "value1"); 
		cv.put("key2", 5);

		String buf = StrUtil.toXml(cv);
		
		assertTrue(buf.startsWith("\r\n<ContentValues>\r\n"));
		assertTrue(buf.contains("<key2>5</key2>"));
		assertTrue(buf.contains("<key1>value1</key1>"));
		assertTrue(buf.endsWith("\r\n</ContentValues>\r\n"));
	
	}
	
	@Test 
	public void testNullToXml()
	{
		String buf = StrUtil.toXml(null);
		assertTrue(buf.startsWith("java.lang.NullPointerException"));			
	}

	/**
	 * Tests for StringUtil.stripBlankLines() 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testStripBlankLines() throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		String sql = "\r\n" +
				   "select * from foo\n" +
					"\n" +
					"\r\n";

		String strippedSql = StrUtil.stripBlankLines(sql);
		assertEquals("select * from foo\n", strippedSql);
		
		String sql2 = "select * from foo\r\n";
		String strippedSql2 = StrUtil.stripBlankLines(sql2);
		assertEquals("select * from foo\r\n", strippedSql2);
		
		assertEquals(null, StrUtil.stripBlankLines(null));
		assertEquals("", StrUtil.stripBlankLines(""));
		
		ClassUtil.callPrivateConstructor(StrUtil.class);
				
	}
	
	
	/**
	 * Tests for StringUtil.isNullOrEmpty()
	 */
	@Test
	public void testIsNullOrEmpty()
	{
		assertTrue(StrUtil.isNullOrEmpty(null));
		assertTrue(StrUtil.isNullOrEmpty(""));
		
		assertFalse(StrUtil.isNullOrEmpty("a"));
		assertFalse(StrUtil.isNullOrEmpty("\n"));
		assertFalse(StrUtil.isNullOrEmpty("\r\n"));
	}

	/**
	 * Tests for StringUtil.hasContent()
	 */
	@Test
	public void testHasContent()
	{
		assertTrue(StrUtil.hasContent("a"));
		assertTrue(StrUtil.hasContent("\n"));
		assertTrue(StrUtil.hasContent("\r\n"));
		
		assertFalse(StrUtil.hasContent(""));
		assertFalse(StrUtil.hasContent(null));
	}
	

}
