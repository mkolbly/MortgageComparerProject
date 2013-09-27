package com.kolbly.android.graphics;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * This EditText extension has some utility methods as well as the ability to
 * determine when the user has finished editing this particular text.
 * <p>
 * A user is defined to be done editing the text when one of the following
 * occurs:
 * 
 * <li>The EditText view has lost focus
 * <li>The user has hit "Search" or "Done" on the keyboard
 * <li>The user has changed some text -AND- some period of time defined by
 * TEXT_EDIT_DELAY has passed
 * 
 * @see android.widget.EditText
 * 
 * @author mkolbly
 */
public class EditTextEx extends EditText implements TextWatcher, OnFocusChangeListener, OnEditorActionListener
{
	private Set<EditTextExListener> myObservers = new HashSet<EditTextExListener>();

	private Handler myHandler;

	/** How long to wait after a text change to notify observers of the change */
	private static final int TEXT_EDIT_DELAY = 7000;

	
	/**
	 * Notify all observers that the text has changed
	 */
	private Runnable myNotifyObserversRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			for (EditTextExListener observer : myObservers)
			{
				observer.onDoneEditing(EditTextEx.this);
			}
		}
	};

	
	/**
	 *  @see android.widget.EditText#EditText(Context)
	 */
	public EditTextEx(Context context)
	{
		super(context);

		this.init();
	}

	
	/**
	 * @see android.widget.EditText#EditText(Context, AttributeSet)
	 */
	public EditTextEx(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		this.init();
	}

	
	/**
	 * @see android.widget.EditText#EditText(Context, AttributeSet, int)
	 */
	public EditTextEx(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		this.init();
	}

	
	/**
	 * Initialize our object
	 */
	private void init()
	{
		this.myHandler = new Handler();

		// Watch for when user hits "Search, Done, or Enter" on the keyboard
		this.setOnEditorActionListener(this);

		// Watch for losing focus events
		this.setOnFocusChangeListener(this);

		// Watch for text changes
		this.addTextChangedListener(this);
	}

	
	/**
	 * Add this observer to our list of observers
	 * 
	 * @param observer Observer to add
	 * @return false if this observer was already in our list of observers, else
	 *         true
	 */
	public boolean addDoneEditingListener(EditTextExListener observer)
	{
		return this.myObservers.add(observer);
	}

	
	/**
	 * Remove this observer from our list of observers
	 * 
	 * @param observer Observer to remove
	 * @return true if the observer was removed, else false
	 */
	public boolean removeDoneEditingListener(EditTextExListener observer)
	{
		return this.myObservers.remove(observer);
	}

	
	/* (non-Javadoc)
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		// Do nothing
	}

	
	/* (non-Javadoc)
	 * @see android.widget.TextView#onTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// Do nothing
	}

	
	/**
	 * Our text watcher that only notifies of text changes after a delay given by
	 * TEXT_EDIT_DELAY milliseconds
	 * <p>
	 * Note the use of a handler instead of a Timer or TimerTask. Handlers
	 * perform much better on Android over timers.
	 * 
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable s)
	{
		myHandler.removeCallbacks(myNotifyObserversRunnable);
		myHandler.postDelayed(myNotifyObserversRunnable, TEXT_EDIT_DELAY);
	}

	
	/**
	 * If we've lost focus, notify the observers we're done editing
	 * 
	 * @see android.view.View.OnFocusChangeListener#onFocusChange(android.view.View,
	 *      boolean)
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		if (hasFocus)
		{
			// Do nothing, we still have focus and are possibly still editing
			return;
		}

		// Remove any delayed callbacks that the TextWatcher may have added
		myHandler.removeCallbacks(myNotifyObserversRunnable);

		// Notify observers
		myHandler.post(myNotifyObserversRunnable);
	}

	
	/* (non-Javadoc)
	 * @see android.widget.TextView.OnEditorActionListener#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
	{
		if (GraphicsUtil.isUserDoneEditing(actionId, event))
		{
			GraphicsUtil.hideKeyboard(view);
			
			// Remove any pending notification callbacks
			myHandler.removeCallbacks(myNotifyObserversRunnable);

			myHandler.post(myNotifyObserversRunnable);
		}

		// Return false here so OS can properly close the keyboard popup
		return false;
	}


	/**
	 * Get the double contained in this EditText box
	 * 
	 * @return double value or 0 if text is null or empty string
	 * @throws NumberFormatException if can't convert
	 */
	public double getDouble()
	{
		String buf = this.getText().toString();

		if ((buf == null) || buf.length() < 1)
		{
			return 0;
		}
		else
		{
			return Double.parseDouble(buf);
		}
	}
	
	
	/**
	 * Get the integer contained in this EditText box
	 * 
	 * @return integer value or 0 if text is null or empty string
	 * @throws NumberFormatException if can't convert
	 */
	public int getInteger()
	{
		String buf = this.getText().toString();
		if ((buf == null) || buf.length() < 1)
		{
			return 0;
		}
		else
		{
			return Integer.parseInt(buf);
		}	
	}

	
	/**
	 * Update the text without triggering any onTextChanged() events
	 * <p>
	 * Basically, removes the listener, then calls setText(CharSequence), then
	 * re-enables the listener
	 * 
	 * @see android.widget.TextView#setText(CharSequence) 
	 * 
	 * @param text Text to update widget with
	 */
	public final void setTextEx(CharSequence text)
	{
		// Temporarily remove the text change listener
		this.removeTextChangedListener(this);
		
		this.setText(text);
		
		// Add back in the text change listener
		this.addTextChangedListener(this);
	}
	
	
}
