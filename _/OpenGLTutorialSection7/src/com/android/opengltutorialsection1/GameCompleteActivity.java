package com.android.opengltutorialsection1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;

public class GameCompleteActivity extends Activity implements OnTouchListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call Activity's onCreate function
		super.onCreate(savedInstanceState);
		// Set the layout
		setContentView(R.layout.activity_game_complete);
		// Find the view and set the touch listener
		View v = (View) findViewById(R.id.view1);
		v.setOnTouchListener(this);
		

	}

	// OnTouch function for the view's onTouchListener
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// If there is a highscore, go to highscore screen
	// Otherwise go straight to menu
			final Intent menu = new Intent(this, MainMenu.class);
			startActivity(menu);
		
		return false;
	}



}
