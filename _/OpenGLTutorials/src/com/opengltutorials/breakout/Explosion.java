package com.opengltutorials.breakout;

import java.util.Random;

public class Explosion {
	private ParticleShaderProgram program;
	private ParticleSystem system;
	private ParticleEmitter launcher;
		
	private Vector3f startPos;
	private long startTime;
	
	private Random randDir;

	public Explosion(Vector3f position) {
		startPos = position;
		randDir = new Random();
	}

	public void onCreate() {
		program = new ParticleShaderProgram();
		system = new ParticleSystem(200);
		startTime = System.nanoTime();

		launcher = new ParticleEmitter(startPos, new Vector3f(0, 1, 0));
	}
	
	public void render(Matrix4f WVP) {
		float curTime = (System.nanoTime() - startTime) / 100000000f; 
		Vector3f newDir = new Vector3f((randDir.nextFloat() - 0.5f), 1.0f, (randDir.nextFloat() - 0.5f));
		launcher.addParticle(system, newDir, curTime, 5);

			
		program.useProgram();
		program.setUniform(WVP, curTime);
		system.bind(program);
		system.draw();
	}

}
