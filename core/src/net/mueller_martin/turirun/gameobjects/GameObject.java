package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.MusicBox;
import net.mueller_martin.turirun.utils.CollusionDirections;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by DM on 06.11.15.
 */
public class GameObject {
	public Vector2 lastPosition;
	public Vector2 currentPosition;
	public Vector2 size;
	public TheTrueRectangle bounds;
	public int id = 0;
	public TextureRegion texture;


	private ShapeRenderer shapeRenderer;


	public GameObject (float x, float y, TextureRegion t) {
		this.texture = t;
		this.currentPosition = new Vector2(x, y);
		this.lastPosition = new Vector2(x,y);
		this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight());
		this.bounds = new TheTrueRectangle(x, y,size.x ,size.y );
	}


	public void update(float deltaTime)
	{
		this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);
	}

	public void draw(SpriteBatch batch) {

		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(texture, currentPosition.x, currentPosition.y, bounds.width,bounds.height);
		batch.end();

	}

	public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type) {

	}
}
