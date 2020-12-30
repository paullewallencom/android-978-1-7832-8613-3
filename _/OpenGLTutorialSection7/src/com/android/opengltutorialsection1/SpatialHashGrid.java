package com.android.opengltutorialsection1;

import java.util.ArrayList;
import java.util.List;

import android.util.FloatMath;

public class SpatialHashGrid {
	// List of static cells
	List<GameEntity>[] staticCells;
	// List of dynamic cells
	List<GameEntity>[] dynamicCells;
	// The number of cells in a row
	int cellsInRow;
	// The number of cells in a column
	int cellsInCol;
	// The size of the cells
	float cellSize;
	// The ID's of the cells
	int[] cellID = new int[4];
	// The list of found objects
	List<GameEntity> foundObj;
	// Offset for the width and height
	float widthOffset;
	float heightOffset;
	
	// Constructor of the grid
	public SpatialHashGrid(float width, float height, float size, float widthOffset, float heightOffset) {
		this.cellSize = size;
		this.cellsInRow = (int) Math.ceil(width/size);
		this.cellsInCol = (int) Math.ceil(height/size);
		// Calculate the number of cells
		int numOfCells = cellsInRow * cellsInCol;
		// Set the dynamic and static cells
		dynamicCells = new List[numOfCells];
		staticCells = new List[numOfCells];
		this.widthOffset = widthOffset;
		this.heightOffset = heightOffset;
		for(int i=0; i<numOfCells; i++) {
			dynamicCells[i] = new ArrayList<GameEntity>(2);
			staticCells[i] = new ArrayList<GameEntity>(12);
		}
		foundObj = new ArrayList<GameEntity>(10);
	}

	// Insert the entity into static cells
	public void insertStaticEnt(GameEntity ent) {
		// Calculate the ID's
		int[] cellIDs = getCellIDs(ent);
		int i = 0;
		int cellID = -1;
		// Place the object into the cells
		while(i<=3 && (cellID = cellIDs[i++]) != -1) {
			staticCells[cellID].add(ent);
		}
	}

	// Insert the entity into dynamic cells
	public void insertDynamicEnt(GameEntity ent) {
		// Calculate the ID's
		int[] cellIDs = getCellIDs(ent);
		int i = 0;
		int cellID = -1;
		// Place the object into the right cells
		while(i<=3 && (cellID = cellIDs[i++])  != -1) {
			dynamicCells[cellID].add(ent);
		}
	}

	// Remove the static object
	public void removeStaticEnt(GameEntity ent) {
		// Calculate the ID
		int[] cellIDs = getCellIDs(ent);
		int i=0;
		int cellID = -1;
		// Remove the entity from the static cells
		while(i<=3 && (cellID = cellIDs[i++]) != -1) {
			staticCells[cellID].remove(ent);
		}
	}

	// Clear the dynamic cells
	public void clearDynamicCells() {
		int len = dynamicCells.length;
		for(int i=0; i<len; i++) {
			dynamicCells[i].clear();
		}
	}

	// Get the ID's of the cells
	public int[] getCellIDs(GameEntity ent) {
		// Get the bottom left corner of the entity
		float entLowerX = ((ent.getPos().x + widthOffset) - (ent.getWidth() / 2)) ;
		float entLowerY = ((ent.getPos().y+ heightOffset) - (ent.getHeight() / 2)) ;
		
		// Find which cell each corner is in
		int x1 = (int)Math.floor(entLowerX / cellSize);
		int y1 = (int)Math.floor(entLowerY / cellSize);
		int x2 = (int)Math.floor(entLowerX  + ent.getWidth());
		int y2 = (int)Math.floor(entLowerY  + ent.getHeight());
		
		// First check if the entity is in one cell
		if(x1 == x2 && y1 == y2) {
			if(x1 >= 0 && x1 < cellsInRow && y1 >=0 && y1 < cellsInCol){
				// The entity is in one cell
				cellID[0] = x1 + y1 * cellsInRow;
			}
			else {
				// The entity is in no cells
				cellID[0] = -1;
			}
			// The entity cannot be in any more cells
			cellID[1] = -1;
			cellID[2] = -1;
			cellID[3] = -1;
		} 
		// Else if the entity is in two cells above or below
		else if(x1 == x2) {
			int i = 0;
			if(x1 >= 0 && x1 < cellsInRow) {
				if(y1 >= 0 && y1 < cellsInCol) {
					cellID[i++] = x1 + y1 * cellsInRow;
				}
				if(y2 >= 0 && y2 < cellsInCol) {
					cellID[i++] = x1 + y2 * cellsInRow;
				}
			}
			while(i <= 3) cellID[i++] = -1;
		} 
		// Else if the entity is in two cells side by side
		else if(y1 == y2) {
			int i = 0;
			if(y1 >= 0 && y1 < cellsInCol) {
				if(x1 >= 0 && x1 < cellsInRow) {
					cellID[i++] = x1 + y1 * cellsInRow;
				}
				if(x2 >= 0 && x2 < cellsInRow) {
					cellID[i++] = x2 + y1 * cellsInRow;
				}
				while(i <= 3) {
					cellID[i++] = -1;
				}
			}
		} 
		// Else if the entity is in four different cells
		else {
			int i=0;
			int y1CellsRow = y1 * cellsInRow;
			int y2CellsRow = y2 * cellsInRow;
			if(x1 >= 0 && x1 < cellsInRow && y1>= 0 && y1 < cellsInCol) {
				cellID[i++] = x1 + y1CellsRow;
			}
			if(x2 >= 0 && x2 < cellsInRow && y1 >= 0 && y1 < cellsInCol) {
				cellID[i++] = x2 + y1CellsRow;
			}
			if(x2 >= 0 && x2 < cellsInRow && y2 >= 0 && y2 < cellsInCol) {
				cellID[i++] = x2 + y2CellsRow;
			}
			if(x1 >= 0 && x1 < cellsInRow && y2 >= 0 && y2 < cellsInCol) {
				cellID[i++] = x1 + y2CellsRow;
			}
			while(i <= 3) {
				cellID[i++] = -1;
			}
		}
		return cellID;
	}
	
	// Find the possible colliders for static objects
	public List<GameEntity> getPossibleStaticColliders(GameEntity ent) {
		// Clear the found objects
		foundObj.clear();
		// Get the ID's of the cells
		int[] cellIDs = getCellIDs(ent);
		int i=0;
		int cellID = -1;
		while (i <= 3 && (cellID = cellIDs[i++]) != -1) {
			// Find the number of found cells
			int len = staticCells[cellID].size();
			for(int j=0; j<len; j++) {
				// Check if there are any possible colliders
				GameEntity collide = staticCells[cellID].get(j);
				if(!foundObj.contains(collide))
					foundObj.add(collide);
			}
		}
		// Return the list
		return foundObj;
	}

	// Find the list of possible dynamic colliders
	public List<GameEntity> getPossibleDynamicColliders(GameEntity ent) {
		// Clear the list of found objects
		foundObj.clear();
		// Get the ID's of the cells
		int[] cellIDs = getCellIDs(ent);
		int i=0;
		int cellID = -1;
		while (i <= 3 && (cellID = cellIDs[i++]) != -1) {
			// Find the number of cells the entity is in
			int len = dynamicCells[cellID].size();
			// for each cell
			for(int j=0; j<len; j++) {
				// Add all the possible colliders
				GameEntity collide = dynamicCells[cellID].get(j);
				if(!foundObj.contains(collide))
					foundObj.add(collide);
			}
		}
		return foundObj;
	}


	
}
