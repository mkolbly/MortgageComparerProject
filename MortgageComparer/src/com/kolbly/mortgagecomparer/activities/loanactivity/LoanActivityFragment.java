package com.kolbly.mortgagecomparer.activities.loanactivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.Period;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.kolbly.android.fragments.DateDialogFragment;
import com.kolbly.android.fragments.DateDialogFragmentListener;
import com.kolbly.android.fragments.SetLoanNameDialog;
import com.kolbly.android.fragments.SetLoanNameDialogEvent;
import com.kolbly.android.graphics.EditTextEx;
import com.kolbly.android.graphics.EditTextExListener;
import com.kolbly.android.graphics.RangeFilter;
import com.kolbly.global.G;
import com.kolbly.java.util.ClassUtil;
import com.kolbly.java.util.MathUtil;
import com.kolbly.mortgagecomparer.R;
import com.kolbly.mortgagecomparer.activities.amortizationactivity.AmortizationActivityListView;
import com.kolbly.mortgagecomparer.db.Amortization;
import com.kolbly.mortgagecomparer.db.DataManager;
import com.kolbly.mortgagecomparer.db.Loan;
import com.kolbly.mortgagecomparer.db.User;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Fragment for a single loan from the list
 * 
 * @author mkolbly
 */
public class LoanActivityFragment extends Fragment implements OnClickListener
{
	private static final String TAG = ClassUtil.getShortName(LoanActivityFragment.class);

	private static final int MIN_YEAR = 0;
	private static final int MAX_YEAR = 3000;
	private static final int MIN_MONTH = 0;
	private static final int MAX_MONTH = 11;

	// Loan for this Fragment
	private Loan myLoan = null;

	// Amortization schedule for this Fragment
	private Amortization myAmortization = null;

	// Parent fragment 
	private LoanActivity myLoanActivity = null;

	// Required loan information
	private EditTextEx myLoanAmount;
	private EditTextEx myInterestRate;
	private EditTextEx myLoanTermYears;
	private EditTextEx myLoanTermMonths;
	private EditTextEx myMonthlyPayment;

	// More loan info section
	private LinearLayout myMoreLoanInfoSection;
	private ImageButton myCollapseMoreLoanInfoButton;
	private ImageButton myExpandMoreLoanInfoButton;
	private ImageButton myExtraPaymentHelpButton; // NOSONAR
	private EditTextEx myExtraPayment;
	private EditTextEx myLoanStartDate;
	private Button myPickDateButton; // NOSONAR

	// Results grid
	private TableRow myResultsFirstPaymentDateRow;
	private TextView myResultsFirstPaymentDate;
	private TextView myResultsStartingBalance;
	private TextView myResultsMinimumMonthlyPayment;
	private TextView myResultsMonthlyPayment;
	private TextView myResultsExcessMonthlyPayment;
	private TextView myResultsTotalNumberOfPayments;
	private TextView myResultsPayoffDate;
	private TextView myResultsTotalInterestPaid;
	private TextView myResultsTotalPrincipalPaid;
	private TextView myResultsTotalPaymentsMade;
	private TextView myResultsBorrowerBirthDate;
	private TextView myResultsAgeWhenPaidOff;
	private TableRow myResultsAgeWhenPaidOffRow;

	/**
	 * Ctor
	 * 
	 * Note: Do not override but instead use a Bundle to pass arguments. System
	 * will not pass arguments back in when paging back from memory.
	 */
	public LoanActivityFragment()
	{
		super();
	}

	/**
	 * Setup our view
	 * 
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.loan_activity_fragment4, container, false);

		this.myLoanActivity = (LoanActivity) this.getActivity();

		this.myLoan = this.getLoan();

		User user = this.myLoanActivity.getUser();

		this.myAmortization = new Amortization(this.myLoan, user);

		this.setHasOptionsMenu(true);

		// Assign member variables to view's widgets
		this.initWidgets(view);

		// Set loan values in view
		this.setLoanData();

		return view;

	}

	/**
	 * Set member variables to layout widgets
	 * 
	 * @param view View container for widgets
	 */
	private void initWidgets(View view)
	{
		// Required loan info section
		myLoanAmount = (EditTextEx) view.findViewById(R.id.loanAmount);
		myInterestRate = (EditTextEx) view.findViewById(R.id.annual_interest_rate);
		myLoanTermYears = (EditTextEx) view.findViewById(R.id.loan_term_years);
		myLoanTermMonths = (EditTextEx) view.findViewById(R.id.loan_term_months);
		myMonthlyPayment = (EditTextEx) view.findViewById(R.id.minimum_monthly_payment);

		// More loan info section
		myMoreLoanInfoSection = (LinearLayout) view.findViewById(R.id.more_loan_info_section);
		myCollapseMoreLoanInfoButton = (ImageButton) view.findViewById(R.id.collapseMoreLoanInfoButton);
		myExpandMoreLoanInfoButton = (ImageButton) view.findViewById(R.id.expandMoreLoanInfoButton);
		myExtraPaymentHelpButton = (ImageButton) view.findViewById(R.id.extra_payment_help);
		myExtraPayment = (EditTextEx) view.findViewById(R.id.extra_payment);
		myLoanStartDate = (EditTextEx) view.findViewById(R.id.loan_start_date);
		myPickDateButton = (Button) view.findViewById(R.id.pick_loan_date_button);

		// Results grid
		myResultsFirstPaymentDateRow = (TableRow) view.findViewById(R.id.results_first_payment_row);
		myResultsFirstPaymentDate = (TextView) view.findViewById(R.id.results_first_payment_date);
		myResultsStartingBalance = (TextView) view.findViewById(R.id.results_starting_balance);
		myResultsMinimumMonthlyPayment = (TextView) view.findViewById(R.id.results_minimum_monthly_payment);
		myResultsMonthlyPayment = (TextView) view.findViewById(R.id.results_monthly_payment);
		myResultsExcessMonthlyPayment = (TextView) view.findViewById(R.id.results_excess_monthly_payment);
		myResultsTotalNumberOfPayments = (TextView) view.findViewById(R.id.results_total_number_of_payments);
		myResultsPayoffDate = (TextView) view.findViewById(R.id.results_payoff_date);
		myResultsTotalInterestPaid = (TextView) view.findViewById(R.id.results_total_interest_paid);
		myResultsTotalPrincipalPaid = (TextView) view.findViewById(R.id.results_total_principal_paid);
		myResultsTotalPaymentsMade = (TextView) view.findViewById(R.id.results_total_payments_made);
		myResultsBorrowerBirthDate = (TextView) view.findViewById(R.id.results_borrower_birth_date);
		myResultsAgeWhenPaidOff = (TextView) view.findViewById(R.id.results_age_when_paid_off);
		myResultsAgeWhenPaidOffRow = (TableRow) view.findViewById(R.id.results_age_when_paid_off_row);

		// Set reasonable loan term ranges
		this.myLoanTermMonths.setFilters(new InputFilter[] { new RangeFilter(MIN_MONTH, MAX_MONTH) });
		this.myLoanTermYears.setFilters(new InputFilter[] { new RangeFilter(MIN_YEAR, MAX_YEAR) });

		// Add text change listeners for EditTextEx
		this.myLoanAmount.addDoneEditingListener(this.myUpdateLoanAmountListener);
		this.myInterestRate.addDoneEditingListener(this.myUpdateInterestRateListener);
		this.myLoanTermYears.addDoneEditingListener(this.myUpdateLoanTermYearsListener);
		this.myLoanTermMonths.addDoneEditingListener(this.myUpdateLoanTermMonthsListener);
		this.myExtraPayment.addDoneEditingListener(this.myUpdateExtraPaymentListener);

		// No editing allowed for this field
		this.myMonthlyPayment.setKeyListener(null);

		// No editing allowed for Loan Start Date field
		this.myLoanStartDate.setKeyListener(null);

		// Button listeners
		myPickDateButton.setOnClickListener(this);
		myCollapseMoreLoanInfoButton.setOnClickListener(this);
		myExpandMoreLoanInfoButton.setOnClickListener(this);
		myExtraPaymentHelpButton.setOnClickListener(this);

		// Initially set more loan info section invisible
		myExpandMoreLoanInfoButton.setVisibility(View.VISIBLE);
		myCollapseMoreLoanInfoButton.setVisibility(View.GONE);
		myMoreLoanInfoSection.setVisibility(View.GONE);

	}

	/**
	 * Listener for when user updates the extra payment amount
	 */
	private final EditTextExListener myUpdateExtraPaymentListener = new EditTextExListener()
	{
		@Override
		public void onDoneEditing(EditTextEx view)
		{
			double currentValue = myLoan.getExtraPayment();
			double newValue = myExtraPayment.getDouble();

			if (!MathUtil.areEqual(currentValue, newValue))
			{
				myLoan.setExtraPayment(newValue);
				invalidate();
			}
		}
	};

	/**
	 * Listener for when user updates the loan amount
	 */
	private final EditTextExListener myUpdateLoanAmountListener = new EditTextExListener()
	{
		@Override
		public void onDoneEditing(EditTextEx view)
		{
			double currentValue = myLoan.getLoanAmount();
			double newValue = myLoanAmount.getDouble();

			if (!MathUtil.areEqual(currentValue, newValue))
			{
				myLoan.setLoanAmount(myLoanAmount.getDouble());
				invalidate();
			}
		}
	};

	/**
	 * Listener for when the user updates the interest rate
	 */
	private final EditTextExListener myUpdateInterestRateListener = new EditTextExListener()
	{
		@Override
		public void onDoneEditing(EditTextEx view)
		{
			double currentValue = myLoan.getInterestRatePct();
			double newValue = myInterestRate.getDouble();

			if (!MathUtil.areEqual(currentValue, newValue))
			{
				myLoan.setInterestRatePct(newValue);
				invalidate();
			}
		}
	};

	/**
	 * Listener for when the user updates the loan term years value
	 */
	private final EditTextExListener myUpdateLoanTermYearsListener = new EditTextExListener()
	{
		@Override
		public void onDoneEditing(EditTextEx view)
		{
			int currentValue = myLoan.getYears();
			int newValue = myLoanTermYears.getInteger();

			if (currentValue != newValue)
			{
				myLoan.setYears(newValue);
				invalidate();
			}
		}
	};

	/**
	 * Listener for when the user updates the loan term months value
	 */
	private final EditTextExListener myUpdateLoanTermMonthsListener = new EditTextExListener()
	{
		@Override
		public void onDoneEditing(EditTextEx view)
		{
			int currentValue = myLoan.getMonths();
			int newValue = myLoanTermMonths.getInteger();

			if (currentValue != newValue)
			{
				myLoan.setMonths(newValue);
				invalidate();
			}
		}
	};

	/**
	 * Invalidate all the Fragments contents so they will be recalculated and
	 * redrawn
	 */
	public void invalidate()
	{
		this.myAmortization.clear();

		this.setLoanData();
	}

	/**
	 * Update our view widgets with the value of the given loan
	 * 
	 * @param loan Loan to show on screen
	 */
	private void setLoanData()
	{
		// Set loan amount
		myLoanAmount.setTextEx(this.myLoan.getLoanAmountString());

		// Annual interest rate
		myInterestRate.setTextEx(this.myLoan.getInterestRatePctString());

		// Loan term years
		myLoanTermYears.setTextEx(this.myLoan.getYearsString());

		// Loan term months
		myLoanTermMonths.setTextEx(this.myLoan.getMonthsString());

		// Monthly payment
		myMonthlyPayment.setTextEx(this.myLoan.getMonthlyPaymentString());

		myExtraPayment.setText(this.myLoan.getExtraPaymentString());

		myLoanStartDate.setText(this.myLoan.getLoanStartDateString());

		// First payment date
		if (this.myLoan.getLoanStartDate().getTime() == 0)
		{
			this.myResultsFirstPaymentDateRow.setVisibility(View.GONE);

		}
		else
		{
			this.myResultsFirstPaymentDateRow.setVisibility(View.VISIBLE);
			this.myResultsFirstPaymentDate.setText(this.myLoan.getLoanStartDateString());
		}

		// Starting Balance
		this.myResultsStartingBalance.setText(this.myLoan.getLoanAmountString());

		// Minimum monthly payment
		this.myResultsMinimumMonthlyPayment.setText(this.myLoan.getMinimumPaymentString());

		// Monthly payment
		this.myResultsMonthlyPayment.setText(this.myLoan.getMonthlyPaymentString());

		// Excess monthly payment
		this.myResultsExcessMonthlyPayment.setText(this.myLoan.getExtraPaymentString());

		// Total number of payments
		Integer totalPayments = this.myAmortization.getTotalNumberOfPayments();
		this.myResultsTotalNumberOfPayments.setText(totalPayments.toString());

		// Payoff date
		this.myResultsPayoffDate.setText(myAmortization.getPayoffDateString());

		// Total interest paid
		this.myResultsTotalInterestPaid.setText(myAmortization.getTotalInterestPaidString());

		// Total principal paid
		this.myResultsTotalPrincipalPaid.setText(myAmortization.getTotalPrincipalPaidString());

		// Total payments made
		this.myResultsTotalPaymentsMade.setText(myAmortization.getTotalPaymentsString());

		// Borrower's birth date
		User user = this.myLoanActivity.getUser();
		this.myResultsBorrowerBirthDate.setText(user.getBirthDateString());

		// Age when paid off
		Period age = myAmortization.getPaidOffAge();
		if (age == null)
		{
			// If age is unknown, hide the entire row
			this.myResultsAgeWhenPaidOffRow.setVisibility(View.GONE);
		}
		else
		{
			int ageYears = age.getYears();
			int ageMonths = age.getMonths();

			String ageString = (ageMonths > 0) ? String.format(G.getLocale(), "%d y,  %d m", ageYears, ageMonths)
				: String.format(G.getLocale(), "%d y", ageYears);

			this.myResultsAgeWhenPaidOff.setText(ageString);

			this.myResultsAgeWhenPaidOffRow.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * Get the Loan to be described by this Fragment as passed in by the View
	 * arguments Bundle
	 * 
	 * @return Loan on this page
	 * @throws Exception
	 */
	private Loan getLoan()
	{
		Loan loan = null;

		try
		{
			Bundle bundle = this.getArguments();
			if (bundle == null)
			{
				throw new IllegalArgumentException("Failed to get loan id - no Bundle arguments");
			}

			if (!bundle.containsKey(Loan.IDENTITY))
			{
				throw new IllegalArgumentException("Failed to get loan id - Bundle arguments doesn't contain key : "
					+ Loan.IDENTITY);
			}

			int id = bundle.getInt(Loan.IDENTITY);

			DataManager dm = DataManager.instance();
			loan = dm.getLoan(id);
		}
		catch (Exception ex)
		{
			loan = null;
			Log.e(TAG, ex.getMessage(), ex);
		}

		return loan;
	}

	/**
	 * First indication that user is leaving the fragment, user may or may not
	 * come back to this fragment so any changes should be committed here
	 * <p>
	 * Unregisters our message bus
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause()
	{
		DataManager dm = DataManager.instance();

		if (this.myLoan != null)
		{
			dm.save(this.myLoan);
		}

		Bus.getBus().unregister(this);

		super.onPause();
	}

	/** 
	 * Register our message bus
	 *
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume()
	{
		Bus.getBus().register(this);

		super.onResume();
	}

	/**
	 * Pick a new loan start date via dialog
	 */
	private void pickLoanStartDate()
	{
		Calendar date = GregorianCalendar.getInstance();
		date.setTime(this.myLoan.getLoanStartDate());

		Context context = getActivity();

		DateDialogFragment ddf = DateDialogFragment.create(context, date);

		ddf.setListener(new DateDialogFragmentListener()
		{
			// Fired when user selects date
			@Override
			public void onDateSet(int year, int month, int day)
			{
				Calendar cal = new GregorianCalendar(G.getLocale());
				cal.set(year, month, day);

				long currentValue = myLoan.getLoanStartDate().getTime();
				long newValue = cal.getTime().getTime();

				if (newValue != currentValue)
				{
					myLoan.setLoanStartDate(cal.getTime());
					invalidate();
				}
			}
		});

		ddf.show(this.getFragmentManager(), "date picker dialog fragment");
	}

	/**
	 * Enter the user's birthday via a DateDialogFragment
	 */
	private void enterBirthday()
	{
		final User user = this.myLoanActivity.getUser();
		final Calendar date = GregorianCalendar.getInstance();
		date.setTime(user.getBirthDate());

		final Context context = getActivity();
		final DateDialogFragment ddf = DateDialogFragment.create(context, date);

		ddf.setListener(new DateDialogFragmentListener()
		{
			// Fired when user selects date
			@Override
			public void onDateSet(int year, int month, int day)
			{
				Calendar cal = new GregorianCalendar(G.getLocale());
				cal.set(year, month, day);

				user.setBirthDate(cal.getTime());
				DataManager.instance().save(user);
				invalidate();
			}
		});

		ddf.show(this.getFragmentManager(), "birthday picker dialog fragment");
	}

	/**
	 * User sets a custom loan name from a dialog instead of the default one
	 */
	private void setLoanName()
	{
		FragmentManager fm = getFragmentManager();

		SetLoanNameDialog dlg = SetLoanNameDialog.create(this.myLoan.getId(), this.myLoan.getName());

		dlg.show(fm, "fragment_set_loan_name");
	}

	/**
	 * Listener for the SetLoanNameDialogEvent Dialog which sets the loan name
	 * 
	 * @param event Event fired when SetLoanNameDialog completes
	 */
	@Subscribe
	public void onLoanNameSet(SetLoanNameDialogEvent event)
	{
		int loanId = event.getLoanId();
		String loanName = event.getLoanName();

		// Update the loan name if our loan name has been updated
		if (this.myLoan.getId() == loanId)
		{
			this.myLoan.setName(loanName);
			DataManager.instance().save(this.myLoan);

			this.myLoanActivity.restart(loanId);
		}
	}

	/**
	 * Show Extra payment help
	 */
	private void showExtraPaymentHelpDialog()
	{
		AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();

		dlg.setTitle(R.string.Extra_Payment);
		dlg.setIcon(R.drawable.ic_action_about);

		String helpText = getResources().getString(R.string.ExtraPaymentHelpMessage);
		dlg.setMessage(helpText);

		// Set OK Button
		String ok = getResources().getString(R.string.OK);

		dlg.setButton(AlertDialog.BUTTON_POSITIVE, ok, (DialogInterface.OnClickListener) null);

		dlg.show();
	}

	/**
	 * Send this loan's information in an email message
	 */
	private void sendEmail()
	{
		Intent i = new Intent(Intent.ACTION_SEND);

		i.putExtra(Intent.EXTRA_SUBJECT, "Mortgage Calculator Results");
		i.putExtra(Intent.EXTRA_TEXT, "email text");
		i.setType("message/rfc822");

		Resources r = getResources();

		r.getString(R.string.First_payment_date);

		StringBuilder sb = new StringBuilder();

		// First payment date
		sb.append(String.format("%s : %s%n", r.getString(R.string.First_payment_date),		// NOSONAR
			this.myLoan.getLoanStartDateString()));	

		// Loan Amount
		sb.append(String.format("%s : %s%n", r.getString(R.string.Loan_amount), this.myLoan.getLoanAmountString()));

		// Annual Interest Rate %
		sb.append(String.format("%s : %s%n", r.getString(R.string.Annual_Interest_Rate_Pct),
			this.myLoan.getInterestRatePctString()));

		// Loan Term
		String loanTerm = (this.myLoan.getMonths() > 0) ? String.format(G.getLocale(), "%d y, %d m%n",
			this.myLoan.getYears(), this.myLoan.getMonths()) : String.format(G.getLocale(), "%d y%n",
			this.myLoan.getYears());

		sb.append(String.format("%s : %s%n", r.getString(R.string.Loan_term), loanTerm));

		// Minimum monthly payment
		sb.append(String.format("%s : %s%n", r.getString(R.string.Minimum_monthly_payment),
			this.myLoan.getMinimumPaymentString()));

		// Monthly payment
		sb.append(String.format("%s : %s%n", r.getString(R.string.Monthly_payment), this.myLoan.getMonthlyPaymentString()));

		// Excess monthly payment
		sb.append(String.format("%s : %s%n", r.getString(R.string.Excess_monthly_payment),
			this.myLoan.getExtraPaymentString()));

		// Total number of payments
		sb.append(String.format("%s : %d%n", r.getString(R.string.Total_number_of_payments),
			this.myAmortization.getTotalNumberOfPayments()));

		// Payoff date
		sb.append(String.format("%s : %s%n", r.getString(R.string.Payoff_date), this.myAmortization.getPayoffDateString()));

		// Total interest paid
		sb.append(String.format("%s : %s%n", r.getString(R.string.Total_interest_paid),
			this.myAmortization.getTotalInterestPaidString()));

		// Total principal paid
		sb.append(String.format("%s : %s%n", r.getString(R.string.Total_principal_paid),
			this.myAmortization.getTotalPrincipalPaidString()));

		// Total payments made
		sb.append(String.format("%s : %s%n", r.getString(R.string.Total_payments_made),
			this.myAmortization.getTotalPaymentsString()));

		// Borrower's birth date
		User user = this.myLoanActivity.getUser();
		sb.append(String.format("%s : %s%n", r.getString(R.string.Borrower_birth_date), user.getBirthDateString()));

		// Age when paid off
		Period age = myAmortization.getPaidOffAge();
		if (age != null)
		{
			int ageYears = age.getYears();
			int ageMonths = age.getMonths();

			String ageString = (ageMonths > 0) ? String.format(G.getLocale(), "%d y,  %d m", ageYears, ageMonths)
				: String.format(G.getLocale(), "%d y", ageYears);

			sb.append(String.format("%s : %s%n", r.getString(R.string.Age_when_paid_off), ageString));
		}

		i.putExtra(Intent.EXTRA_TEXT, sb.toString());

		startActivity(Intent.createChooser(i, r.getString(R.string.Choose_an_email_client)));
	}

	/**
	 * Handle Activities action bar items
	 * <p>
	 * Note: The Activity's onOptionsItemSelected() method is called first. This
	 * is only called when the activity doesn't consume it first
	 * 
	 * TODO: Improve the amortization activity - maybe use an HTML dialog, maybe 
	 * 	create alternate view with fewer columns depending on screen size or 
	 * 	user preference -or- user defined columns to show
	 * 
	 * @see android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			// Action button - Calculate Amortization table
			case R.id.action_calculate:
								
				Intent i = new Intent(getActivity(), AmortizationActivityListView.class);
				i.putExtra(Loan.KEY_LOAN_ID, this.myLoan.getId());
				
				startActivity(i);
				return true;

			// Action button - Email results
			case R.id.action_mail:
				this.sendEmail();
				return true;

			// Menu button - Enter birth date
			case R.id.action_enter_birthday:
				this.enterBirthday();
				return true;

			// Menu button - Enter loan name
			case R.id.action_set_loan_name:
				this.setLoanName();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Button click callback handler
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// Collapse "More Loan Info" section
			case R.id.collapseMoreLoanInfoButton:
				myCollapseMoreLoanInfoButton.setVisibility(View.GONE);
				myExpandMoreLoanInfoButton.setVisibility(View.VISIBLE);
				myMoreLoanInfoSection.setVisibility(View.GONE);
				break;

			// Expand "More Loan Info" section
			case R.id.expandMoreLoanInfoButton:
				myExpandMoreLoanInfoButton.setVisibility(View.GONE);
				myCollapseMoreLoanInfoButton.setVisibility(View.VISIBLE);
				myMoreLoanInfoSection.setVisibility(View.VISIBLE);
				break;

			// Pick new loan start date via Dialog
			case R.id.pick_loan_date_button:
				this.pickLoanStartDate();
				break;

			// Extra Payment help button pressed
			case R.id.extra_payment_help:
				this.showExtraPaymentHelpDialog();
				break;

			default:
				// Do Nothing
				break;
		}

	}

}