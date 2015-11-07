package net.mueller_martin.turirun;

import com.badlogic.gdx.maps.tiled.TiledMap;
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
    public final int mapWidth =  300; //get from tile map;
    public final int mapHeight = 300; //get from tile map;

    public Turirun game;
    public ObjectController objs;
    public Level level;

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
        System.out.println("INIT!");
        GameObject playerObj = new GameObject(10, 10 ,5 ,5);
        this.objs.addObject(playerObj);
        controller.setPlayerObj(playerObj);
        //map
        level = new Level();

    }

    public void draw(SpriteBatch batch) {
        level.render();
    	for (GameObject obj: objs.getObjects()) {
    		obj.draw(batch);
    	}

    }

    public void update(float deltaTime) {
        // Input Update
        controller.update(deltaTime);
        // Update Camera
        CameraHelper.instance.camera.position.set(controller.character.currentPosition.x,controller.character.currentPosition.y,0);

    	for (GameObject obj: objs.getObjects()) {
    		obj.update();
            resetIfOutsideOfMap(obj);
    	}

        for (GameObject obj: objs.getObjects()) {
            for (GameObject collusionObj: objs.getObjects()) {

                if(false)/* TODO collusion detection */
                {
                    obj.solveCollusion();
                }
            }
        }
    }

    private void resetIfOutsideOfMap(GameObject obj)
    {
        if (obj.currentPosition.x > mapWidth || obj.currentPosition.x < mapWidth) {
            obj.solveCollusion();
        }

        if (obj.currentPosition.y > mapHeight || obj.currentPosition.y < mapHeight) {
            obj.solveCollusion();
        }
    }
}
