package com.opengltutorials.breakout;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Ball extends GameEntity{
	
	// Velocity of the ball
	private Vector3f vel;
	
	// Boolean to start the ball's movement
	private Boolean started;

	// Floatbuffer used to hold vertex data
	private FloatBuffer vertexData;
	
	// Vertices of the ball, in the format:  PosX, PosY, PosZ,	NormalX, NormalY, NormalZ,	ColourR, ColourG, ColourB, ColourA
	private static final float[] vertices= new float[] {
			0.0f, 0.0f, 100.0f,		0.0f, 0.0f, -1.0f,		1.0f, 0.0f, 0.0f, 1.0f,
			-6.0f, -5.0f, 110.0f,	0.0f, 0.0f, -1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			0.0f, -10.0f, 110.0f, 	0.0f, 0.0f, -1.0f, 		0.0f, 1.0f, 0.0f, 1.0f,
			6.0f, -5.0f, 110.0f,	0.0f, 0.0f, -1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			10.0f, 0.0f, 110.0f,	0.0f, 0.0f, -1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
			6.0f, 5.0f, 110.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			0.0f, 10.0f, 110.0f,	0.0f, 0.0f, -1.0f,		0.0f, 1.0f, 0.0f, 1.0f,
			-6.0f, 5.0f, 110.0f,	0.0f, 0.0f, -1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			-10.0f, 0.0f, 110.0f,	0.0f, 0.0f, -1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
			-6.0f, -5.0f, 110.0f, 	0.0f, 0.0f, -1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			
			0.0f, 0.0f, 120.0f,		0.0f, 0.0f, 1.0f,		1.0f, 0.0f, 0.0f, 1.0f,
			-6.0f, -5.0f, 110.0f,	0.0f, 0.0f, 1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			0.0f, -10.0f, 110.0f, 	0.0f, 0.0f, 1.0f, 		0.0f, 1.0f, 0.0f, 1.0f,
			6.0f, -5.0f, 110.0f,	0.0f, 0.0f, 1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			10.0f, 0.0f, 110.0f,	0.0f, 0.0f, 1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
			6.0f, 5.0f, 110.0f,		0.0f, 0.0f, 1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			0.0f, 10.0f, 110.0f,	0.0f, 0.0f, 1.0f,		0.0f, 1.0f, 0.0f, 1.0f,
			-6.0f, 5.0f, 110.0f,	0.0f, 0.0f, 1.0f,		0.0f, 0.5f, 0.5f, 1.0f,
			-10.0f, 0.0f, 110.0f,	0.0f, 0.0f, 1.0f,		0.0f, 0.0f, 1.0f, 1.0f,
			-6.0f, -5.0f, 110.0f, 	0.0f, 0.0f, 1.0f,		0.0f, 0.5f, 0.5f, 1.0f
	};

	// Contructor for the ball, sets the initial values and initalises the float buffer
	public Ball(Vector3f p) {
		super(p, 20, 20);
		vel = new Vector3f(40f, 50f, 0.0f);
		started = false;
			
		vertexData = ByteBuffer.allocateDirect(vertices.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexData.put(vertices);
	}
	
	// Sets the start boolean
	public void setStart(boolean start) {
		started = start;
	}
	
	// Updates the ball's movement
	public void update(float elapsedTime) {
		// If the game has started
		if(started) {
			// Create the next position to be added
			Vector3f next = new Vector3f(vel.x, vel.y, vel.z);
			// Multiply by the elapsed time to smooth movement
			next.Mult(elapsedTime);
			// Update the position
			getPos().add(next);

			// Checks for the boundary, if the ball reaches them the velocity is reversed
			if(getPos().x <  -80) {
				getPos().x = -80;
				vel.x = -vel.x;
			} else if(getPos().x > 80) {
				getPos().x = 80;
				vel.x = -vel.x;
			}
		
			if(getPos().y > 140) {
				getPos().y = 140;
				vel.y = -vel.y;
			}
		}
	}
	
	// Returns the started boolean
	public Boolean isStart() {
		return started;
	}
		
	// Returns the velocity
	public Vector3f getVel() {
		return vel;
	}

	// Sets the velocity
	public void setVel(Vector3f v) {
		vel = v;
	}

	// Sets the attribute pointers
	public void setAttributePoints(int pos, int norm, int col) {
		// First the position
		vertexData.position(0);
		glVertexAttribPointer(pos, 3, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(pos);
			
		// Then the normals
		vertexData.position(3);
		glVertexAttribPointer(norm, 3, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(norm);
			
		// Finally the colour
		vertexData.position(6);
		glVertexAttribPointer(col, 4, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(col);
		vertexData.position(0);
	}

	// Draws the ball using two triangle fans
	public void draw() {
		glDrawArrays(GL_TRIANGLE_FAN, 0, 10);
		glDrawArrays(GL_TRIANGLE_FAN, 11, 10);
	}


}
