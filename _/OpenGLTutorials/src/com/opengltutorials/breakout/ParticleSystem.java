package com.opengltutorials.breakout;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class ParticleSystem {
	// The stride is the jump between each vertex multiplied the number of bytes in a float.
	private static final int STRIDE = 40 ;
	private static final int NUMFLOATS = 10;	

	private final float[] particles;
	private final int maxParticles;
	private int curNumParticles;
	private int nextParticle;
		
	private FloatBuffer floatbuffer;

	public ParticleSystem(int max) {
		particles = new float[max * NUMFLOATS ];
		maxParticles = max;
		floatbuffer = ByteBuffer.allocateDirect(particles.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
	}

	public void addParticle(Vector3f pos, Vector3f col, Vector3f dir, float start) {
		if(curNumParticles < maxParticles) {
			final int offset = nextParticle * 10;
			int curOffset = offset;
			nextParticle++;
			curNumParticles++;
			
			particles[curOffset++ ] = pos.x;
			particles[curOffset++] = pos.y;
			particles[curOffset++] = pos.z;
					
			particles[curOffset++] = dir.x;
			particles[curOffset++] = dir.y;
			particles[curOffset++] = dir.z;
					
			particles[curOffset++] = col.x;
			particles[curOffset++] = col.y;
			particles[curOffset++] = col.z;
					
			particles[curOffset++] = start;

			floatbuffer.position(offset);
			floatbuffer.put(particles, offset, 10);
			floatbuffer.position(0);

		}

	}
	
	public void bind(ParticleShaderProgram program) {
		floatbuffer.position(0);
		glVertexAttribPointer(program.getPosAttributeLocation(), 3, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getPosAttributeLocation());
			
		floatbuffer.position(3);
		glVertexAttribPointer(program.getDirectionAttributeLocation(), 3, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getDirectionAttributeLocation());
			
		floatbuffer.position(6);
		glVertexAttribPointer(program.getColAttributeLocation(), 3, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getColAttributeLocation());
			
		floatbuffer.position(9);
		glVertexAttribPointer(program.getStartTimeAttributeLocation(), 1, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getStartTimeAttributeLocation());
	}

	public void draw() {
		glDrawArrays(GL_POINTS, 0, curNumParticles);
	}

}
