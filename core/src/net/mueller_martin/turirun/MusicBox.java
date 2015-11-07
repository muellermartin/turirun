package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class MusicBox
{
	
	public final static String TAG = MusicBox.class.getName();
	private boolean audio;
	Sound sound;
	Sound bgSound;
	public static final MusicBox instance = new MusicBox();
	private MusicBox assetOrganizer;


	public MusicBox()
	{
		audio = true;

		Gdx.app.log(TAG,"audio: "+audio);

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
					AssetOrganizer.instance.audio_Cheering.audio_Cheering.play(0.9f);
					break;

				case Constants.AUDIO_HELICOPTER:
					AssetOrganizer.instance.audio_Helicopter.audio_Helicopter.play(0.9f);
					break;

				case Constants.AUDIO_CAMERASOUND:
					AssetOrganizer.instance.audio_CameraSound.audio_CameraSound.play(0.9f);
					break;

				case Constants.AUDIO_LOOP1:
					AssetOrganizer.instance.audio_loop1.audio_loop1.play(0.9f);
					break;
				case Constants.AUDIO_LOOP2:
					AssetOrganizer.instance.audio_loop2.audio_loop2.play(0.9f);
					break;

				case Constants.AUDIO_LOOP3:
					AssetOrganizer.instance.audio_loop3.audio_loop3.play(0.9f);
					break;

				case Constants.AUDIO_LOOP4:
					AssetOrganizer.instance.audio_loop4.audio_loop4.play(0.9f);
					break;


				case Constants.AUDIO_LOOP5:
					AssetOrganizer.instance.audio_loop5.audio_loop5.play(0.9f);
					break;


				case Constants.AUDIO_LOOP6:
					AssetOrganizer.instance.audio_loop6.audio_loop6.play(0.9f);
					break;


			}


		}
	}

	public long loopTheme(int x)
	{
		sound = null;
		long id = 0;
		switch(x)
		{
			case Constants.AUDIO_CANNIBALEAT:
				sound = AssetOrganizer.instance.audio_soundTrack.audio_soundTrack;
				break;
			case Constants.AUDIO_CANNIBALWIN:
				sound = AssetOrganizer.instance.audio_cannibalsWin.audio_cannibalsWin;
				break;
			case Constants.AUDIO_RUNNING1:
				sound = AssetOrganizer.instance.audio_running1.audio_running1;
				break;
			case Constants.AUDIO_WALKING1:
				sound = AssetOrganizer.instance.audio_walking1.audio_walking1;
				break;
			case Constants.AUDIO_WALKING2:
				sound = AssetOrganizer.instance.audio_walking2.audio_walking2;
				break;
			case Constants.AUDIO_SOUNDTRACK:
				sound = AssetOrganizer.instance.audio_soundTrack.audio_soundTrack;
				break;
			case Constants.AUDIO_MENUSELECT:
				sound = AssetOrganizer.instance.audio_MenuSelect.audio_MenuSelect;
				break;
			case Constants.AUDIO_MENUSWITCH:
				sound = AssetOrganizer.instance.audio_MenuSwitch.audio_MenuSwitch;
				break;

			case Constants.AUDIO_CHEERING:
				sound = AssetOrganizer.instance.audio_Cheering.audio_Cheering;
				break;

			case Constants.AUDIO_HELICOPTER:
				sound = AssetOrganizer.instance.audio_Helicopter.audio_Helicopter;
				break;

			case Constants.AUDIO_CAMERASOUND:
				sound = AssetOrganizer.instance.audio_CameraSound.audio_CameraSound;
				break;

			case Constants.AUDIO_LOOP1:
				sound = AssetOrganizer.instance.audio_loop1.audio_loop1;
				break;

			case Constants.AUDIO_LOOP2:
				sound = AssetOrganizer.instance.audio_loop2.audio_loop2;
				break;

			case Constants.AUDIO_LOOP3:
				sound = AssetOrganizer.instance.audio_loop3.audio_loop3;
				break;

			case Constants.AUDIO_LOOP4:
				sound = AssetOrganizer.instance.audio_loop4.audio_loop4;
				break;


			case Constants.AUDIO_LOOP5:
				sound = AssetOrganizer.instance.audio_loop5.audio_loop5;
				break;


			case Constants.AUDIO_LOOP6:
				sound = AssetOrganizer.instance.audio_loop6.audio_loop6;
				break;


		}


		id = sound.play(0.9f);
		Gdx.app.log(TAG,"id: "+id);
		sound.setLooping(id, true); // keeps the sound looping

		return id;
	}
	public void stopId(long x)
	{
		Gdx.app.log(TAG,"X: "+x);
		if(sound == null)
		{
			Gdx.app.log(TAG,"sound null ");
		}
		sound.stop(x);
		sound.dispose();

	}



	public void init(MusicBox musicBox) {

	}
}
