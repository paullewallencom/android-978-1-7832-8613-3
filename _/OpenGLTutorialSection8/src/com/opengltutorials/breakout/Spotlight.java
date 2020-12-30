package com.opengltutorials.breakout;

public class Spotlight extends Pointlight {
	// Direction of the light
	Vector3f Direction;
	// Cutoff angle
	float cutoff;

	// Constructor that sets the default values
	public Spotlight()
	{
		super();
		Direction = new Vector3f(0.0f, 0.0f, 0.0f);
		cutoff = 0.0f;
	}

}
