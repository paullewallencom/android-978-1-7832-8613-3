package com.opengltutorials.breakout;

public class Pipeline {
	public Vector3f mScale;
	public Vector3f mPos;
	public Vector3f mRot;
		
	public Vector3f mCamPos;
	public Vector3f mCamTarg;
	public Vector3f mCamUp;

	public PerspInfo mPersp;

	public Matrix4f mWorldTrans;
	public Matrix4f mWVPTrans;

	public Pipeline() {
		mScale = new Vector3f(1.0f,1.0f,1.0f);
		mPos = new Vector3f(0.0f, 0.0f, 0.0f);
		mRot = new Vector3f(0.0f, 0.0f, 0.0f);
	}

	public void Scale(Vector3f sc) {
		mScale = sc;
	}
		
	public void WorldPos(Vector3f pos) {
		mPos = pos;
	}
		
	public void Rotate(Vector3f rot) {
		mRot = rot;
	}
		
	public void SetPersp(PerspInfo pr) {
		mPersp = pr;
	}
		
	public void SetCamera(Vector3f pos, Vector3f targ, Vector3f up) {
		mCamPos = pos;
		mCamTarg = targ;
		mCamUp = up;
	}

	public Matrix4f getWorldTrans() {
		Matrix4f scale = new Matrix4f();
		Matrix4f rot = new Matrix4f();
		Matrix4f pos = new Matrix4f();
				
		scale.scaleTransform(mScale);
		rot.rotateTransform(mRot);
		pos.translateTransform(mPos);

		Matrix4f sc = pos.mult(rot);
		mWorldTrans = sc.mult(scale);
		
		return mWorldTrans;

	}
	
	public Matrix4f getWVPTrans() {
		getWorldTrans();
		
		Matrix4f cameraTrans = new Matrix4f();
		Matrix4f cameraRot = new Matrix4f();
		Matrix4f persp = new Matrix4f();
				
		cameraTrans.translateTransform(new Vector3f(-mCamPos.x, -mCamPos.y, -mCamPos.z));
		cameraRot.cameraTransform(mCamTarg, mCamUp);
		persp.perspProjMatrix(mPersp);

		Matrix4f pcr = persp.mult(cameraRot);
		Matrix4f pc = pcr.mult(cameraTrans);
		mWVPTrans = pc.mult(mWorldTrans);
				
		return mWVPTrans;

	}
	
}
