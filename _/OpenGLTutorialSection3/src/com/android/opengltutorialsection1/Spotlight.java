package com.android.opengltutorialsection1;

public class Spotlight extends Pointlight {
	Vector3f Direction;
	float cutoff;
	
	public Spotlight()
	{
		super();
		Direction = new Vector3f(0.0f, 0.0f, 0.0f);
		cutoff = 0.0f;
	}
}
