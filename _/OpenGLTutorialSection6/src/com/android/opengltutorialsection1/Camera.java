package com.android.opengltutorialsection1;

public class Camera {
	
	// The position of the camera
	public Vector3f mPos;
	// The camera's target
	public Vector3f mTarget;
	// The camera's up vector
	public Vector3f mUp;

	// Constructor of the camera that sets the attributes
	public Camera(Vector3f pos, Vector3f targ, Vector3f up) {
		mPos = pos;
		mTarget = targ;
		mUp = up;
	}

	
}
