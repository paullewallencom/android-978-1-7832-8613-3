package com.opengltutorials.breakout;

import java.util.ArrayList;
import java.util.List;

import android.util.FloatMath;

public class SpatialHashGrid {
	List<GameEntity>[] staticCells;
	List<GameEntity>[] dynamicCells;
	int cellsInRow;
	int cellsInCol;
	float cellSize;
	int[] cellID = new int[4];
	List<GameEntity> foundObj;
	float widthOffset;
	float heightOffset;
	
	public SpatialHashGrid(float width, float height, float size, float widthOffset, float heightOffset) {
		this.cellSize = size;
		this.cellsInRow = (int) Math.ceil(width/size);
		this.cellsInCol = (int) Math.ceil(height/size);
		int numOfCells = cellsInRow * cellsInCol;
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

	public void insertStaticEnt(GameEntity ent) {
		int[] cellIDs = getCellIDs(ent);
		int i = 0;
		int cellID = -1;
		while(i<=3 && (cellID = cellIDs[i++]) != -1) {
			staticCells[cellID].add(ent);
		}
	}

	public void insertDynamicEnt(GameEntity ent) {
		int[] cellIDs = getCellIDs(ent);
		int i = 0;
		int cellID = -1;
		while(i<=3 && (cellID = cellIDs[i++])  != -1) {
			dynamicCells[cellID].add(ent);
		}
	}

	public void removeStaticEnt(GameEntity ent) {
		int[] cellIDs = getCellIDs(ent);
		int i=0;
		int cellID = -1;
		while(i<=3 && (cellID = cellIDs[i++]) != -1) {
			staticCells[cellID].remove(ent);
		}
	}

	public void clearDynamicCells() {
		int len = dynamicCells.length;
		for(int i=0; i<len; i++) {
			dynamicCells[i].clear();
		}
	}

	public int[] getCellIDs(GameEntity ent) {
		float entLowerX = ((ent.getPos().x + widthOffset) - (ent.getWidth() / 2)) ;
		float entLowerY = ((ent.getPos().y+ heightOffset) - (ent.getHeight() / 2)) ;
		int x1 = (int)Math.floor(entLowerX / cellSize);
		int y1 = (int)Math.floor(entLowerY / cellSize);
		int x2 = (int)Math.floor(entLowerX  + ent.getWidth());
		int y2 = (int)Math.floor(entLowerY  + ent.getHeight());
		
		if(x1 == x2 && y1 == y2) {
			if(x1 >= 0 && x1 < cellsInRow && y1 >=0 && y1 < cellsInCol){
				cellID[0] = x1 + y1 * cellsInRow;
			}
			else {
				cellID[0] = -1;
			}
			cellID[1] = -1;
			cellID[2] = -1;
			cellID[3] = -1;
		} else if(x1 == x2) {
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
		} else if(y1 == y2) {
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
		} else {
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
	
	public List<GameEntity> getPossibleStaticColliders(GameEntity ent) {
		foundObj.clear();
		int[] cellIDs = getCellIDs(ent);
		int i=0;
		int cellID = -1;
		while (i <= 3 && (cellID = cellIDs[i++]) != -1) {
			int len = staticCells[cellID].size();
			for(int j=0; j<len; j++) {
				GameEntity collide = staticCells[cellID].get(j);
				if(!foundObj.contains(collide))
					foundObj.add(collide);
			}
		}
		return foundObj;
	}

	public List<GameEntity> getPossibleDynamicColliders(GameEntity ent) {
		foundObj.clear();
		int[] cellIDs = getCellIDs(ent);
		int i=0;
		int cellID = -1;
		while (i <= 3 && (cellID = cellIDs[i++]) != -1) {
			int len = dynamicCells[cellID].size();
			for(int j=0; j<len; j++) {
				GameEntity collide = dynamicCells[cellID].get(j);
				if(!foundObj.contains(collide))
					foundObj.add(collide);
			}
		}
		return foundObj;
	}


	
}
