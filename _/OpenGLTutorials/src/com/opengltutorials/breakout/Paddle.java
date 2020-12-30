package com.opengltutorials.breakout;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Paddle extends GameEntity {
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
	private FloatBuffer vertexData;
	private static float XLIMIT = 70.0f; 

	private float velX = 0;
	
	public Paddle(Vector3f p) {
		super(p, 40, 10);
			
		vertexData = ByteBuffer.allocateDirect(vertices.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexData.put(vertices);
	}
	
	public void setVelX(float x) {
		velX = x;
	}
	
	public void update(float elapsedTime) {
		float nextX = getPos().x - ((velX * 15) * elapsedTime);
		if(nextX < XLIMIT && nextX > -XLIMIT ) {
			getPos().x = nextX;
		}
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
		glDrawArrays(GL_TRIANGLES, 0, 36);
	}
}
