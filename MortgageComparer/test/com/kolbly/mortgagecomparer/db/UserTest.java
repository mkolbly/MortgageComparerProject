package com.kolbly.mortgagecomparer.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.ContentValues;
import android.database.MatrixCursor;

import com.kolbly.android.test.MCTest;
import com.kolbly.android.util.date.DateFormat;
import com.kolbly.android.util.date.SystemDateFormat;
import com.kolbly.java.util.ClassUtil;

@RunWith(RobolectricTestRunner.class)
//@Config(shadows = {ShadowDateFormat.class})
public class UserTest extends MCTest
{
	public UserTest() throws Exception
	{
		super();
	}

	@After
	public void tearDown()
	{
		SystemDateFormat.instance().clear();
	}
	
	/**
	 * Robolectric throws a "Database locked" error whenever calling
	 * DataManager.getUser() or DataManager.save(User)
	 * <p>
	 * This is a known Robolectric bug
	 * <p>
	 * TODO: Upgrade Robolectric when next version comes out and try getting
	 * this test working - or employ some other methodology for testing database
	 * stuff
	 */
	@Test
	public void test()
	{
		final int year = 1966;
		final int month = Calendar.FEBRUARY;
		final int day = 19;
		Calendar birthDate = new GregorianCalendar(year, month, day);

		// This causes a "Database locked" error in robolectric
		// User u1 = dm.getUser();
		
		User u1 = new User("John", birthDate.getTime());

		assertEquals("John", u1.getUserName());
		assertEquals(birthDate.getTime(), u1.getBirthDate());
	
		
//		u1.setUserName("John");
//		u1.setBirthDate(birthDate.getTime());
//		
//		User u2 = this.dm.getUser();
//		
//		assertEquals(u1.getUserName(), u2.getUserName());
//		assertEquals(u1.getBirthDate(), u2.getBirthDate());
//		assertEquals(u1.getBirthDateString(), u2.getBirthDateString());
	}

	

	@Test
	public void testCreateDefaultUser() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
	{
		User u1 = User.createDefaultUser();
		
		
		String defaultUserName = (String)ClassUtil.getPrivateField(u1, "DEFAULT_USERNAME");
		
		Date defaultBirthDate = (Date)ClassUtil.getPrivateField(u1, "DEFAULT_BIRTH_DATE");
		
		
		assertEquals(defaultUserName, u1.getUserName());
		assertEquals(defaultBirthDate, u1.getBirthDate());
	}
	
	@Test
	public void testGetContentValues()
	{
		final int year = 1966;
		final int month = Calendar.FEBRUARY;
		final int day = 19;
		Calendar birthDate = new GregorianCalendar(year, month, day);
		Date bDate = birthDate.getTime();

		User u1 = new User("John", birthDate.getTime());
		
		
		ContentValues cv = u1.getContentValues();
		
		assertEquals((Integer)0, (Integer)cv.get(User.IDENTITY));
		assertEquals("John", (String)cv.get("userName"));
		assertEquals((Long)bDate.getTime(), (Long)cv.get("birthDate"));
	}
	
	@Test
	public void testSetters()
	{
		SystemDateFormat.instance().clear();

		SystemDateFormat.overrideSystemDateFormat(DateFormat.YYYYMMDD);
		
		Calendar birthDate = new GregorianCalendar(1966, Calendar.FEBRUARY, 19);
		
		User u1 = User.createDefaultUser();
		
		u1.setUserName("Tim");
		u1.setBirthDate(birthDate.getTime());
		
		assertEquals("Tim", u1.getUserName());
		
		assertTrue(User.hasBirthDate(u1));
		assertEquals("1966/02/19", u1.getBirthDateString());
	}
	
	
	@
	Test
	public void testHasBirthdate()
	{
		Date birthDate = new GregorianCalendar(1966, Calendar.FEBRUARY, 19).getTime();
		
		User u1 = User.createDefaultUser();
		u1.setUserName("Tom");
		u1.setBirthDate(birthDate);
		
		assertTrue(User.hasBirthDate(u1));
		
		u1.setBirthDate(null);
		assertFalse(User.hasBirthDate(u1));
		
		assertFalse(User.hasBirthDate(null));
			
	}
	
	
	@Test
	public void testCursorCtor()
	{
		Date birthDate = new GregorianCalendar(1966, Calendar.FEBRUARY, 19).getTime();
		
		MatrixCursor c = new MatrixCursor(new String[]{User.IDENTITY, "userName", "birthdate"});
		
		c.addRow(new Object[] {1, "Tom", birthDate.getTime()});
		
		c.moveToFirst();
		
		User u2 = new User(c);
	
		assertEquals("Tom", u2.getUserName());
		assertEquals(birthDate, u2.getBirthDate());
		assertEquals(1, u2.getId());
		
		assertEquals(User.TABLENAME, u2.getTableName());
		
	}
	
	
	
}
