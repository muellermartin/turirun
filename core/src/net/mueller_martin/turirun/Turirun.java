package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mueller_martin.turirun.screens.GameScreen;

public class Turirun extends Game
{
	public SpriteBatch batch;
	ScreenManager screenManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// Load Settings
		// Load Assets
		AssetOrganizer.instance.init(new AssetManager());
		screenManager = new ScreenManager(this);
		screenManager.setScreenState(Constants.MENUSCREEN);



	}

	@Override
	public void render () {
		super.render();
		CameraHelper.instance.update();
	}
}
