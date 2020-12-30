package com.opengltutorials.breakout;

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

	// Check for the highscore
	boolean isHigh = false;
	// Edit text box so user can write their name
	EditText name;
	// Highscores table
	Highscore hs;
	// Previous score for the game
	int score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Call Activity's onCreate function
		super.onCreate(savedInstanceState);
		// Set the layout
		setContentView(R.layout.activity_game_complete);
		// Get the bundle for the score
		Bundle extras = getIntent().getExtras();
		// Get the actual score
		score = extras.getInt("com.OpenGLProject.data");
		// Find the view and set the touch listener
		View v = (View) findViewById(R.id.view1);
		v.setOnTouchListener(this);
		
		// Get the highscore textview
		TextView high = (TextView) findViewById(R.id.high);
		// Set it to invisible
		high.setVisibility(View.INVISIBLE);
		// Find the EditText box for the name
		name = (EditText) findViewById(R.id.enterName);
		// Then make that invisible
		name.setVisibility(View.INVISIBLE);
		
		// Initialize the highscores
		hs = new Highscore(this);
		
		// Check if the last score is a highscore, if so show the text views
		if(hs.isHighscore(score)) {
			isHigh = true;
			name.setVisibility(View.VISIBLE);
			high.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
	}

	// OnTouch function for the view's onTouchListener
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// If there is a highscore, go to highscore screen
		if(isHigh) {
			hs.addScore(score, name.getText().toString());
			final Intent highs = new Intent(this, HighscoreActivity.class);
			startActivity(highs);
		} else {
			// Otherwise go straight to menu
			final Intent menu = new Intent(this, MainMenu.class);
			startActivity(menu);
		}
		return false;
	}



}
