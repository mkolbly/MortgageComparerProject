//package com.kolbly.java.util;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//
///**
// * IO Utilities
// * 
// * @author mkolbly
// */
//public final class IOUtil
//{
//	/**
//	 * Ctor - kept private for utility class
//	 */
//	private IOUtil()
//	{
//	}
//
//	/**
//	 * Get the contents of an <code>InputStream</code> as a String using the
//	 * specified character encoding and buffer size.
//	 * 
//	 * @param is - input stream to read
//	 * @param bufSize - buffer size
//	 * @param encoding - string encoding type
//	 * @return - string read from input stream
//	 * @throws IOException
//	 */
//	public static String toString(final InputStream is, final int bufSize, final String encoding) throws IOException
//	{
//		final char[] buf = new char[bufSize];
//
//		final StringBuilder sb = new StringBuilder();
//
//		final Reader in = new InputStreamReader(is, encoding);
//
//		int charsRead;
//		
//		while ((charsRead = in.read(buf, 0, bufSize)) > -1)
//		{
//			sb.append(buf, 0, charsRead);
//		}
//		
//		return sb.toString();
//	}
//
//}
