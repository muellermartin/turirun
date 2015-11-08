package net.mueller_martin.turirun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GUI {
	public final static String TAG = GUI.class.getName();
	Turirun game;
	WorldController world;
	BitmapFont font;
	SpriteBatch batch;
	GlyphLayout layout;

	public GUI(Game game, WorldController world) {
		this.game = (Turirun)game;
		this.world = world;
		this.font = new BitmapFont();
		this.batch = new SpriteBatch();
		this.layout = new GlyphLayout();
	}

	public void update(float delta) {
	}

	public void render() {
		batch.begin();

		String checkpointAndDeadTouristText = "Checkpoints: " + this.world.checkpointCount + " / " + this.world.checkpointsNeeded + "    eaten Tourists: " + this.world.deadTouries + " / " + this.world.playingTouries;
		this.layout.setText(this.font, checkpointAndDeadTouristText);

		font.draw(batch, checkpointAndDeadTouristText, 10, this.layout.height + 10);

		batch.end();
	}
}
