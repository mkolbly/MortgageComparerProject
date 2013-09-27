package com.kolbly.java.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * SQL Utility class
 * 
 * @author mkolbly
 */
public final class SqlUtil
{
	private static final String TAG = ClassUtil.getShortName(SqlUtil.class);

	/** Regular expression to find single line sql comments (-- ...) */
	private static final Pattern SQL_SINGLE_LINE_COMMENT_PATTERN = Pattern.compile("-- .*? \\r? \\n", Pattern.COMMENTS | Pattern.MULTILINE);

	/**
	 * Ctor - kept private for utility class 
	 */
	private SqlUtil()
	{
	}

	/**
	 * Strip out single-line sql comments from the given sql<br>
	 * (i.e. lines beginning with --)
	 * 
	 * @param sql - sql to remove comments from
	 * @return - sql minus single line comments
	 */
	public static String stripSingleLineComments(String sql)
	{
		if (sql == null)
		{
			Log.w(TAG, "sql == null");
			return null;
		}

		if (sql.length() == 0)
		{
			Log.w(TAG, "sql is empty");
			return "";
		}

		Matcher commentMatcher = SQL_SINGLE_LINE_COMMENT_PATTERN.matcher(sql);
		String buf = commentMatcher.replaceAll("");

		return buf;
	}
}
