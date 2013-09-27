package com.kolbly.android.graphics;

import android.content.Context;
import android.graphics.Paint;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ToggleButton;

/**
 * Graphics utility class
 * 
 * @author mkolbly
 */
public final class GraphicsUtil
{
	/**
	 * Private ctor for utility class
	 */
	private GraphicsUtil(){}
	
	/**
	 * Hide the soft keyboard
	 * 
	 * @param view Current View being edited by keyboard
	 */
	public static void hideKeyboard(View view)
	{
		Context context = view.getContext();	
		IBinder windowToken = view.getWindowToken();
		
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);			
		imm.hideSoftInputFromWindow(windowToken, 0);
	}
	
	
	/**
	 * Show the soft keyboard
	 * 
	 * @param view Current View being edited by keybaord
	 */
	public static void showKeybaord(View view)
	{
		Context context = view.getContext();	
		
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);	
		imm.showSoftInput(view, 0);		
	}
	
	/**
	 * Has the user finished editing some field
	 * 
	 * @param actionId Identifier of the action. This will be either the
	 *           identifier you supplied, or EditorInfo.IME_NULL if being called
	 *           due to the enter key being pressed.
	 * @param event If triggered by an enter key, this is the event; otherwise,
	 *           this is null.
	 * @return true if user is done editing
	 */
	public static boolean isUserDoneEditing(int actionId, KeyEvent event)
	{
		switch (actionId)
		{
			case EditorInfo.IME_ACTION_SEARCH:
			case EditorInfo.IME_ACTION_DONE:
				return true;
		}

		if (event != null)
		{
			int eventAction = event.getAction();
			int eventKeyCode = event.getKeyCode();

			if ( (eventAction == KeyEvent.ACTION_DOWN) && 
				  (eventKeyCode == KeyEvent.KEYCODE_ENTER) &&
				  (!event.isShiftPressed())
				)
			{
				return true;
			}
		}

		return false;
	}
	
	
	/**
	 * Sets the ToggleButton width to the largest of the the on/off states text
	 * 
	 * @param view View containing toggle button
	 * @param args One or more ToggleButton ID's to freeze width of
	 */
	public static void freezeToggleButtonWidth(View view, int...args)
	{
		for (int id : args)
		{
			ToggleButton tb = (ToggleButton) view.findViewById(id);
		
			GraphicsUtil.freezeToggleButtonWidth(tb);
		}
	}
	
	
	/**
	 * Sets the ToggleButton width to the largest of the the on/off states text
	 * 
	 * @param args One or more ToggleButton to freeze width of
	 */
	public static void freezeToggleButtonWidth(ToggleButton... args)
	{
		for (ToggleButton tb : args)
		{
			String onText = tb.getTextOn().toString();
			String offText = tb.getTextOff().toString();
			
			Paint p = tb.getPaint();
			
			int onSize = (int)p.measureText(onText) + tb.getPaddingLeft() + tb.getPaddingRight();
			int offSize = (int)p.measureText(offText) + tb.getPaddingLeft() + tb.getPaddingRight();
			
			tb.setWidth((onSize > offSize) ? onSize : offSize);
		}
	}
	
}
