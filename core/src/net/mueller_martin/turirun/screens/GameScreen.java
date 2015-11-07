package net.mueller_martin.turirun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import net.mueller_martin.turirun.Constants;
import net.mueller_martin.turirun.ScreenManager;
import net.mueller_martin.turirun.Turirun;
import net.mueller_martin.turirun.WorldController;

public class GameScreen extends ScreenAdapter {

	public final static String TAG = GameScreen.class.getName();
	Turirun game;
	WorldController world;
	ScreenManager screenManager;

	public GameScreen (ScreenManager s, Turirun game) {
		this.game = game;
		this.world = new WorldController(game);
		this.screenManager = s;
	}

	public void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.draw(game.batch);
	}

	public void update(float deltaTime) {
		world.update(deltaTime);

		// Back To Menu
		if(Gdx.input.isButtonPressed(Input.Keys.ESCAPE))
		{
			Gdx.app.log(TAG," esc pressed - back to main Menu");
			screenManager.setScreenState(Constants.MENUSCREEN);
		}
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}
}
