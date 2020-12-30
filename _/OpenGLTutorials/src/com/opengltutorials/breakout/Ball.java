package com.opengltutorials.breakout;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Ball extends GameEntity{
	private Vector3f vel;
	private Boolean started;

	
	private FloatBuffer vertexData;
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

	public Ball(Vector3f p) {
		super(p, 20, 20);
		vel = new Vector3f(40f, 50f, 0.0f);
		started = false;
			
		vertexData = ByteBuffer.allocateDirect(vertices.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexData.put(vertices);
	}
	
	public void setStart(boolean start) {
		started = start;
	}
	
	public void update(float elapsedTime) {
		if(started) {
			Vector3f next = new Vector3f(vel.x, vel.y, vel.z);
			next.Mult(elapsedTime);
			getPos().add(next);

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
	
	public Boolean isStart() {
		return started;
	}
		
	public Vector3f getVel() {
		return vel;
	}

	public void setVel(Vector3f v) {
		vel = v;
	}
		
	public void setStart(Boolean b) {
		started = b;
	}

	public void setAttributePoints(int pos, int norm, int col) {
		vertexData.position(0);
		glVertexAttribPointer(pos, 3, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(pos);
			
		vertexData.position(3);
		glVertexAttribPointer(norm, 3, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(norm);
			
		vertexData.position(6);
		glVertexAttribPointer(col, 4, GL_FLOAT, false, 40, vertexData);
		glEnableVertexAttribArray(col);
		vertexData.position(0);
	}

	public void draw() {
		glDrawArrays(GL_TRIANGLE_FAN, 0, 10);
		glDrawArrays(GL_TRIANGLE_FAN, 11, 10);
	}


}
