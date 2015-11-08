package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class MusicBox
{
	
	public final static String TAG = MusicBox.class.getName();
	private boolean audio;
	Sound sound;
	Music music;
	boolean jukeBoxPlay;
	public static final MusicBox instance = new MusicBox();
	int[] musicArray = {Constants.AUDIO_LOOP1,Constants.AUDIO_LOOP2,Constants.AUDIO_LOOP3,Constants.AUDIO_LOOP4,Constants.AUDIO_LOOP5,Constants.AUDIO_LOOP6};
	private MusicBox assetOrganizer;


	public MusicBox()
	{
		audio = true;
		jukeBoxPlay = false;


	}

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

				case Constants.AUDIO_MENUSELECT:
					AssetOrganizer.instance.audio_MenuSelect.audio_MenuSelect.play(0.9f);
					break;
				case Constants.AUDIO_MENUSWITCH:
					AssetOrganizer.instance.audio_MenuSwitch.audio_MenuSwitch.play(0.9f);
					break;
				case Constants.AUDIO_CHEERING:
					AssetOrganizer.instance.audio_Cheering.audio_Cheering.play(0.8f);
					break;

				case Constants.AUDIO_HELICOPTER:
					AssetOrganizer.instance.audio_Helicopter.audio_Helicopter.play(0.9f);
					break;

				case Constants.AUDIO_CAMERASOUND:
					AssetOrganizer.instance.audio_CameraSound.audio_CameraSound.play(0.9f);
					break;

				case Constants.AUDIO_LOOP1:
					AssetOrganizer.instance.music_loop1.music_loop1.setLooping(false);
					AssetOrganizer.instance.music_loop1.music_loop1.play();
					break;
				case Constants.AUDIO_LOOP2:
					AssetOrganizer.instance.music_loop2.music_loop2.setLooping(false);
					AssetOrganizer.instance.music_loop2.music_loop2.play();
					break;

				case Constants.AUDIO_LOOP3:
					AssetOrganizer.instance.music_loop3.music_loop3.setLooping(false);
					AssetOrganizer.instance.music_loop3.music_loop3.play();
					break;

				case Constants.AUDIO_LOOP4:
					AssetOrganizer.instance.music_loop4.music_loop4.setLooping(false);
					AssetOrganizer.instance.music_loop4.music_loop4.play();
					break;


				case Constants.AUDIO_LOOP5:
					AssetOrganizer.instance.music_loop5.music_loop5.setLooping(false);
					AssetOrganizer.instance.music_loop5.music_loop5.play();
					break;


				case Constants.AUDIO_LOOP6:
					AssetOrganizer.instance.music_loop6.music_loop6.setLooping(false);
					AssetOrganizer.instance.music_loop6.music_loop6.play();
					break;
			}
		}
	}

	public long loopTheme(int x)
	{
		music  = null;
		long id = 0;
		switch(x)
		{

			case Constants.AUDIO_LOOP1:
				music = AssetOrganizer.instance.music_loop1.music_loop1;
				break;

			case Constants.AUDIO_LOOP2:
				music = AssetOrganizer.instance.music_loop2.music_loop2;
				break;

			case Constants.AUDIO_LOOP3:
				music = AssetOrganizer.instance.music_loop3.music_loop3;
				break;

			case Constants.AUDIO_LOOP4:
				music = AssetOrganizer.instance.music_loop4.music_loop4;
				break;


			case Constants.AUDIO_LOOP5:
				music = AssetOrganizer.instance.music_loop5.music_loop5;
				break;


			case Constants.AUDIO_LOOP6:
				music = AssetOrganizer.instance.music_loop6.music_loop6;
				break;

			case Constants.AUDIO_REMIX:
				music = AssetOrganizer.instance.music_remix.music_remix;
				break;
		}


		music.play();
		Gdx.app.log(TAG,"id: "+id);
		music.setLooping(true);



		return id;
	}
	public void stopId(long x)
	{
		Gdx.app.log(TAG, "X: " + x);
		if(sound == null)
		{
			Gdx.app.log(TAG,"sound null ");
		}
		else
		{
			sound.stop(x);
		}
	}

	public void stopMusic(int y)
	{
		switch(y)
		{


		case Constants.AUDIO_LOOP1:
		AssetOrganizer.instance.music_loop1.music_loop1.stop();;
		break;

		case Constants.AUDIO_LOOP2:
		AssetOrganizer.instance.music_loop2.music_loop2.stop();;
		break;

		case Constants.AUDIO_LOOP3:
		AssetOrganizer.instance.music_loop3.music_loop3.stop();;
		break;

		case Constants.AUDIO_LOOP4:
		AssetOrganizer.instance.music_loop4.music_loop4.stop();;
		break;


		case Constants.AUDIO_LOOP5:
		AssetOrganizer.instance.music_loop5.music_loop5.stop();
		break;


		case Constants.AUDIO_LOOP6:
		AssetOrganizer.instance.music_loop6.music_loop6.stop();
		break;

		case Constants.AUDIO_REMIX:
		AssetOrganizer.instance.music_remix.music_remix.stop();
		break;
		}
	}
	public void jukebox()
	{
		if(!checkIfMusicPlays())
		{
			Gdx.app.log(TAG, "new Loop");
			int a = MathUtils.random(0,5);
			Gdx.app.log(TAG,"RANDOM MATH: "+a);
			this.playSound(musicArray[a]);
		}
	}

	public boolean checkIfMusicPlays()
	{
		boolean ret = false;

		if(AssetOrganizer.instance.music_loop1.music_loop1.isPlaying())
		{
			ret = true;
		}
		if(AssetOrganizer.instance.music_loop2.music_loop2.isPlaying())
		{
			ret = true;
		}
		if(AssetOrganizer.instance.music_loop3.music_loop3.isPlaying())
		{
			ret = true;
		}
		if(AssetOrganizer.instance.music_loop4.music_loop4.isPlaying())
		{
			ret = true;
		}
		if(AssetOrganizer.instance.music_loop5.music_loop5.isPlaying())
		{
			ret = true;
		}
		if(AssetOrganizer.instance.music_loop6.music_loop6.isPlaying())
		{
			ret = true;
		}
	return ret;

	}
	public void stopJukeBox()
	{
		this.jukeBoxPlay = false;
	}



	public void init(MusicBox musicBox) {

	}
}
