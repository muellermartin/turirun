package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by DM on 06.11.15.
 */
public class DynamicGameObject extends GameObject {
	public final Vector2 velocity;

	public DynamicGameObject (float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
	}
}
