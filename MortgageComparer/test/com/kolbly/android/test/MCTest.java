package com.kolbly.android.test;

import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowLog;

import android.content.Context;

import com.kolbly.global.G;
//import com.kolbly.global.S;
import com.kolbly.mortgagecomparer.db.DataManager;

/**
 * Convenience class for setting up Android testing specific to our project
 * <p>
 * Derive your test class from this and ensure the derived class calls the
 * Ctor of the base.  If not an exception is thrown letting you know of the
 * test setup issue.
 */
public class MCTest 
{
	/** Android context */
	protected Context context;
	
	/** Data manager */
	protected DataManager dm;
	
	/** Test class has been properly initialized flag  */
	private boolean initialized;
	
	
	/**
	 * Test Ctor - must be called by derived classes to ensure proper setup
	 * of testing objects
	 * 
	 * @throws Exception If failed to initialize
	 */
	public MCTest() throws Exception
	{
		initialized = false;
		
		init();
		
		if (! initialized)
		{
			throw new Exception("Failed to call MCTest() Ctor");
		}
	}
	
	/**
	 * Initialize our member variables as well as our Global G
	 */
	private void init()
	{
		// Send Log statements to stdout
		ShadowLog.stream = System.out;
		
		context = Robolectric.getShadowApplication().getApplicationContext();
		
		//S.init(context);
		G.init(context);
		
		dm = DataManager.create(context, "test.db", null, 1);
				
		initialized = true;
	}
	


}
