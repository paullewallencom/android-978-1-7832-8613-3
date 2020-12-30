package com.opengltutorials.breakout;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool mSoundPool;
	private HashMap mSoundPoolMap;
	private AudioManager mAudioManager;
	private Context mContext;

	public void initSounds(Context context) {
		mContext = context;
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		mSoundPoolMap = new HashMap();
		mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	public void addSound(int index, int soundID) {
		mSoundPoolMap.put(index, mSoundPool.load(mContext,soundID, 1));
	}

	public void playSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
	}
	
	public void playLoopedSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}


}
