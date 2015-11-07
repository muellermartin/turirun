package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends ScreenAdapter {
	public final static String TAG = MainMenuScreen.class.getName();
	private  ImageButton menuPlay,menuQuit;
	//Turirun game;
	ScreenManager screenManager;
	private ImageButton.ImageButtonStyle playBtnStyle,quitBtnStyle;
	 NinePatch patchQuit;
	 NinePatch patchPlay;

	Skin skin;
	Stage stage;
	Table table;



	public MainMenuScreen (ScreenManager s) {
		this.screenManager = s;



		if(AssetOrganizer.instance.btnQuit.btnQuit==null)
		{
			Gdx.app.log(TAG,"ist NULL");
		}
		MusicBox.instance.loopTheme(Constants.AUDIO_LOOP1);

		patchQuit = new NinePatch(new TextureRegion(AssetOrganizer.instance.btnQuit.btnQuit));
		patchPlay = new NinePatch(new TextureRegion(AssetOrganizer.instance.btnPlay.btnPlay));


		skin = new Skin();
		skin.add("patchQuit",patchQuit);
		skin.add("patchPlay",patchPlay);

		//ImageButtonStyle
		playBtnStyle = new ImageButton.ImageButtonStyle();
		playBtnStyle.up = skin.getDrawable("patchPlay");
		playBtnStyle.down = skin.getDrawable("patchPlay");

		quitBtnStyle = new ImageButton.ImageButtonStyle();
		quitBtnStyle.up = skin.getDrawable("patchQuit");
		quitBtnStyle.down = skin.getDrawable("patchQuit");

		skin.add("quitBtnStyle",quitBtnStyle);
		skin.add("playBtnStyle",playBtnStyle);

		menuPlay = new ImageButton(skin.get("playBtnStyle", ImageButton.ImageButtonStyle.class));
		menuQuit = new ImageButton(skin.get("quitBtnStyle", ImageButton.ImageButtonStyle.class));

		//Stage und Table
		stage = new Stage();
		table = new Table();
		table.setFillParent(false);

		table.setWidth(Gdx.graphics.getWidth());
		table.setHeight(Gdx.graphics.getHeight());
		table.row();
		table.add(menuPlay);
		table.row();
		table.add(menuQuit);
		stage.addActor(table);



		menuPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MusicBox.instance.playSound(Constants.AUDIO_MENUSWITCH);
				screenManager.setScreenState(Constants.JOINMENUSCREEN);
			}
		});


		menuQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MusicBox.instance.playSound(Constants.AUDIO_MENUSWITCH);
				Gdx.app.exit();
			}
		});

		Gdx.input.setInputProcessor(stage);





	}

	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	public void update() {
	}

	@Override
	public void render(float delta) {
		update();
		draw();
	}

	public void dispose()
	{

	}
}
