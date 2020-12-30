package com.opengltutorials.breakout;

public class PerspInfo {
	public float FOV;
	public float Width;
	public float Height;
	public float zNear;
	public float zFar;
	
	public PerspInfo(float f, float w, float h, float zn, float zf)
	{
		FOV = f;
		Width = w;
		Height = h;
		zNear = zn;
		zFar = zf;
	}


}
