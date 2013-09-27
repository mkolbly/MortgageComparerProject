package com.kolbly.java.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;  

import android.content.ContentValues;

/**
 * Generic string utilities
 * 
 * @author mkolbly
 */
public final class StrUtil
{
	/** Regular expression for rooting out blank lines */
	private static final Pattern BLANK_LINE_PATTERN;

	/** Number of spaces in a tab */
	private static final int TABSIZE = 3;
	
	/**
	 * Ctor - kept private for utility class
	 */
	private StrUtil()
	{
	}

	static
	{
		BLANK_LINE_PATTERN = Pattern.compile("^ [ \\t]* \\r? \\n", Pattern.COMMENTS | Pattern.MULTILINE);
	}

	/**
	 * Strip blank lines out of the given string
	 * 
	 * @param buf - String to remove blank lines from
	 * @return - Same string without the blank lines
	 */
	public static String stripBlankLines(String buf)
	{
		// We must have something to operate on
		if (buf == null)
		{
			return null;
		}

		// Nothing to do on an empty string
		if (buf.length() == 0)
		{
			return "";
		}

		Matcher blankMatcher = BLANK_LINE_PATTERN.matcher(buf);
		String stripped = blankMatcher.replaceAll("");

		return stripped;
	}

	/**
	 * Utility function to check if string is null or empty
	 * 
	 * @param str - string to check
	 * @return true if string is null or empty
	 */
	public static boolean isNullOrEmpty(String str)
	{
		return ((str == null) || (str.length() < 1));
	}

	/**
	 * Utility function to check if string has some value
	 * 
	 * @param str - string to check
	 * @return true if string is not null and length > 0
	 */
	public static boolean hasContent(String str)
	{
		return ((str != null) && (str.length() > 0));
	}

	/**
	 * Create a ContentValues xml string suitable for debug logging 
	 * 
	 * @param vals ContentValues to get xml for
	 * @return String representation
	 */
	public static String toXml(ContentValues vals)
	{
		return StrUtil.toXml(vals, 0);
	}

	/**
	 * Create a ContentValues xml string suitable for debug logging 
	 * 
	 * @param vals ContentValues to get xml for
	 * @param tab Tab spacing to use (1 tab == 3 spaces)
	 * @return String representation
	 */
	public static String toXml(ContentValues vals, int tab)
	{
		StringBuffer sb = new StringBuffer();
		
		try
		{
			Set<Entry<String, Object>> set = vals.valueSet();
			Iterator<Entry<String, Object>> itr = set.iterator();
				
			String s = Spaces.repeat(TABSIZE * tab);

			sb.append(String.format("%n%s<ContentValues>%n", s));

			while (itr.hasNext())
			{
				Map.Entry<String, Object> me = (Map.Entry<String, Object>) itr.next();
				String key = me.getKey().toString();
				Object value = me.getValue();

				sb.append(String.format("%s   <%s>%s</%s>%n", s, key, value, key));
			}

			sb.append(String.format("%s</ContentValues>%n", s));
		}
		catch (Exception ex)
		{
			sb = new StringBuffer(ExceptionUtils.getStackTrace(ex));
		}

		return (sb.toString());
	}

}
