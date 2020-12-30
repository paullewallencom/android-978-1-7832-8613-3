package com.opengltutorials.breakout;

public class PerspInfo {
	// The attributes for the perspective information
	public float FOV;
	public float Width;
	public float Height;
	public float zNear;
	public float zFar;
	
	// Constructor to set the attributes
	public PerspInfo(float f, float w, float h, float zn, float zf)
	{
		FOV = f;
		Width = w;
		Height = h;
		zNear = zn;
		zFar = zf;
	}


}
