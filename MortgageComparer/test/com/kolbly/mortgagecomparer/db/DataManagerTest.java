package com.kolbly.mortgagecomparer.db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.ContentValues;

import com.kolbly.android.test.MCTest;
import com.kolbly.java.util.ClassUtil;


/**
 * Notes:
 * <p>
 * 
 * @Config(manifest="../MortgageComparer/AndroidManifest.xml") - uses manifest in 
 * 	MortgageComparer project.  Side effect is that any resources are also loaded
 * 	from there so any test resources are not available
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest="../MortgageComparer/AndroidManifest.xml")
public class DataManagerTest extends MCTest
{
	public DataManagerTest() throws Exception
	{
		super();
	}

	@Before
	public void setUp() throws Exception
	{

	}
	
	@After
	public void tearDown() throws Exception
	{
//		db.close();
		// context.deleteDatabase("test.db");		
	}
	
	
	@Test
	public void test() throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException
	{			
		ApplicationError ue1 = new ApplicationError(0, "Message 1", "Stacktrace 1", "Thread 1", 501, 1, 300);
		ApplicationError ue2 = new ApplicationError(0, "Message 2", "Stacktrace 2", "Thread 2", 502, 2, 302);
		ApplicationError ue3 = new ApplicationError(0, "Message 3", "Stacktrace 3", "Thread 3", 503, 3, 303);

		dm.insert(ue1);
		dm.insert(ue2);
		dm.insert(ue3);
		
		List<ApplicationError> ueList = dm.getAllApplicationError();
		
		ApplicationError ae = ueList.get(0);
		ClassUtil.printAllFields(ae);
		
		int id = ae.getId();
		String message = (String)ClassUtil.getPrivateField(ae, "message");
		String stackTrace = (String)ClassUtil.getPrivateField(ae, "stackTrace");
		String threadName = (String)ClassUtil.getPrivateField(ae, "threadName");
		long threadId = (Long)ClassUtil.getPrivateField(ae, "threadId");
		int threadPriority = (Integer)ClassUtil.getPrivateField(ae, "threadPriority");
		long timeStamp = (Long)ClassUtil.getPrivateField(ae, "timeStamp");
		
		assertEquals(1, id);
		assertEquals("Message 1", message);
		assertEquals("Stacktrace 1", stackTrace);
		assertEquals("Thread 1", threadName);
		assertEquals(501, threadId);
		assertEquals(1, threadPriority);
		assertEquals(300, timeStamp);
		
		
		ContentValues cv = ueList.get(1).getContentValues();
				
		assertEquals((Integer)2, cv.getAsInteger(DbRow.IDENTITY));
		
			
			


	}

}
