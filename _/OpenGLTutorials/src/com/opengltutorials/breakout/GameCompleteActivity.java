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

	boolean isHigh = false;
	EditText name;
	Highscore hs;
	int score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_complete);
		Bundle extras = getIntent().getExtras();
		score = extras.getInt("com.OpenGLProject.data");
		View v = (View) findViewById(R.id.view1);
		v.setOnTouchListener(this);
		
		TextView high = (TextView) findViewById(R.id.high);
		high.setVisibility(View.INVISIBLE);
		name = (EditText) findViewById(R.id.enterName);
		name.setVisibility(View.INVISIBLE);
		
		hs = new Highscore(this);
		
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(isHigh) {
			hs.addScore(score, name.getText().toString());
			final Intent highs = new Intent(this, HighscoreActivity.class);
			startActivity(highs);
		} else {
			final Intent menu = new Intent(this, MainMenu.class);
			startActivity(menu);
		}
		return false;
	}



}
