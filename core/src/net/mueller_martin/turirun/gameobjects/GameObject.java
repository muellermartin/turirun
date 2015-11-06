package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by DM on 06.11.15.
 */
public class GameObject {
	public Vector2 position;
	public Rectangle bounds;
	public int id = 0;

	public GameObject (float x, float y, float width, float height) {
		this.position = new Vector2(x, y);
		this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	public void update() {

	}

	public void draw() {
		
	}

}
