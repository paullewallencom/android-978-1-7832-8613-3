package com.opengltutorials.breakout;

public class Pointlight extends Baselight {
	// Position of the light
	public Vector3f lightPosition;
	// Attenuation for the light
	public float lightAtten;
	// Light fall off
	public float lightFalloff;

	// Constructor that sets the default values
	public Pointlight() {
		lightPosition = new Vector3f(0.0f, 0.0f, 0.0f);
		lightAtten = 5000;
		lightFalloff = 2;
	}

}
