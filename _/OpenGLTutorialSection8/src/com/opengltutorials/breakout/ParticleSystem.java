package com.opengltutorials.breakout;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;

public class ParticleSystem {
	// The stride is the jump between each vertex multiplied the number of bytes in a float.
	private static final int STRIDE = 40 ;
	// The number of bytes in a float
	private static final int NUMFLOATS = 10;	

	// The array of particles
	private final float[] particles;
	// The maximum number of particles
	private final int maxParticles;
	// The current number of particles
	private int curNumParticles;
	// The next particle
	private int nextParticle;
		
	// The float buffer
	private FloatBuffer floatbuffer;

	// The constructor for the particle system
	public ParticleSystem(int max) {
		// Initialize the particles
		particles = new float[max * NUMFLOATS ];
		// Set the max number of particles
		maxParticles = max;
		// Initialize the float buffer
		floatbuffer = ByteBuffer.allocateDirect(particles.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
	}

	// Add the particle 
	public void addParticle(Vector3f pos, Vector3f col, Vector3f dir, float start) {
		// If we haven't reached the maximum
		if(curNumParticles < maxParticles) {
			// Set the offset for the attributes of the particle
			final int offset = nextParticle * 10;
			int curOffset = offset;
			nextParticle++;
			curNumParticles++;
			
			// Set the position
			particles[curOffset++ ] = pos.x;
			particles[curOffset++] = pos.y;
			particles[curOffset++] = pos.z;
					
			// Set the direction
			particles[curOffset++] = dir.x;
			particles[curOffset++] = dir.y;
			particles[curOffset++] = dir.z;
					
			// Set the colour
			particles[curOffset++] = col.x;
			particles[curOffset++] = col.y;
			particles[curOffset++] = col.z;
					
			// Set the start time
			particles[curOffset++] = start;

			// Set the floatbuffer with the particles
			floatbuffer.position(offset);
			floatbuffer.put(particles, offset, 10);
			floatbuffer.position(0);

		}

	}
	
	// Bind the vertex data to the shader program
	public void bind(ParticleShaderProgram program) {
		// First the position
		floatbuffer.position(0);
		glVertexAttribPointer(program.getPosAttributeLocation(), 3, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getPosAttributeLocation());
			
		// Then the direction
		floatbuffer.position(3);
		glVertexAttribPointer(program.getDirectionAttributeLocation(), 3, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getDirectionAttributeLocation());
			
		// Then the colour
		floatbuffer.position(6);
		glVertexAttribPointer(program.getColAttributeLocation(), 3, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getColAttributeLocation());
			
		// Finally the start time
		floatbuffer.position(9);
		glVertexAttribPointer(program.getStartTimeAttributeLocation(), 1, GL_FLOAT, false, STRIDE, floatbuffer);
		glEnableVertexAttribArray(program.getStartTimeAttributeLocation());
	}

	// Draw the particles
	public void draw() {
		glDrawArrays(GL_POINTS, 0, curNumParticles);
	}

}
