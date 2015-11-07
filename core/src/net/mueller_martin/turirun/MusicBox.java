package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class MusicBox implements IAudioInterface
{
	
	public final static String TAG = MusicBox.class.getName();
	private boolean audio;
	Sound sound;
	public MusicBox()
	{
		updateAudio();
		Gdx.app.log(TAG,"audio: "+audio);
	}

	@Override
	public void playSound(int x)
	{
		if(audio)
		{
			switch(x)
			{
			case Constants.AUDIO_CANNIBALEAT:
				AssetOrganizer.instance.audio_soundTrack.audio_soundTrack.play(0.7f);
			break;
			case Constants.AUDIO_CANNIBALWIN:
				AssetOrganizer.instance.audio_cannibalsWin.audio_cannibalsWin.play(0.9f);
			break;
			case Constants.AUDIO_RUNNING1:
				AssetOrganizer.instance.audio_running1.audio_running1.play(0.9f);
				break;
			case Constants.AUDIO_WALKING1:
				AssetOrganizer.instance.audio_walking1.audio_walking1.play(0.9f);
				break;
			case Constants.AUDIO_WALKING2:
				AssetOrganizer.instance.audio_walking2.audio_walking2.play(0.9f);
				break;
			case Constants.AUDIO_SOUNDTRACK:
				AssetOrganizer.instance.audio_soundTrack.audio_soundTrack.play(0.9f);
				break;

			}
		}
	}

	@Override
	public void updateAudio() {

	}

	public void resume()
	{
		updateAudio();
	}


}
