package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class CheckpointGameObject extends GameObject {
    public boolean active = false;

    public CheckpointGameObject (float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
