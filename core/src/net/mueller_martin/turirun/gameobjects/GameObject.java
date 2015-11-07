package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import net.mueller_martin.turirun.CameraHelper;

/**
 * Created by DM on 06.11.15.
 */
public class GameObject {
	public Vector2 lastPosition;
	public Vector2 currentPosition;
	public Vector2 size;
	public Rectangle bounds;
	public int id = 0;

	private ShapeRenderer shapeRenderer;

	public GameObject (float x, float y, float width, float height) {
		this.currentPosition = new Vector2(x, y);
		this.size = new Vector2(width, height);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);

		this.shapeRenderer = new ShapeRenderer();

	}

	public void update()
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

}
