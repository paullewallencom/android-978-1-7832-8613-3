package com.opengltutorials.breakout;


public class Matrix4f {

	public float m[][] = new float[4][4]; 
	
	public void initIdentity() 
	{
	m[0][0] = 1.0f; m[0][1] = 0.0f; m[0][2] = 0.0f; m[0][3] = 0.0f;
	m[1][0] = 0.0f; m[1][1] = 1.0f; m[1][2] = 0.0f; m[1][3] = 0.0f;
	m[2][0] = 0.0f; m[2][1] = 0.0f; m[2][2] = 1.0f; m[2][3] = 0.0f;
	m[3][0] = 0.0f; m[3][1] = 0.0f; m[3][2] = 0.0f; m[3][3] = 1.0f;
	}

	public void perspProjMatrix(PerspInfo p) {
		float ar = (float)p.Width / (float)p.Height;
		float zRange = p.zNear - p.zFar;
		float tanHalfFOV = (float) Math.tan(Math.toRadians(p.FOV / 2.0));

		m[0][0] = 1.0f/(tanHalfFOV * ar); m[0][1] = 0.0f;            m[0][2] = 0.0f;            				m[0][3] = 0.0f;
	    m[1][0] = 0.0f;                   m[1][1] = 1.0f/tanHalfFOV; m[1][2] = 0.0f;            				m[1][3] = 0.0f;
	    m[2][0] = 0.0f;                   m[2][1] = 0.0f;            m[2][2] = (-p.zNear - p.zFar)/zRange ; 	m[2][3] = 2f * p.zFar * (p.zNear / zRange);
	    m[3][0] = 0.0f;                   m[3][1] = 0.0f;            m[3][2] = 1.0f; 							m[3][3] = 0.0f;

	}
	
	public void cameraTransform(Vector3f targ, Vector3f up) {
		Vector3f n = targ;
		n.normalize();
		Vector3f u = up;
		u.normalize();
		u = u.cross(n);
		Vector3f v = n.cross(u);

		m[0][0] = u.x;   m[0][1] = u.y;   m[0][2] = u.z;   m[0][3] = 0.0f;
		m[1][0] = v.x;   m[1][1] = v.y;   m[1][2] = v.z;   m[1][3] = 0.0f;
		m[2][0] = n.x;   m[2][1] = n.y;   m[2][2] = n.z;   m[2][3] = 0.0f;
		m[3][0] = 0.0f;  m[3][1] = 0.0f;  m[3][2] = 0.0f;  m[3][3] = 1.0f;

	}
	
	public void scaleTransform(Vector3f scale) {
		m[0][0] = scale.x; m[0][1] = 0.0f;    m[0][2] = 0.0f;    m[0][3] = 0.0f;
		m[1][0] = 0.0f;    m[1][1] = scale.y; m[1][2] = 0.0f;    m[1][3] = 0.0f;
		m[2][0] = 0.0f;    m[2][1] = 0.0f;    m[2][2] = scale.z; m[2][3] = 0.0f;
		m[3][0] = 0.0f;    m[3][1] = 0.0f;    m[3][2] = 0.0f;    m[3][3] = 1.0f;

	}
	
	public void translateTransform(Vector3f trans) {
		m[0][0] = 1.0f; m[0][1] = 0.0f; m[0][2] = 0.0f; m[0][3] = trans.x;
		m[1][0] = 0.0f; m[1][1] = 1.0f; m[1][2] = 0.0f; m[1][3] = trans.y;
		m[2][0] = 0.0f; m[2][1] = 0.0f; m[2][2] = 1.0f; m[2][3] = trans.z;
		m[3][0] = 0.0f; m[3][1] = 0.0f; m[3][2] = 0.0f; m[3][3] = 1.0f;

	}

	public Matrix4f mult(Matrix4f r) {
		Matrix4f ret = new Matrix4f();
			
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				ret.m[i][j] = m[i][0] * r.m[0][j] +
						 m[i][1] * r.m[1][j] +
						 m[i][2] * r.m[2][j] +
						 m[i][3] * r.m[3][j];
			}
		}
			
		return ret;
	}

	
	public void rotateTransform(Vector3f rot) {
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
				
		float x = (float) Math.toRadians(rot.x);
		float y = (float) Math.toRadians(rot.y);
		float z = (float) Math.toRadians(rot.z);

		rx.m[0][0] = 1.0f; rx.m[0][1] = 0.0f;	 			 rx.m[0][2] = 0.0f; 				rx.m[0][3] = 0.0f;
	    rx.m[1][0] = 0.0f; rx.m[1][1] = (float) Math.cos(x); rx.m[1][2] = (float) -Math.sin(x); rx.m[1][3] = 0.0f;
	    rx.m[2][0] = 0.0f; rx.m[2][1] = (float) Math.sin(x); rx.m[2][2] = (float) Math.cos(x) ; rx.m[2][3] = 0.0f;
	    rx.m[3][0] = 0.0f; rx.m[3][1] = 0.0f; 				 rx.m[3][2] = 0.0f;		 			rx.m[3][3] = 1.0f;

	    ry.m[0][0] = (float) Math.cos(y); ry.m[0][1] = 0.0f; ry.m[0][2] = (float) -Math.sin(y); ry.m[0][3] = 0.0f;
	    ry.m[1][0] = 0.0f; 				  ry.m[1][1] = 1.0f; ry.m[1][2] = 0.0f; 				ry.m[1][3] = 0.0f;
	    ry.m[2][0] = (float) Math.sin(y); ry.m[2][1] = 0.0f; ry.m[2][2] = (float) Math.cos(y) ; ry.m[2][3] = 0.0f;
	    ry.m[3][0] = 0.0f; 				  ry.m[3][1] = 0.0f; ry.m[3][2] = 0.0f; 				ry.m[3][3] = 1.0f;

	    rz.m[0][0] = (float) Math.cos(z); rz.m[0][1] = (float) -Math.sin(z); rz.m[0][2] = 0.0f; rz.m[0][3] = 0.0f;
	    rz.m[1][0] = (float) Math.sin(z); rz.m[1][1] = (float) Math.cos(z) ; rz.m[1][2] = 0.0f; rz.m[1][3] = 0.0f;
	    rz.m[2][0] = 0.0f; 				  rz.m[2][1] = 0.0f; 				 rz.m[2][2] = 1.0f; rz.m[2][3] = 0.0f;
	    rz.m[3][0] = 0.0f; 				  rz.m[3][1] = 0.0f; 				 rz.m[3][2] = 0.0f; rz.m[3][3] = 1.0f;
			
		Matrix4f rzy = rz.mult(ry);
		Matrix4f r = rzy.mult(rx);
			    
		m = r.m;

	}
	
	public float[] convertShader() {
		float[] w = new float[16];
		
		w[0]  = m[0][0]; w[1]  = m[0][1]; w[2]  = m[0][2]; w[3]  = m[0][3];
		w[4]  = m[1][0]; w[5]  = m[1][1]; w[6]  = m[1][2]; w[7]  = m[1][3];
		w[8]  = m[2][0]; w[9]  = m[2][1]; w[10] = m[2][2]; w[11] = m[2][3];
		w[12] = m[3][0]; w[13] = m[3][1]; w[14] = m[3][2]; w[15] = m[3][3];
				
		return w;

	}
}
