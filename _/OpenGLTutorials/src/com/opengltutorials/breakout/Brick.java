package com.opengltutorials.breakout;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Brick extends GameEntity {
	
	private static final float[] vertices = new float[] {
		10f, -10f, 120.0f,		0.0f, 0.0f, 1.0f,		0.0f, 1.0f,
		-10f, -10f, 120.0f, 	0.0f, 0.0f, 1.0f,		1.0f, 1.0f,
		-10f, 10f, 120.0f,		0.0f, 0.0f, 1.0f,		0.0f, 0.0f,
		-10f, 10f, 120.0f,		0.0f, 0.0f, 1.0f,		0.0f, 0.0f,
		10f, 10f, 120.0f, 		0.0f, 0.0f, 1.0f, 		1.0f, 1.0f,
		10f, -10f, 120.0f, 		0.0f, 0.0f, 1.0f,		1.0f, 0.0f,
		
		-10f, -10f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 1.0f,
		10f, -10f, 100.0f, 		0.0f, 0.0f, -1.0f,		1.0f, 1.0f,
		-10f, 10f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f,
		-10f, 10f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f,
		10f, -10f, 100.0f, 		0.0f, 0.0f, -1.0f, 		1.0f, 1.0f,
		10f, 10f, 100.0f, 		0.0f, 0.0f, -1.0f,		1.0f, 0.0f,

		10f,  10f, 120.0f,		1.0f, 0.0f, 0.0f,		0.0f, 1.0f,
		10f, -10f, 120.0f, 		1.0f, 0.0f, 0.0f,		1.0f, 1.0f,
		10f, -10f,  100.0f,		1.0f, 0.0f, 0.0f,		0.0f, 0.0f,
		10f, -10f,  100.0f,		1.0f, 0.0f, 0.0f,		0.0f, 0.0f,
		10f,  10f,  100.0f, 	1.0f, 0.0f, 0.0f, 		1.0f, 1.0f,
		10f,  10f, 120.0f, 		1.0f, 0.0f, 0.0f,		1.0f, 0.0f,
		
		-10f, -10f, 100.0f,		-1.0f, 0.0f, 0.0f,		0.0f, 1.0f,
		-10f, 10f, 100.0f, 		-1.0f, 0.0f, 0.0f,		1.0f, 1.0f,
		-10f, -10f, 120.0f,		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f,
		-10f, -10f, 120.0f,		-1.0f, 0.0f, 0.0f,		0.0f, 0.0f,
		-10f, 10f, 100.0f, 		-1.0f, 0.0f, 0.0f, 		1.0f, 1.0f,
		-10f, 10f,  120.0f, 	-1.0f, 0.0f, 0.0f,		1.0f, 0.0f,
		
		-10f, 10f, 100.0f,		0.0f, 1.0f, 0.0f,		0.0f, 1.0f,
		10f,  10f, 100.0f, 		0.0f, 1.0f, 0.0f,		1.0f, 1.0f,
		-10f, 10f,  120.0f,		0.0f, 1.0f, 0.0f,		0.0f, 0.0f,
		-10f, 10f,  120.0f,		0.0f, 1.0f, 0.0f,		0.0f, 0.0f,
		10f,  10f, 100.0f, 		0.0f, 1.0f, 0.0f, 		1.0f, 1.0f,
		10f,  10f, 120.0f, 		0.0f, 1.0f, 0.0f,		1.0f, 0.0f,
		
		10f, -10f, 100.0f,		0.0f, -1.0f, 0.0f,		0.0f, 1.0f,
		-10f, -10f, 100.0f, 	0.0f, -1.0f, 0.0f,		1.0f, 1.0f,
		-10f, -10f, 120.0f,		0.0f, -1.0f, 0.0f,		0.0f, 0.0f,
		-10f, -10f, 120.0f,		0.0f, -1.0f, 0.0f,		0.0f, 0.0f,
		 10f, -10f, 120.0f,  	0.0f, -1.0f, 0.0f,  	1.0f, 1.0f,
		10f, -10f, 100.0f, 		0.0f, -1.0f, 0.0f,		1.0f, 0.0f,
	};

	private FloatBuffer vertexData;
	
	public Brick(Vector3f pos) {
		super(pos, 20, 20);
		vertexData = ByteBuffer.allocateDirect(vertices.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexData.put(vertices);
	}
	
	public void setAttributePoints(int pos, int norm, int tex) {
		vertexData.position(0);
		glVertexAttribPointer(pos, 3, GL_FLOAT, false, 32, vertexData);
		glEnableVertexAttribArray(pos);
			
		vertexData.position(3);
		glVertexAttribPointer(norm, 3, GL_FLOAT, false, 32, vertexData);
		glEnableVertexAttribArray(norm);
			
		vertexData.position(6);
		glVertexAttribPointer(tex, 2, GL_FLOAT, false, 32, vertexData);
		glEnableVertexAttribArray(tex);
		vertexData.position(0);
	}

	
	public void draw(Matrix4f wvp) {
		if(!isDead()) {
			glDrawArrays(GL_TRIANGLES, 0, 36);
		} 
	}

}
