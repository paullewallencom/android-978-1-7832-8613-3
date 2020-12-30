package com.android.opengltutorialsection1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Paddle extends GameEntity {
	// Vertices of the paddle, in the format:  PosX, PosY, PosZ,	NormalX, NormalY, NormalZ,	ColourR, ColourG, ColourB, ColourA
	private static final float[] vertices = new float[] {
		20f, -5f, 120.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		-20f, -5f, 120.0f, 		0.0f, 0.0f, 1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f, 120.0f,		0.0f, 0.0f, 1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f, 120.0f,		0.0f, 0.0f, 1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		20f, 5f, 120.0f, 		0.0f, 0.0f, 1.0f, 		1.0f, 0.0f, 0.0f, 1.0f,
		20f, -5f, 120.0f, 		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 0.0f, 1.0f,
				
		-20f, -5f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		20f, -5f, 100.0f, 		0.0f, 0.0f, -1.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		-20f, 5f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		20f, -5f, 100.0f, 		0.0f, 0.0f, -1.0f, 		1.0f, 0.0f, 0.0f, 1.0f,
		20f, 5f, 100.0f, 		0.0f, 0.0f, -1.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		
		20f,  5f, 120.0f,		1.0f, 0.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		20f, -5f, 120.0f, 		1.0f, 0.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		20f, -5f,  100.0f,		1.0f, 0.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		20f, -5f,  100.0f,		1.0f, 0.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		20f,  5f,  100.0f, 		1.0f, 0.0f, 0.0f, 		1.0f, 0.0f, 0.0f, 1.0f,
		20f,  5f, 120.0f, 		1.0f, 0.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
				
		-20f, -5f, 100.0f,		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f, 100.0f, 		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, -5f, 120.0f,		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, -5f, 120.0f,		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f, 100.0f, 		-1.0f, 0.0f, 0.0f, 		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f,  120.0f, 		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
				
		-20f, 5f, 100.0f,		0.0f, 1.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		20f,  5f, 100.0f, 		0.0f, 1.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		-20f, 5f,  120.0f,		0.0f, 1.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, 5f,  120.0f,		0.0f, 1.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		20f,  5f, 100.0f, 		0.0f, 1.0f, 0.0f, 		1.0f, 0.0f, 0.0f, 1.0f,
		20f,  5f, 120.0f, 		0.0f, 1.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
				
		20f, -5f, 100.0f,		0.0f, -1.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,
		-20f, -5f, 100.0f, 		0.0f, -1.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, -5f, 120.0f,		0.0f, -1.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		-20f, -5f, 120.0f,		0.0f, -1.0f, 0.0f,		0.0f, 0.0f, 1.0f, 1.0f,
		 20f, -5f, 120.0f,  	0.0f, -1.0f, 0.0f,  	1.0f, 0.0f, 0.0f, 1.0f,
		20f, -5f, 100.0f, 		0.0f, -1.0f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f
	};
	
	// Floatbuffer for the vertex data
	private FloatBuffer vertexData;
	// The boundary in the x direction
	private static float XLIMIT = 70.0f; 

	// The velocity for the accelerometer
	private float velX = 0;
	
	// Constructor for the paddle that initializes the attributes and float buffer
	public Paddle(Vector3f p) {
		super(p, 40, 10);
			
		vertexData = ByteBuffer.allocateDirect(vertices.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexData.put(vertices);
	}
	
	// Set the X-velocity
	public void setVelX(float x) {
		velX = x;
	}
	
	public void update(float elapsedTime) {
		// Update the position of the paddle based on velocity
		float nextX = getPos().x - ((velX * 15) * elapsedTime);
		if(nextX < XLIMIT && nextX > -XLIMIT ) {
			getPos().x = nextX;
		}
	}

	// Set the attribute pointers
	public void setAttributePoints(int pos, int norm, int col) {
		// One for the position
		vertexData.position(0);
		glVertexAttribPointer(pos, 3, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(pos);
			
		// One for the normals
		vertexData.position(3);
		glVertexAttribPointer(norm, 3, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(norm);
			
		// Last one for the colour
		vertexData.position(6);
		glVertexAttribPointer(col, 4, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(col);
		vertexData.position(0);
	}

	public void draw() {
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}
}
