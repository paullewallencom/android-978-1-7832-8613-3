package com.opengltutorials.breakout;

public class Vector3f {
	// x,y,z components of vector
	public float x;
	public float y;
	public float z;
	
	// Constructor that sets the variables
	public Vector3f(float _x, float _y, float _z)
	{
		x = _x;
		y = _y;
		z = _z;
	}

	// Function that adds two vectors
	public void add(Vector3f v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	// Function that subtracts two vectors
	public void subtract(Vector3f v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}

	// Function that multiplies a vector with a float
	public void Mult(float f)
	{
		x *= f;
		y *= f;
		z *= f;
	}

	// Normalize the vector
	public void normalize() {
		// Get the length
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		
		// Divide each component by the length
		x /= length;
		y /= length;
		z /= length;
	}
	
	// Calculate the dot product of two vectors
	public float dot(Vector3f v) {
		float _x = x * v.x;
		float _y = y * v.x;
		float _z = z * v.z;
				
		return _x + _y + _z;

	}
	
	// Calculate the cross product of two vectors
	public Vector3f cross(Vector3f v) {
		float _x = y * v.z - z * v.y;
		float _y = z * v.x - x * v.z;
		float _z = x * v.y - y * v.x;
		
		Vector3f c = new Vector3f(_x, _y, _z);
		
		return c;


	}
	
	public void rotate(float angle, Vector3f axe) {
		float sinHalfAngle = (float) Math.sin(Math.toRadians(angle)/2);
		float cosHalfAngle = (float) Math.cos(Math.toRadians(angle)/2);
				
		float Rx = axe.x * sinHalfAngle;
		float Ry = axe.y * sinHalfAngle;
		float Rz = axe.z * sinHalfAngle;
		float Rw = cosHalfAngle;
		Quaternion RotationQ = new Quaternion(Rx, Ry, Rz, Rw);

		Quaternion ConjugateQ = RotationQ.conjugate();
		Quaternion W = RotationQ.mult(this);
		W = W.mult(ConjugateQ);
				
		x = W.x;
		y = W.y;
		z = W.z;

	}
}
