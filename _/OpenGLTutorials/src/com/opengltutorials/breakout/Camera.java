package com.opengltutorials.breakout;

public class Camera {
	
	public Vector3f mPos;
	public Vector3f mTarget;
	public Vector3f mUp;

	public Camera(Vector3f pos, Vector3f targ, Vector3f up) {
		mPos = pos;
		mTarget = targ;
		mUp = up;
	}

	
}
