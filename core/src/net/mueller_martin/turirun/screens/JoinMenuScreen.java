package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class JoinMenuScreen extends ScreenAdapter {
	public final static String TAG = JoinMenuScreen.class.getName();
	ScreenManager screenManager;
	Stage stage;
	Table table;
	TextField nickText;
	TextField hostText;
	TextField portText;

	public JoinMenuScreen(ScreenManager s) {
		this.screenManager = s;
		stage = new Stage();
		table = new Table();
		BitmapFont font = new BitmapFont();
		BaseDrawable dummy = new BaseDrawable(); // Replaces cursor, background etc.

		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Label nickLabel = new Label("Nickname:", labelStyle);
		Label hostLabel = new Label("Host:", labelStyle);
		Label portLabel = new Label("Port:", labelStyle);

		TextFieldStyle textFieldStyle = new TextFieldStyle(font, Color.WHITE, dummy, dummy, dummy);
		nickText = new TextField("Unnamed", textFieldStyle);
		hostText = new TextField("127.0.0.1", textFieldStyle);
		portText = new TextField("1337", textFieldStyle);

		TextButtonStyle textButtonStyle = new TextButtonStyle(dummy, dummy, dummy, font);
		TextButton backButton = new TextButton("Back", textButtonStyle);
		TextButton startButton = new TextButton("Start", textButtonStyle);

		table.setFillParent(true);
		stage.addActor(table);

		table.row();
		table.add(nickLabel).left().padRight(10);
		table.add(nickText);
		table.row();
		table.add(hostLabel).left();
		table.add(hostText);
		table.row();
		table.add(portLabel).left();
		table.add(portText);
		table.row();
		table.add(backButton);
		table.add(startButton);

		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenManager.setScreenState(Constants.MENUSCREEN);
			}
		});

		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screenManager.game.host = hostText.getText();
				screenManager.game.port = Integer.parseInt(portText.getText());
				screenManager.game.nickname = nickText.getText();
				screenManager.setScreenState(Constants.GAMESCREEN);
			}
		});

		Gdx.input.setInputProcessor(stage);

		// turn on all debug lines (table, cell, and widget)
		//table.setDebug(true);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}
}
