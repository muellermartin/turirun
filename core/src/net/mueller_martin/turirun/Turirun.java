package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mueller_martin.turirun.screens.GameScreen;

public class Turirun extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// Load Settings
		// Load Assets

		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
		CameraHelper.instance.update();
	}
}
