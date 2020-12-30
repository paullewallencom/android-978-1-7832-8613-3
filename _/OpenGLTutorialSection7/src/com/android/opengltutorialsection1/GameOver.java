package com.android.opengltutorialsection1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameOver extends Activity implements OnTouchListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		
		// Sets the touch listener
		View v = (View) findViewById(R.id.view2);
		v.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		final Intent menu = new Intent(this, MainMenu.class);
		startActivity(menu);
		return false;
	}


}
