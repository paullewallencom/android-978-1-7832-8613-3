package com.android.opengltutorialsection1;

public class Pointlight extends Baselight {
	public Vector3f lightPosition;
	public float lightAtten;
	public float lightFalloff;
	
	public Pointlight() {
		super();
		lightPosition = new Vector3f(0.0f, 0.0f, 0.0f);
		lightAtten = 5000;
		lightFalloff = 2;
	}
}
