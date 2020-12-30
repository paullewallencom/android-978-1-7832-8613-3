package com.opengltutorials.breakout;

import java.util.Random;

public class ParticleEmitter {
	public Vector3f pos;
	private final Vector3f[] col = {
			new Vector3f(1.0f, 0.0f, 0.0f),
			new Vector3f(1.0f, 0.25f, 0.0f),
			new Vector3f(1.0f, 0.35f, 0.0f),
			new Vector3f(1.0f, 0.5f, 0.0f),
			new Vector3f(1.0f, 0.6f, 0.0f),
			new Vector3f(1.0f, 0.75f, 0.0f)
		};
	private Random randCol;
	
	public Vector3f dir;
	
	public ParticleEmitter(Vector3f pos, Vector3f col) {
		this.pos = pos;
		randCol = new Random();
	}

	public void addParticle(ParticleSystem system, Vector3f dir, float curTime, int max) {
		for(int i=0; i<max; i++) {
			int curCol = randCol.nextInt(6);
			
			system.addParticle(pos, col[curCol], dir, curTime);

		}
	}

}
