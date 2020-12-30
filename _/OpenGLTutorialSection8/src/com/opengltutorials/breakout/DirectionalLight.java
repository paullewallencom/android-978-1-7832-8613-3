package com.opengltutorials.breakout;

public class DirectionalLight extends Baselight{
	// The direction of the light
	Vector3f direction;
	
	// Constructor that sets the default attributes
	public DirectionalLight() {
		super();
		direction = new Vector3f(0.0f, 0.0f, 0.0f);
	}

}
