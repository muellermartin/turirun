package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.mueller_martin.turirun.utils.CollusionDirections;

import net.mueller_martin.turirun.AssetOrganizer;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class CheckpointGameObject extends GameObject {
    public boolean active = false;

    public float timer = 0.0f;

    public static float MAX_TIMER = 5.0f;

    public CheckpointGameObject (float x, float y, float width, float height) {
        super(x, y, AssetOrganizer.instance.stone.stone);
    }

    @Override
    public void isCollusion(GameObject otherObject , CollusionDirections.CollusionDirectionsTypes type)
    {
        System.out.println("I HIT A CHECKPOINT");
        this.active = true;
    }

    public void update(float deltaTime) {
    	// Steht ein Spieler auf dem Feld
    	if (this.active)
	    	this.timer+= deltaTime;
	    else
	    	this.timer = 0.0f;


    	if (this.timer > MAX_TIMER) {
    		this.timer = 0.0f;
    		System.out.println("TIMER BAAAAAAM");
    	}

    	// Reset für nächsten Test
    	this.active = false;
    }
}
