package com.android.opengltutorialsection1;

public class Pipeline {
	// The scale, position and rotation
	public Vector3f mScale;
	public Vector3f mPos;
	public Vector3f mRot;
		
	// The camera attributes
	public Vector3f mCamPos;
	public Vector3f mCamTarg;
	public Vector3f mCamUp;

	// The perspective information
	public PerspInfo mPersp;

	// The world and world view projection matrices
	public Matrix4f mWorldTrans;
	public Matrix4f mWVPTrans;

	// Constructor that sets the default values
	public Pipeline() {
		mScale = new Vector3f(1.0f,1.0f,1.0f);
		mPos = new Vector3f(0.0f, 0.0f, 0.0f);
		mRot = new Vector3f(0.0f, 0.0f, 0.0f);
	}

	// Set the scale
	public void Scale(Vector3f sc) {
		mScale = sc;
	}
		
	// Set the position
	public void WorldPos(Vector3f pos) {
		mPos = pos;
	}
		
	// Set the rotation
	public void Rotate(Vector3f rot) {
		mRot = rot;
	}
		
	// Set the perspective information
	public void SetPersp(PerspInfo pr) {
		mPersp = pr;
	}
		
	// Set the camera
	public void SetCamera(Vector3f pos, Vector3f targ, Vector3f up) {
		mCamPos = pos;
		mCamTarg = targ;
		mCamUp = up;
	}

	// Get the world matrix
	public Matrix4f getWorldTrans() {
		Matrix4f scale = new Matrix4f();
		Matrix4f rot = new Matrix4f();
		Matrix4f pos = new Matrix4f();
				
		// Set the three transforms
		scale.scaleTransform(mScale);
		rot.rotateTransform(mRot);
		pos.translateTransform(mPos);

		// Get the world matrix by multiplying the matrices
		Matrix4f sc = pos.mult(rot);
		mWorldTrans = sc.mult(scale);
		
		return mWorldTrans;

	}
	
	// Get the WVP matrix
	public Matrix4f getWVPTrans() {
		// First get the world matrix
		getWorldTrans();
		
		Matrix4f cameraTrans = new Matrix4f();
		Matrix4f cameraRot = new Matrix4f();
		Matrix4f persp = new Matrix4f();
		
		// Get the camera transform and rotation
		cameraTrans.translateTransform(new Vector3f(-mCamPos.x, -mCamPos.y, -mCamPos.z));
		cameraRot.cameraTransform(mCamTarg, mCamUp);
		// Get the perspective projection matrix
		persp.perspProjMatrix(mPersp);

		// Multiply all matrices to get the WVP matrix
		Matrix4f pcr = persp.mult(cameraRot);
		Matrix4f pc = pcr.mult(cameraTrans);
		mWVPTrans = pc.mult(mWorldTrans);
				
		return mWVPTrans;

	}
	
}
