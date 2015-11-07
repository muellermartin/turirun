package net.mueller_martin.turirun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import net.mueller_martin.turirun.Turirun;
import net.mueller_martin.turirun.WorldController;

public class GameScreen extends ScreenAdapter {
	Turirun game;
	WorldController world;

	Texture img;

	public GameScreen (Turirun game) {
		this.game = game;
		this.world = new WorldController(game);

		img = new Texture("badlogic.jpg");
	}

	public void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(img, 0, 0);
		game.batch.end();
		world.draw(game.batch);
	}

	public void update(float deltaTime) {
		world.update(deltaTime);
	}

	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}
}