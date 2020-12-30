package com.android.opengltutorialsection1;

import java.util.Random;

public class ParticleEmitter {
	// Position of the emitter
	public Vector3f pos;
	// Colour of the particles
	private final Vector3f[] col = {
			new Vector3f(1.0f, 0.0f, 0.0f),
			new Vector3f(1.0f, 0.25f, 0.0f),
			new Vector3f(1.0f, 0.35f, 0.0f),
			new Vector3f(1.0f, 0.5f, 0.0f),
			new Vector3f(1.0f, 0.6f, 0.0f),
			new Vector3f(1.0f, 0.75f, 0.0f)
		};
	// Random for the colours
	private Random randCol;
	
	// Direction of the emitter
	public Vector3f dir;
	
	// Constructor that initializes the position and random colour
	public ParticleEmitter(Vector3f pos, Vector3f col) {
		this.pos = pos;
		randCol = new Random();
	}

	// Adds a particle to the particle system
	public void addParticle(ParticleSystem system, Vector3f dir, float curTime, int max) {
		for(int i=0; i<max; i++) {
			// Set the colour based on the random
			int curCol = randCol.nextInt(6);

			// Add the particle to the particle system
			system.addParticle(pos, col[curCol], dir, curTime);

		}
	}

}
