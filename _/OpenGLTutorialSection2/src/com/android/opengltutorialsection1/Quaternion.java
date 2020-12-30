package com.android.opengltutorialsection1;

public class Quaternion {
	// X, Y, Z and W components
	float x;
	float y;
	float z;
	float w;

	// Constructor for the quaternion
	public Quaternion(float _x, float _y, float _z, float _w) {
		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}

	// Multiplies two quaternions together
	public Quaternion mult(Quaternion r) {
		Quaternion t;
			
		// Calculate the components
		float _w = (w * r.w) - (x * r.x) - (y * r.y) - (z * r.z);
		float _x = (x * r.w) + (w * r.x) + (y * r.z) - (z * r.y);
        float _y = (y * r.w) + (w * r.y) + (z * r.x) - (x * r.z);
        float _z = (z * r.w) + (w * r.z) + (x * r.y) - (y * r.x);
			
        // Create the new quaternion
        t = new Quaternion(_x, _y, _z, _w);
	    
        return t;
	}
	
	// Multiply the quaternion with the vector
	public Quaternion mult(Vector3f v) {
		
		// Multiply the components with the vector
		float _w = - (x * v.x) - (y * v.y) - (z * v.z);
		float _x =   (w * v.x) + (y * v.z) - (z * v.y);
		float _y =   (w * v.y) + (z * v.x) - (x * v.z);
		float _z =   (w * v.z) + (x * v.y) - (y * v.x);

		// Make the final quaternions
		Quaternion ret = new Quaternion(_x, _y, _z, _w);

		return ret;
	}

	// Reverses the quaternion
	public Quaternion conjugate() {
		Quaternion ret = new Quaternion(-x, -y, -z, -w);
		return ret;
	}

	// Normalizes the quaternion
	public void normalise() {
		float length = (float) Math.sqrt(x*x + y*y + z*z + w*w);
			
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}



}
