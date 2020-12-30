package com.opengltutorials.breakout;

public abstract class Baselight {
	Vector3f colour;
	float ambientIntens;
	float diffuseIntens;

	public Baselight() {
		colour = new Vector3f(0.0f, 0.0f, 0.0f);
		ambientIntens = 0.0f;
		diffuseIntens = 0.0f;
	}

}
