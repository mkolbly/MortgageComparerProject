package com.kolbly.java.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.kolbly.android.test.MCTest;

@RunWith(RobolectricTestRunner.class)
public class ClassUtilTest extends MCTest
{
	@SuppressWarnings("unused")
	private static final String TAG = ClassUtil.getShortName(ClassUtilTest.class);		
	
	public ClassUtilTest() throws Exception
	{
		super();
	}

	/**
	 * Test for ClassUtil class
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testGetShortName() throws ClassNotFoundException
	{
		ClassUtil.callPrivateConstructor(ClassUtil.class);
		
		assertEquals(ClassUtil.getShortName(this.getClass()), "ClassUtilTest");
		
		assertEquals(ClassUtil.getShortName(null), "null");
	}

	@Test
	public void testGetShortNameForDefaultPackageClass() throws ClassNotFoundException
	{
		Class<?> cls = Class.forName("DefaultPackageTestClass");
		
		String shortName = ClassUtil.getShortName(cls);
		
		assertEquals("DefaultPackageTestClass", shortName);
	}
	
	
	@Test
	public void testCtor()
	{
		// ClassUtil has a private constructor, this should succeed
		assertTrue(ClassUtil.callPrivateConstructor(ClassUtil.class));
		
		assertFalse(ClassUtil.callPrivateConstructor(null));
		
		// Should fail as TestClass has a public constructor
		assertFalse(ClassUtil.callPrivateConstructor(TestClass.class));

	}

	@Test
	public void testFieldNames() throws IllegalArgumentException, IllegalAccessException
	{
		TestClass tc = new TestClass();
		
		String[] fieldNames = ClassUtil.getAllFieldNames(tc);
		List<String> fieldNamesList = Arrays.asList(fieldNames);
						
		assertTrue(fieldNamesList.contains("myName"));
		assertTrue(fieldNamesList.contains("myAge"));
	}
	
	@Test 
	public void testGetPrivateField() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
	{
		TestClass tc = new TestClass();
		tc.setName("John");
		
		String name = (String)ClassUtil.getPrivateField(tc, "myName"); 
		
		assertEquals("John", name);
		
	}
	
	@Test
	public void testPrintAllFields() throws IllegalArgumentException, IllegalAccessException
	{
		TestClass tc = new TestClass();
		
		tc.setName("John");
		tc.myAge = 5;
		
		ClassUtil.printAllFields(tc);
	}
	
	@Test
	public void testCallPrivateMethod() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		TestClass tc = new TestClass();
		
		Integer intVal = (Integer)ClassUtil.callPrivateMethod(tc, "getInt");
		
		assertEquals((Integer)55, intVal);
	}
	
	@Test
	public void testCallPrivateMethodWithParameter() throws IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		TestClass tc = new TestClass();
		
		int inputVal = 23;
		
		assertEquals(24, ClassUtil.callPrivateMethod(tc, "getInt", inputVal));
	}
	
	
	@Test
	public void testSetPrivateField() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
	{
		TestClass tc = new TestClass();
		
		ClassUtil.setPrivateField(tc, "myAge", 101);
		
		assertEquals(101, tc.getAge());
		
	}
	
}
 
class TestClass
{
	@SuppressWarnings("unused")
	private String myName;
	
	public int myAge;
		
	public void setName(String name)
	{
		this.myName = name;
	}
	
	public TestClass() {}
	
	@SuppressWarnings("unused")
	private int getInt()
	{
		return 55;
	}
	
	@SuppressWarnings("unused")
	private int getInt(int input)
	{
		return input + 1;
	}
	
	@SuppressWarnings("unused")
	private int getInt(int input, long moreInput)
	{
		return input + (int)moreInput;
	}
	
	public int getAge()
	{
		return myAge;
	}

}




