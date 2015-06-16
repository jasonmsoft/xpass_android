package com.Xpass.XpassVoip.pjsua2_impl;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.Xpass.XpassVoip.R;


/**
 * Created by cdmaji1 on 2015/6/8.
 */
public class XpassAudioMgr {
	private AudioManager mAudioMgr = null;
	private Context mCtx = null;
	public static final int ERROR = -1;
	private int mOldMode = 0;
	private MediaPlayer mAudPlayer = null;

	public XpassAudioMgr(Context ct)
	{
		mCtx = ct;
		mAudioMgr = (AudioManager)mCtx.getSystemService(Context.AUDIO_SERVICE);
		mAudPlayer =  MediaPlayer.create(mCtx, R.raw.fly);
	}


	//error return ERROR, otherwise return the volume
	public int getVoiceVolume()
	{
		return mAudioMgr.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
	}

	public int getVoiceMaxVolume()
	{

		return mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
	}

	public void adjustVoiceVolumeLouder(int volume)
	{
		int maxVolume = getVoiceMaxVolume();
		int currVolume = getVoiceVolume();
		int adjustVolume = (currVolume + volume) > maxVolume?(maxVolume - currVolume): volume;


		for(int i = 0; i < adjustVolume; i++)
		{
			mAudioMgr.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE, 0);
		}
		return;
	}

	public void adjustVoiceVolumeLower(int volume)
	{
		int currVolume = getVoiceVolume();
		int adjustVolume = volume > currVolume?currVolume:volume;
		System.out.println("adjust lower volume: " + adjustVolume);
		for(int i = 0; i < adjustVolume; i++)
		{
			mAudioMgr.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_LOWER, 0);
		}
		return;
	}

	public void setCommunicationMode()
	{
		mOldMode = mAudioMgr.getMode();
		mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
		return;
	}

	public void setRingtoneMode()
	{
		mOldMode = mAudioMgr.getMode();
		mAudioMgr.setMode(AudioManager.MODE_RINGTONE);
	}

	public void restoreMode()
	{
		mAudioMgr.setMode(mOldMode);
		return;
	}


	public void openSpeaker()
	{
		if(mAudioMgr.isSpeakerphoneOn())
		{
			return;
		}
		else
		{
			mAudioMgr.setSpeakerphoneOn(true);
		}
		return;
	}

	public void closeSpeaker()
	{
		if(!mAudioMgr.isSpeakerphoneOn())
		{
			return;
		}
		else
		{
			mAudioMgr.setSpeakerphoneOn(false);
		}
		return;
	}

	public  boolean isSpeakerOn()
	{
		return mAudioMgr.isSpeakerphoneOn();
	}

	public void startPlay()
	{
		mAudPlayer.start();
	}

	public void stopPlay()
	{
		mAudPlayer.stop();
	}



}
