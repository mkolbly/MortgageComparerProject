package com.kolbly.mortgagecomparer.activities.about;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Window;
import android.widget.TextView;

import com.kolbly.android.util.ResourceUtil;
import com.kolbly.mortgagecomparer.R;

/**
 * "About Dialog" for this program
 * 
 * Raw text files info and legal are displayed in this dialog
 */
public class AboutDialog extends Dialog
{
	private Context myContext = null;

	/**
	 * Ctor
	 * 
	 * @param context Context to be used
	 */
	public AboutDialog(Context context)
	{
		super(context);

		myContext = context;
	}

	/**
	 * Initialize dialog
	 * <p>
	 * Note: Raw resources info_text and legal_text are both set to honor
	 * supported android html markup
	 * 
	 * @see android.app.Dialog#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		
		// Info Text displayed from raw resource file info_txt
		TextView info = (TextView) findViewById(R.id.info_text);
		String infoText = ResourceUtil.getResourceText(myContext, R.raw.info);
		info.setText(Html.fromHtml(infoText));
		info.setLinkTextColor(Color.WHITE);
		
		// Lower legal portion displayed from raw resource file legal_text
		TextView legal = (TextView) findViewById(R.id.legal_text);
		String legalText = ResourceUtil.getResourceText(myContext, R.raw.legal);
		legal.setText(Html.fromHtml(legalText));	
		legal.setLinkTextColor(Color.WHITE);
		legal.setMovementMethod(LinkMovementMethod.getInstance());
		Linkify.addLinks(legal, Linkify.WEB_URLS);
		
		// Dialog is canceled if user clicks outside of it
		this.setCanceledOnTouchOutside(true);
	}

}