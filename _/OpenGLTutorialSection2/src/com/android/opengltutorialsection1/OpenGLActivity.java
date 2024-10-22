package com.android.opengltutorialsection1;

import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.view.Menu;
import android.widget.Toast;

public class OpenGLActivity extends Activity {

	private GLSurfaceView glSurfaceView;
	private boolean rendererSet = false;
	
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
			
			glSurfaceView.setRenderer(new OpenGLTutorialRenderer(this));
			rendererSet = true;
		} else {
			Toast.makeText(this, "This device does not support OpenGL ES 2.0. ", Toast.LENGTH_LONG).show();
			return;
		}
		
		setContentView(glSurfaceView);
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
