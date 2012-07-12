package com.jeesmon.apps.wikisource;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class WikisourceSetupActivity extends Activity {
	private String renderingFix = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = this.getSharedPreferences(PreferencesPlugin.PREFS_NAME, 0);
		renderingFix = settings.getString("renderingFix", null);
		if(renderingFix == null) {
			setContentView(R.layout.setup);
			setTitle(getTitle() + " - Setup");
			
			Typeface tf = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.font_name));
			
			RadioButton rb = (RadioButton) findViewById(R.id.radio_default);
			rb.setTypeface(tf);
			rb.setText(ComplexCharacterMapper.fix(getResources().getString(R.string.setup_test_text), 0));
			
			rb = (RadioButton) findViewById(R.id.radio_alternate);
			rb.setTypeface(tf);
			rb.setText(ComplexCharacterMapper.fix(getResources().getString(R.string.setup_test_text), 1));
			
			rb = (RadioButton) findViewById(R.id.radio_none);
			rb.setTypeface(tf);
		}
		else {
			startActivity(new Intent(WikisourceSetupActivity.this, WikisourceActivity.class));
		}
	}
	
	public void onRadioButtonClicked(View view) {
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    switch(view.getId()) {
	        case R.id.radio_default:
	            if (checked)
	                renderingFix = "0";
	            break;
	        case R.id.radio_alternate:
	            if (checked)
	            	renderingFix = "1";
	            break;
	        case R.id.radio_none:
	            if (checked)
	                renderingFix = "2";
	            break;
	    }
	    
	    findViewById(R.id.but_continue).setEnabled(checked);
	}
	
	public void onContinueButtonClicked(View view) {
		SharedPreferences settings = this.getSharedPreferences(PreferencesPlugin.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("renderingFix", renderingFix == null ? renderingFix : "0");
		editor.commit();
		startActivity(new Intent(WikisourceSetupActivity.this, WikisourceActivity.class));
	}
}
