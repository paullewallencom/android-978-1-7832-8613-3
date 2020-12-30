package com.opengltutorials.breakout;

import android.content.Context;
import android.content.SharedPreferences;

public class Highscore {
	// Save file for highscore
	private SharedPreferences prefs;
	// Scores and names
	private int[] scores;
	private String[] names;

	public Highscore(Context context) {
		// Load the highscores
		prefs = context.getSharedPreferences("Highscores", 0);
		// Initialise the arrays for the scores
		scores = new int[10];
		names = new String[10];
			
		// Retrieve all the scores and names
		for(int i=0; i<10; i++) {
			scores[i] = prefs.getInt("score"+i, 0);
			names[i] = prefs.getString("name"+i, "AAA");
		}
	}

	// Get the score based on position
	public int getScore(int s) {
		return scores[s];
	}
		
	// Get the name based on position
	public String getName(int s) {
		return names[s];
	}
	
	// Checks if a score is a highscore
	public boolean isHighscore(int score) {
		int p;
		// Loop through all scores, will stop if your score is higher than saved score
		for(p=0; p<10 && this.scores[p] > score; p++);
				
		// If end of loop is reached there is no high score
		if(p==10)
			return false;
				
		return true;
	}

	// Resets the highscores
	public void resetScore() {
		// Set all scores and names to default
		SharedPreferences.Editor editor = prefs.edit();
		for(int i=0; i<10; i++) {
			editor.putInt("score"+i, 0);
			editor.putString("name"+i, "AAA");
		}
		editor.commit();
	}

	// Adds the score to the highscore table
	public boolean addScore(int score, String name) {
		int p;
		// First check if a score is a highscore
		for(p=0; p<10 &&this.scores[p] > score; p++);
		if(p==10)
			return false;
			
		// Lower the positions of all other scores
		for(int x=9; x>p; x--){
			this.scores[x] = this.scores[x-1];
			this.names[x] = this.names[x-1];
		}
				
		// Set the new highscore and name
		this.scores[p] = score;
		this.names[p] = name;
				
		// Save the new scores and names
		SharedPreferences.Editor editor = prefs.edit();
		for(int x=0; x<10; x++) {
			editor.putInt("score"+x, this.scores[x]);
			editor.putString("name"+x, this.names[x]);
		}
		editor.commit();
		return true;
	}


}
