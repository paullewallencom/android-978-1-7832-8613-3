package com.android.opengltutorialsection1;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	// Sound pool to load and play the sounds
	private SoundPool mSoundPool;
	// Holds the sounds
	private HashMap mSoundPoolMap;
	// Gets the maximum volume
	private AudioManager mAudioManager;
	// The application context
	private Context mContext;

	// Initializes the sound manager
	public void initSounds(Context context) {
		mContext = context;
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap();
		mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	// Adds a sound to the sound manager
	public void addSound(int index, int soundID) {
		mSoundPoolMap.put(index, mSoundPool.load(mContext,soundID, 1));
	}
	
	// Plays a sound once
	public void playSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
	}
	
	// Plays a looped sound
	public void playLoopedSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}


}
