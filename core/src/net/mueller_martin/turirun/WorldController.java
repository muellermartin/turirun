package net.mueller_martin.turirun;

import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.gameobjects.DynamicGameObject;
import net.mueller_martin.turirun.CharacterController;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public CharacterController controller;

    public WorldController(Turirun game) {
    	this.game = game;
    	this.objs = new ObjectController();

        // Create Character Input Controller
        controller = new CharacterController();

    	this.init();
    }

    // Start Game
    public void init() {
        GameObject obj = new GameObject(100, 100, 20, 20);
        this.objs.addObject(obj);

//        DynamicGameObject playerObj = new DynamicGameObject();
//        controller.setPlayerObj(playerObj);
    }

    public void draw(SpriteBatch batch) {
    	for (GameObject obj: objs.getObjects()) {
    		obj.draw(batch);
    	}
    }

    public void update(float deltaTime) {
        // Input Update
        controller.update(deltaTime);

    	for (GameObject obj: objs.getObjects()) {
    		obj.update();
    	}
    }
}
