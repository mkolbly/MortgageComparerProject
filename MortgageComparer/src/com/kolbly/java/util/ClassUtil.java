/*
 * ClassUtil.java
 * 
 * Avaya Inc. - Proprietary (Restricted) Solely for authorized persons having a
 * need to know pursuant to Company instructions.
 * 
 * Copyright 2013 Avaya Inc. All rights reserved. THIS IS UNPUBLISHED
 * PROPRIETARY SOURCE CODE OF Avaya Inc. The copyright notice above does not
 * evidence any actual or intended publication of such source code.
 */
package com.kolbly.java.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

/**
 * Class Utility Functions
 * 
 * @author mkolbly
 */
public final class ClassUtil
{
	private static final String TAG = ClassUtil.getShortName(ClassUtil.class);

	/**
	 * Ctor - kept private for utility class
	 */
	private ClassUtil()
	{
	}

	/**
	 * Get the short name of the given class
	 * 
	 * @param clazz : Class to return the short name for
	 * @return : short name of clazz
	 */
	public static String getShortName(Class<?> clazz)
	{
		if (clazz == null)
		{
			return "null";
		}
		else
		{
			String name = clazz.getName();

			if (name.lastIndexOf('.') > 0)
			{
				name = name.substring(name.lastIndexOf('.') + 1);
			}

			return name;
		}
	}

	/**
	 * Call private constructor using reflection
	 * <p>
	 * This is useful in code coverage tests
	 * 
	 * @param clazz - Class to instantiate
	 */
	public static boolean callPrivateConstructor(Class<?> clazz)
	{
		try
		{
			Constructor<?> c = clazz.getDeclaredConstructor();

			if (Modifier.isPrivate(c.getModifiers()))
			{
				c.setAccessible(true);
				c.newInstance();
			}
			else
			{
				Log.e(TAG, "Constructor is not private");
				return false;
			}
		}
		catch (Exception ex)
		{
			Log.e(TAG, ex.getMessage(), ex);
			return false;
		}

		return true;
	}

	/**
	 * Call a private method of a given class object - with no parameters
	 * 
	 * @param objectInstance Class object to invoke method on
	 * @param methodName Method name to invoke
	 * @param inputVal
	 * @return Return value from invoked method
	 * 
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object callPrivateMethod(Object objectInstance, String methodName) throws 
		IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException		// NOSONAR
	{
		Object[] params = null;

		Method method = objectInstance.getClass().getDeclaredMethod(methodName, (Class[]) null);

		method.setAccessible(true);

		return method.invoke(objectInstance, params);
	}

	/**
	 * Call a private method of a given class object
	 * 
	 * @param objectInstance Class object to invoke method on
	 * @param methodName Method name to invoke
	 * @param int1 int Parameter to the method
	 * @return Return value from invoked method
	 * 
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object callPrivateMethod(Object objectInstance, String methodName, int int1) 
		throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException  // NOSONAR
	{

		Method method = objectInstance.getClass().getDeclaredMethod(methodName, Integer.TYPE);

		method.setAccessible(true);

		return method.invoke(objectInstance, int1);
	}

	/**
	 * Get the private field data value of the given instance of a class
	 * 
	 * @param objectInstance Object to get private member data from
	 * @param fieldName Name of private member to get data from
	 * @return Private member's data
	 * 
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getPrivateField(Object objectInstance, String fieldName) 
		throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException		// NOSONAR
	{
		Class<? extends Object> clazz = objectInstance.getClass();

		Field field = clazz.getDeclaredField(fieldName);

		field.setAccessible(true);

		return field.get(objectInstance);
	}

	/**
	 * Set the private field data value of the given instance of a class
	 * 
	 * @param objectInstance Object to set private member data to
	 * @param fieldName Name of private member to set data for
	 * @param value Private member's data to set
	 * 
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setPrivateField(Object objectInstance, String fieldName, Object value) 
		throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException		// NOSONAR
	{
		Class<? extends Object> clazz = objectInstance.getClass();

		Field field = clazz.getDeclaredField(fieldName);

		field.setAccessible(true);

		field.set(objectInstance, value);
	}

	/**
	 * Get all field names for the given object - including private fields
	 * 
	 * @param objectInstance Object to get field names for
	 * @return String array of all field names
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String[] getAllFieldNames(Object objectInstance) 
		throws IllegalArgumentException, IllegalAccessException	// NOSONAR
	{
		Map<String, Object> map = ClassUtil.getAllFields(objectInstance);

		int size = map.size();

		String[] fieldNames = map.keySet().toArray(new String[size]);

		return fieldNames;

	}

	/**
	 * Get a Hashtable of all class member fields (including private) of the
	 * given object
	 * 
	 * @param objectInstance Object to get data from
	 * @return Hashtable of all values
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> getAllFields(Object objectInstance) throws IllegalArgumentException, IllegalAccessException	// NOSONAR
	{
		HashMap<String, Object> map = new HashMap<String, Object>();

		Class<? extends Object> clazz = objectInstance.getClass();

		Field fields[] = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			Field f = fields[i];
			f.setAccessible(true);

			String fieldName = f.getName();
			Object fieldValue = f.get(objectInstance);

			map.put(fieldName, fieldValue);
		}

		return map;
	}

	public static void printAllFields(Object objectInstance) throws IllegalArgumentException, IllegalAccessException  // NOSONAR
	{
		Class<? extends Object> clazz = objectInstance.getClass();

		Field fields[] = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			fields[i].setAccessible(true);

			String buf = String.format("Field Name: %s : %s", fields[i].getName(), fields[i].get(objectInstance));

			Log.d(TAG, buf);

		}

	}
}
