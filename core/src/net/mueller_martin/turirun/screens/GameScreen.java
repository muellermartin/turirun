package net.mueller_martin.turirun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import net.mueller_martin.turirun.Constants;
import net.mueller_martin.turirun.MusicBox;
import net.mueller_martin.turirun.ScreenManager;
import net.mueller_martin.turirun.Turirun;
import net.mueller_martin.turirun.WorldController;
import net.mueller_martin.turirun.GUI;

public class GameScreen extends ScreenAdapter {
	public final static String TAG = GameScreen.class.getName();
	Turirun game;
	WorldController world;
	ScreenManager screenManager;
	GUI gui;

	public GameScreen (ScreenManager s, Turirun game) {
		this.game = game;
		this.world = new WorldController(game);
		this.screenManager = s;
		this.gui = new GUI(game, world);
	}

	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.draw(game.batch);
		gui.render();
	}

	public void update(float deltaTime) {
		world.update(deltaTime);
		gui.update(deltaTime);
		MusicBox.instance.jukebox();

		// Back To Menu
		/*
		if(Gdx.input.isButtonPressed(Input.Keys.ESCAPE))
		{
			Gdx.app.log(TAG," esc pressed - back to main Menu");
			screenManager.setScreenState(Constants.MENUSCREEN);
		
		}*/
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}
}
