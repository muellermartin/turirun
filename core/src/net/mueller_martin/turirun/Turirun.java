package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Turirun extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// Load Settings
		// Load Assets

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
