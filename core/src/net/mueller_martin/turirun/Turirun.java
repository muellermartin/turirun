package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mueller_martin.turirun.screens.GameScreen;

public class Turirun extends Game
{
	public SpriteBatch batch;
	ScreenManager screenManager;
	String host = "127.0.0.1";
	int port = 1337;
	String nickname = "Unnamed";
	String winner = "";

	@Override
	public void create () {
		batch = new SpriteBatch();
		// Load Settings
		// Load Assets
		AssetOrganizer.instance.init(new AssetManager());
		MusicBox.instance.init(new MusicBox());
		screenManager = new ScreenManager(this);
		screenManager.setScreenState(Constants.MENUSCREEN);
	}

	@Override
	public void render () {
		super.render();
		CameraHelper.instance.update();
	}
}
