package com.opengltutorials.breakout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighscoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get all score TextViews
		TextView s0 = (TextView) findViewById(R.id.score0);
		TextView s1 = (TextView) findViewById(R.id.score1);
		TextView s2 = (TextView) findViewById(R.id.score2);
		TextView s3 = (TextView) findViewById(R.id.score3);
		TextView s4 = (TextView) findViewById(R.id.score4);
		TextView s5 = (TextView) findViewById(R.id.score5);
		TextView s6 = (TextView) findViewById(R.id.score6);
		TextView s7 = (TextView) findViewById(R.id.score7);
		TextView s8 = (TextView) findViewById(R.id.score8);
		TextView s9 = (TextView) findViewById(R.id.score9);

		// Get all name TextViews
		TextView n0 = (TextView) findViewById(R.id.name0);
		TextView n1 = (TextView) findViewById(R.id.name1);
		TextView n2 = (TextView) findViewById(R.id.name2);
		TextView n3 = (TextView) findViewById(R.id.name3);
		TextView n4 = (TextView) findViewById(R.id.name4);
		TextView n5 = (TextView) findViewById(R.id.name5);
		TextView n6 = (TextView) findViewById(R.id.name6);
		TextView n7 = (TextView) findViewById(R.id.name7);
		TextView n8 = (TextView) findViewById(R.id.name8);
		TextView n9 = (TextView) findViewById(R.id.name9);

		// Get the back button and reset button
		Button back = (Button) findViewById(R.id.back);
		Button reset = (Button) findViewById(R.id.reset);
		
		// Create a new Highscore variable
		final Highscore hs = new Highscore(this);

		// Set the score TextViews using the Highscores
		s0.setText(""+hs.getScore(0));
		s1.setText(""+hs.getScore(1));
		s2.setText(""+hs.getScore(2));
		s3.setText(""+hs.getScore(3));
		s4.setText(""+hs.getScore(4));
		s5.setText(""+hs.getScore(5));
		s6.setText(""+hs.getScore(6));
		s7.setText(""+hs.getScore(7));
		s8.setText(""+hs.getScore(8));
		s9.setText(""+hs.getScore(9));

		// Set the name TextViews using the Highscores
		n0.setText(""+hs.getName(0));
		n1.setText(""+hs.getName(1));
		n2.setText(""+hs.getName(2));
		n3.setText(""+hs.getName(3));
		n4.setText(""+hs.getName(4));
		n5.setText(""+hs.getName(5));
		n6.setText(""+hs.getName(6));
		n7.setText(""+hs.getName(7));
		n8.setText(""+hs.getName(8));
		n9.setText(""+hs.getName(9));

		// Set the back button's onClick listener to go to the main menu
		final Intent menu = new Intent(this, MainMenu.class);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
						
			startActivity(menu);
			}
		});

		// Set the reset button's onClick listener to reset the scores
		reset.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				hs.resetScore();
			}
		});

		
		setContentView(R.layout.activity_highscore);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.highscore, menu);
		return true;
	}

}
