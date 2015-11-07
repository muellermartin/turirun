package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by DM on 06.11.15.
 */
public class GameObject {
	public Vector2 position;
	public Vector2 size;
	public Rectangle bounds;
	public int id = 0;

	private ShapeRenderer shapeRenderer;

	public GameObject (float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.size = new Vector2(width, height);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);

		this.shapeRenderer = new ShapeRenderer();
	}

	public void update() {
		this.bounds.setPosition(this.position.x, this.position.y);
		System.out.print("Player Position: " + position.x + ", " + position.y);
	}

	public void draw(SpriteBatch batch) {
		batch.begin();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(this.position.x, this.position.y, this.size.x, this.size.y);
		shapeRenderer.end();
		batch.end();
	}

}
