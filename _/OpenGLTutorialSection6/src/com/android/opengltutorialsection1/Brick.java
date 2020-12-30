package com.android.opengltutorialsection1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class Brick extends GameEntity {
	
	// Vertices for the brick in the format:  PosX, PosY, PosZ,	NormalX, NormalY, NormalZ,	U, V
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

	// Float buffer for the vertices
	private FloatBuffer vertexData;
	
	// Constructor of the brick - sets the initial attributes and initializes the float buffer
	public Brick(Vector3f pos) {
		super(pos, 20, 20);
		vertexData = ByteBuffer.allocateDirect(vertices.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexData.put(vertices);
	}
	
	// Sets the attribute pointers
	public void setAttributePoints(int pos, int norm, int tex) {
		// First the position
		vertexData.position(0);
		glVertexAttribPointer(pos, 3, GL_FLOAT, false, 32, vertexData);
		glEnableVertexAttribArray(pos);
			
		// Then the normals
		vertexData.position(3);
		glVertexAttribPointer(norm, 3, GL_FLOAT, false, 32, vertexData);
		glEnableVertexAttribArray(norm);
			
		// Finally the texture coordinates
		vertexData.position(6);
		glVertexAttribPointer(tex, 2, GL_FLOAT, false, 32, vertexData);
		glEnableVertexAttribArray(tex);
		vertexData.position(0);
	}

	// This draws the brick if it has not been destroyed
	public void draw(Matrix4f wvp) {
		if(!isDead()) {
			glDrawArrays(GL_TRIANGLES, 0, 36);
		} 
	}

}
