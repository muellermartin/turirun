package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.utils.CollusionDirections;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by DM on 06.11.15.
 */
public class CharacterObject extends GameObject {
	public final static String TAG = CharacterObject.class.getName();

	public String username = "Gast";
	private ShapeRenderer shapeRenderer;
	public boolean isDead = false;
	protected BitmapFont font;
	protected GlyphLayout layout;
	public Animation ani;
	public enum Direction {
		LEFT(0),
		RIGHT(1),
		TOP(2),
		DOWN(3);

		private final int value;

		private Direction(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	};
	public Direction direction = Direction.DOWN;
	public boolean idle = true;

	public int xOffsetTexture = 0;
	public int yOffsetTexture = 0;

	float stateTime = 0;
	public TextureRegion currentFrame;

	public CharacterObject (float x, float y)
	{
		super(x,y,AssetOrganizer.instance.player.player);
		this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight()); // texture.getRegionHeight()
		this.bounds = new TheTrueRectangle(x, y, size.x , size.y);
		this.shapeRenderer = new ShapeRenderer();
		this.font = new BitmapFont();
		this.layout = new GlyphLayout();
	}

	@Override
	public void update(float deltaTime)
	{
		this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);
	}

	@Override
	public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type)
	{
		if(otherObject instanceof WallGameObject)
		{
			switch(type)
			{
				case TOP: this.currentPosition.y = otherObject.currentPosition.y + otherObject.size.y + 1;
					break;
				case BOTTOM: this.currentPosition.y = otherObject.currentPosition.y - this.size.y - 1;
					break;
				case RIGHT: this.currentPosition.x = otherObject.currentPosition.x + otherObject.size.x + 1;
					break;
				case LEFT: this.currentPosition.x = otherObject.currentPosition.x - this.size.x - 1;
					break;
				case NONE:
					break;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(this.bounds.x, this.bounds.y, this.bounds.getWidth(), this.bounds.getHeight());
		shapeRenderer.end();
		*/
		/*
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = ani.getKeyFrame(stateTime, true);
		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(currentFrame, currentPosition.x + xOffsetTexture, currentPosition.y + yOffsetTexture, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		batch.end();
		*/
	}

	public void setNick(String nick) {
		this.username = nick;
	}
}
