package com.android.opengltutorialsection1;

public class DirectionalLight extends Baselight {
	Vector3f direction;
	
	public DirectionalLight() {
		super();
		direction = new Vector3f(0.0f, 0.0f, 0.0f);
	}
}