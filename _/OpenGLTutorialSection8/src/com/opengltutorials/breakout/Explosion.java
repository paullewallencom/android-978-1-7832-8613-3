package com.opengltutorials.breakout;

import java.util.Random;

public class Explosion {
	// The particle shader program
	private ParticleShaderProgram program;
	// The particle system
	private ParticleSystem system;
	// The launcher for the particles
	private ParticleEmitter launcher;
		
	// The start position
	private Vector3f startPos;
	// The start time
	private long startTime;
	
	// The random variable for the direction
	private Random randDir;

	// Constructor - sets the start position and initializes the random direction
	public Explosion(Vector3f position) {
		startPos = position;
		randDir = new Random();
	}

	// On create function - create the particle system
	public void onCreate() {
		// Start the shader program
		program = new ParticleShaderProgram();
		// Initialise the particle system with 200 particles
		system = new ParticleSystem(200);
		// Set the start time
		startTime = System.nanoTime();

		// Initialize the launcher
		launcher = new ParticleEmitter(startPos, new Vector3f(0, 1, 0));
	}
	
	// Render the explosion
	public void render(Matrix4f WVP) {
		// Get the current time
		float curTime = (System.nanoTime() - startTime) / 100000000f; 
		// Get the direction for the particle
		Vector3f newDir = new Vector3f((randDir.nextFloat() - 0.5f), 1.0f, (randDir.nextFloat() - 0.5f));
		// Add the particle to the launcher
		launcher.addParticle(system, newDir, curTime, 5);

		// Use the shader program
		program.useProgram();
		// Set the uniform attributes
		program.setUniform(WVP, curTime);
		// Bind the program
		system.bind(program);
		// Draw the particles
		system.draw();
	}

}
