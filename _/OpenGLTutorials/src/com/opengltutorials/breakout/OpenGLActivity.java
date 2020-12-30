package com.opengltutorials.breakout;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OpenGLActivity extends Activity implements SensorEventListener {

	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;
	
	private TextView lives;
	private TextView score;
	private OpenGLTutorialRenderer rend;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		LinearLayout l = (LinearLayout)findViewById(R.id.rel);
				
		lives = (TextView)findViewById(R.id.lives);
		score = (TextView)findViewById(R.id.score);
		final Handler handle = new Handler();
		final Runnable r = new Runnable() {
			public void run() {
				lives.setText("Lives: " + String.valueOf(rend.getLives()));
				score.setText("Score: " + String.valueOf(rend.getScore()));
				handle.postDelayed(this, 1000);
			}
		};
		handle.postDelayed(r, 250);
		//t.setText("Lives: " + String.valueOf(rend.getLives()));
		score.setTextColor(Color.WHITE);
		lives.setTextColor(Color.WHITE);
		l.addView(glSurfaceView);

		OnTouchListener ontouch = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				rend.onTouchEvent(event);
				return true;
			}
		};
		glSurfaceView.setOnTouchListener(ontouch);
		
		Bundle extras = getIntent().getExtras();
		rend.setExtraLives(extras.getInt("com.OpenGLProject.livesoffset"));
		
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(rendererSet) {
			glSurfaceView.onPause();
		}
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(rendererSet) {
			glSurfaceView.onResume();
		}
		
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_gl, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized(this) {
			if(event.sensor.getType() ==  Sensor.TYPE_ACCELEROMETER) {
				rend.setAccelVals(event.values[0]);
			}
		}

	}

}
