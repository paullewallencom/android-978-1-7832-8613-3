package com.opengltutorials.breakout;

public abstract class Baselight {
	// The colour of the light
	Vector3f colour;
	// The ambient intensity
	float ambientIntens;
	// The diffuse Intensity
	float diffuseIntens;

	// Constructor for the baselight, sets the default values
	public Baselight() {
		colour = new Vector3f(0.0f, 0.0f, 0.0f);
		ambientIntens = 0.0f;
		diffuseIntens = 0.0f;
	}

}
