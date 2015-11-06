package net.mueller_martin.turirun;

import net.mueller_martin.turirun.gameobjects.GameObject;
/**
 * Created by DM on 06.11.15.
 *
 * level wird erzeugt
 * GameObjectController
 *
 *
 */
public class WorldController {
    public final static String TAG = WorldController.class.getName();

    public Turirun game;
    public ObjectController objs;

    public WorldController(Turirun game) {
    	this.game = game;
    	this.objs = new ObjectController();
    }

    public void draw() {
    	for (GameObject obj: objs.getObjects()) {
    		obj.draw();
    	}
    }

    public void update() {
    	for (GameObject obj: objs.getObjects()) {
    		obj.update();
    	}
    }
}
