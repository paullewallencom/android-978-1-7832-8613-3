package com.android.opengltutorialsection1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		
		// Set the game start button
		final Button button = (Button) findViewById(R.id.button1);
		final Intent GameStart = new Intent(this, OpenGLActivity.class);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		      	startActivity(GameStart);
			}
		});
		
		
	}


}
