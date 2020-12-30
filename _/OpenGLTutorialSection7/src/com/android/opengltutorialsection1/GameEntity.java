package com.android.opengltutorialsection1;

// Base class for game objects, such as the ball
public abstract class GameEntity {
	// Position of the entity
	private Vector3f position;
	// Width of bounding box
	private float width;
	// Height of bounding box
	private float height;
	// Boolean for whether the object is dead
	private boolean dead;
	
	// Constructor for game entity, initializes variables
	public GameEntity(Vector3f pos, float w, float h) {
		position = pos;
		width = w;
		height = h;
		dead = false;
	}
	
	// Returns the position
	public Vector3f getPos() {
		return position;
	}
	
	// Sets the position
	public void setPos(Vector3f p) {
		position = p;
	}
	
	// Returns the width of the bounding box
	public float getWidth() {
		return width;
	}
	
	// Returns the height of the bounding box
	public float getHeight() {
		return height;
	}
	
	// Returns the dead boolean
	public boolean isDead() {
		return dead;
	}
	
	// Sets the dead boolean
	public void setDead(boolean d) {
		dead = d;
	}
}
