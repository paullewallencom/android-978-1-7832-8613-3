package com.opengltutorials.breakout;

import android.content.Context;
import android.content.SharedPreferences;

public class Highscore {
	private SharedPreferences prefs;
	private int[] scores;
	private String[] names;

	public Highscore(Context context) {
		prefs = context.getSharedPreferences("Highscores", 0);
		scores = new int[10];
		names = new String[10];
			
		for(int i=0; i<10; i++) {
			scores[i] = prefs.getInt("score"+i, 0);
			names[i] = prefs.getString("name"+i, "AAA");
		}
	}

	public int getScore(int s) {
		return scores[s];
	}
		
	public String getName(int s) {
		return names[s];
	}
	
	public boolean isHighscore(int score) {
		int p;
		for(p=0; p<10 && this.scores[p] > score; p++);
				
		if(p==10)
			return false;
				
		return true;
	}

	public void resetScore() {
		SharedPreferences.Editor editor = prefs.edit();
		for(int i=0; i<10; i++) {
			editor.putInt("score"+i, 0);
			editor.putString("name"+i, "AAA");
		}
		editor.commit();
	}

	public boolean addScore(int score, String name) {
		int p;
		for(p=0; p<10 &&this.scores[p] > score; p++);
		if(p==10)
			return false;
			
		for(int x=9; x>p; x--){
			this.scores[x] = this.scores[x-1];
			this.names[x] = this.names[x-1];
		}
				
		this.scores[p] = score;
		this.names[p] = name;
				
		SharedPreferences.Editor editor = prefs.edit();
		for(int x=0; x<10; x++) {
			editor.putInt("score"+x, this.scores[x]);
			editor.putString("name"+x, this.names[x]);
		}
		editor.commit();
		return true;
	}


}
