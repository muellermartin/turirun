package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameOverScreen extends ScreenAdapter {
	public final static String TAG = GameOverScreen.class.getName();
	ScreenManager screenManager;
	Stage stage;
	Table table;
	String winner;
	SpriteBatch batch;

	public GameOverScreen(ScreenManager s) {
		this.screenManager = s;

		// Load winner string from main class
		winner = s.game.winner;

		MusicBox.instance.loopTheme(Constants.AUDIO_LOOP1);
		stage = new Stage();
		table = new Table();
		BitmapFont font = new BitmapFont();
		BaseDrawable dummy = new BaseDrawable(); // Replaces cursor, background etc.

		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label gameOverLabel = new Label("Game Over", labelStyle);
		Label winnerLabel = new Label(winner + " win", labelStyle);

		TextButtonStyle textButtonStyle = new TextButtonStyle(dummy, dummy, dummy, font);
		TextButton quitButton = new TextButton("Quit", textButtonStyle);
		TextButton menuButton = new TextButton("Menu", textButtonStyle);

		batch = new SpriteBatch();

		table.setFillParent(true);
		stage.addActor(table);

		table.row();
		table.add(gameOverLabel).expand().colspan(2);
		table.row();
		table.add(winnerLabel).expand().top().colspan(2);
		table.row();
		table.add(quitButton).minHeight(100);
		table.add(menuButton);

		quitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MusicBox.instance.stopMusic(Constants.AUDIO_LOOP1);
				Gdx.app.exit();
			}
		});

		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//screenManager.game.reset();
				MusicBox.instance.stopMusic(Constants.AUDIO_LOOP1);
				screenManager.setScreenState(Constants.MENUSCREEN);
			}
		});

		Gdx.input.setInputProcessor(stage);

		// Turn on all debug lines (table, cell, and widget)
		//table.setDebug(true);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.begin();
		batch.draw(AssetOrganizer.instance.touristWin.touristWin, (Gdx.graphics.getWidth() / 2) - (AssetOrganizer.instance.touristWin.touristWin.getWidth() / 2),( Gdx.graphics.getHeight() / 2)-300);
		batch.end();
	}

	public void dispose() {
		stage.dispose();
	}
}
