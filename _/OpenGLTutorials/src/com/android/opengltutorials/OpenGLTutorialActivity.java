package com.android.opengltutorials;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.opengltutorials.breakout.R;

public class OpenGLTutorialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_gltutorial);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_gltutorial, menu);
		return true;
	}

}
