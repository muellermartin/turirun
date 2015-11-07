package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import net.mueller_martin.turirun.CameraHelper;
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

	private ShapeRenderer shapeRenderer;

	public GameObject (float x, float y, float width, float height) {
		this.currentPosition = new Vector2(x, y);
		this.lastPosition = new Vector2(x,y);
		this.size = new Vector2(width, height);
		this.bounds = new TheTrueRectangle(x, y, width, height);

		this.shapeRenderer = new ShapeRenderer();

	}

	public void update(float deltaTime)
	{
		this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);
	}

	public void draw(SpriteBatch batch) {
		batch.begin();

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(this.currentPosition.x, this.currentPosition.y, this.size.x, this.size.y);
		shapeRenderer.end();
		batch.end();
	}

	public void isCollusion(GameObject otherObject) {

	}
}
