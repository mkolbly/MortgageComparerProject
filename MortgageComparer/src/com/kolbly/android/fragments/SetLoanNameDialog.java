package com.kolbly.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.kolbly.android.graphics.GraphicsUtil;
import com.kolbly.mortgagecomparer.R;
import com.squareup.otto.Bus;

/**
 * Dialog fragment where user can input a loan name
 * <p>
 * Note: This dialog uses the Bus to post the result - listeners must listen for
 * a SetLoanNameDialogEvent event to get the result
 * 
 * @author mkolbly
 */
public class SetLoanNameDialog extends DialogFragment implements DialogInterface.OnClickListener
{
	private static final String KEY_LOAN_NAME = "KEY_LOAN_NAME";
	private static final String KEY_LOAN_ID = "KEY_LOAN_ID";

	private EditText myLoanNameEditText;
	
	/**
	 * Static ctor for creating this dialog
	 * 
	 * @param loanId Database id for loan to update with new name
	 * @param loanName Initial loan name to populate dialog with
	 * @return SetLoanNameDialog
	 */
	public static SetLoanNameDialog create(int loanId, String loanName)
	{
		SetLoanNameDialog dlg = new SetLoanNameDialog();

		Bundle args = new Bundle();
		args.putString(KEY_LOAN_NAME, loanName);
		args.putInt(KEY_LOAN_ID, loanId);
		dlg.setArguments(args);

		return dlg;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();	
		View view = inflater.inflate(R.layout.fragment_set_loan_name, null);
		b.setView(view);
		b.setMessage(R.string.Set_Loan_Name);
		
		// OK Button
		b.setPositiveButton(R.string.OK, this);
	
		// Cancel Button
		b.setNegativeButton(R.string.Cancel, this);
				
		// Create the AlertDialog object 
		AlertDialog dlg = b.create();
		
		// Set the initial loan name
		String loanName = this.getArguments().getString(KEY_LOAN_NAME);
		this.myLoanNameEditText = (EditText) view.findViewById(R.id.loan_name_edittext);
		this.myLoanNameEditText.setText(loanName);

		Window window = dlg.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.gravity = Gravity.TOP;
		window.setAttributes(lp);
		
		return dlg;
	}

	/**
	 * Empty Ctor required for DialogFragment - pass arguments via Bundle
	 */
	public SetLoanNameDialog()
	{
		// Empty Ctor for Fragment
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		switch (which)
		{
			case DialogInterface.BUTTON_POSITIVE:
				int loanId = this.getArguments().getInt(KEY_LOAN_ID);
				String newLoanName = this.myLoanNameEditText.getText().toString();
				
				// Post this event to the Bus for any listeners to get
				Bus.postEvent(new SetLoanNameDialogEvent(loanId, newLoanName));
				
				GraphicsUtil.hideKeyboard(myLoanNameEditText);
				break;
				
			case DialogInterface.BUTTON_NEGATIVE:
				// Do nothing
				GraphicsUtil.hideKeyboard(myLoanNameEditText);
				break;
				
			default:
				// Do nothing
				break;
		}
	}

}
