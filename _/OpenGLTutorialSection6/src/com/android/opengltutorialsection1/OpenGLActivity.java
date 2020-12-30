package com.android.opengltutorialsection1;


import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OpenGLActivity extends Activity {

	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;
	
	// Textview for the current lives and score
	private TextView lives;
	private TextView score;
		
	// The tutorial renderer
	private OpenGLTutorialRenderer rend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_open_gltutorial);
		
		glSurfaceView = new GLSurfaceView(this);
		
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configInfo = activityManager.getDeviceConfigurationInfo();
		
		final boolean supportsEs2 = configInfo.reqGlEsVersion >= 0x20000
				|| (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
				&& (Build.FINGERPRINT.startsWith("generic")
				|| Build.FINGERPRINT.startsWith("unknown")
				|| Build.MODEL.contains("google_sdk")
				|| Build.MODEL.contains("Emulator")
				|| Build.MODEL.contains("Android SDK built for x86")));
		
		
			if(supportsEs2) {
				glSurfaceView.setEGLContextClientVersion(2);
				rend = new OpenGLTutorialRenderer(this);
				glSurfaceView.setRenderer(rend);
				rendererSet = true;
			   } else {
				   Toast.makeText(this, "This device does not support OpenGL ES 2.0. ", Toast.LENGTH_LONG).show();
				return;
			   }

			setContentView(R.layout.activity_open_gl);
			// Get the linear layout from the XML
			LinearLayout l = (LinearLayout)findViewById(R.id.rel);
					
			// Get the lives and score TextViews
			lives = (TextView)findViewById(R.id.lives);
			score = (TextView)findViewById(R.id.score);
			
			final Handler handle = new Handler();
			// Start a new runnable that sets the lives and scores TextViews
			final Runnable r = new Runnable() {
				public void run() {
					lives.setText("Lives: " + String.valueOf(rend.getLives()));
					score.setText("Score: " + String.valueOf(rend.getScore()));
					handle.postDelayed(this, 1000);
				}
			};
			handle.postDelayed(r, 250);
			
			// Set the colour of the text boxes
			score.setTextColor(Color.WHITE);
			lives.setTextColor(Color.WHITE);
			
			// Add the surface view to the layout
			l.addView(glSurfaceView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_gl, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(rendererSet) {
			glSurfaceView.onPause();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(rendererSet) {
			glSurfaceView.onResume();
		}
	}

}
