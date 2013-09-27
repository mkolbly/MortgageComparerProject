package com.kolbly.android.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.SqlUtil;
import com.kolbly.java.util.StrUtil;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Sql String Splitter class that can take a raw resource file containing 
 * multiple sql statements separated by colons and split them up for purposes
 * of executing them one at a time
 * 
 * @author mkolbly
 */
public class SqlSplitter implements Iterable<String>
{
	private static final String TAG = ClassUtil.getShortName(SqlSplitter.class); 
	
	private Context myContext = null;
	private int myResourceId = -1;
	private List<String> mySqlStatements = null;

	private static final Pattern SQL_SPLIT_PATTERN;
	private static final int BUF_SIZE = 1024;
	
	static
	{
		SQL_SPLIT_PATTERN = Pattern.compile("; (\\s)* [\\n\\r]", Pattern.COMMENTS | Pattern.MULTILINE);
	}

	/**
	 * Create an Sql splitter that can split a resource text file into separate
	 * Sql statements.
	 * <p>
	 * Note: This is a simple algorithm which assumes that Sql statements are
	 * separated by a semicolon and EOL. This is meant to be fast - not
	 * comprehensive.
	 * 
	 * @param context - Context to use for reading the resource
	 * @param resourceId - Resource ID of raw resource containing multiple Sql
	 *           statements separated by semicolons
	 */
	public SqlSplitter(Context context, int resourceId)
	{
		if (context == null)
		{
			throw new IllegalArgumentException("context is null");
		}

		if (resourceId <= 0)
		{
			throw new IllegalArgumentException("resourceId <= 0");
		}

		this.myContext = context;
		this.myResourceId = resourceId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<String> iterator()
	{
		if (this.mySqlStatements == null)
		{
			this.mySqlStatements = splitSqlStatements(this.myContext, this.myResourceId);
		}

		return mySqlStatements.iterator();
	}

	/**
	 * Split sql text into separate sql statements
	 * <p>
	 * sql statements need to be separated by a semicolon, then EOL
	 * 
	 * @param sql - String containing multiple sql statements separated by
	 *           semicolons
	 * @return - Array of separate sql statements, or empty Array if there was an
	 *         error
	 */
	public static List<String> splitSqlStatements(String sql)
	{
		if (StrUtil.isNullOrEmpty(sql))
		{
			Log.w(TAG, "Empty sql");
			return new ArrayList<String>();
		}

		String noCommentSql = SqlUtil.stripSingleLineComments(sql);
		String noBlankLinesSql = StrUtil.stripBlankLines(noCommentSql);

		String[] sqlList = SQL_SPLIT_PATTERN.split(noBlankLinesSql);

		ArrayList<String> sqlArray = new ArrayList<String>(Arrays.asList(sqlList));

		return sqlArray;
	}

	/**
	 * Take a raw text resource containing sql statements, and split the sql into
	 * individual elements
	 * 
	 * @param context - Context to use for reading the resource
	 * @param resourceId - Resource ID of raw resource containing multiple Sql
	 *           statements separated by semicolons
	 * @return - Array of separate sql statements, or empty Array if there was an
	 *         error
	 */
	public static List<String> splitSqlStatements(Context context, int resourceId)
	{
		if (context == null)
		{
			throw new IllegalArgumentException("context is null");
		}

		if (resourceId <= 0)
		{
			throw new IllegalArgumentException("resourceId <= 0");
		}
		
		String sql = null;
		try
		{
			sql = getRawResourceText(context, resourceId);
		}
		catch (Exception ex)
		{
			Log.e(TAG, "Failed to read resource", ex);
			return new ArrayList<String>();
		}

		if (StrUtil.hasContent(sql))
		{
			return SqlSplitter.splitSqlStatements(sql);
		}
		else
		{
			return new ArrayList<String>();
		}
	}

	/**
	 * Get the contents of a raw resource text file
	 * 
	 * @param context - Context of resource to be read
	 * @param resourceId - Resource ID of text file
	 * @return - String containing contents of resource text file or null on error
	 */
	private static String getRawResourceText(Context context, int resourceId) 
	{
		Resources res = context.getResources();
		String fileContents = null;

		InputStream in = res.openRawResource(resourceId);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(BUF_SIZE);
		byte[] buffer = new byte[BUF_SIZE];
		int length = 0;

		try
		{
			while ((length = in.read(buffer)) != -1)
			{
				baos.write(buffer, 0, length);
			}
		}
		catch (IOException ex)
		{
			fileContents = null;
			Log.e(TAG, ex.getMessage(), ex);
		}

		try
		{
			fileContents = baos.toString("UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			fileContents = null;
			Log.e(TAG, ex.getMessage(), ex);
		}
		
		return fileContents;
	}
	
}
