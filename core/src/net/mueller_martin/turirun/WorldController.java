package net.mueller_martin.turirun;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.gameobjects.DynamicGameObject;
import net.mueller_martin.turirun.CharacterController;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mueller_martin.turirun.gameobjects.CheckpointGameObject;
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
    public int mapPixelWidth =  300;
    public int mapPixelHeight = 300;

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
        GameObject playerObj = new GameObject(10, 10 ,5 ,5);
        this.objs.addObject(playerObj);
        controller.setPlayerObj(playerObj);

        // Spawn Checkpoint
        CheckpointGameObject checkpoint = new CheckpointGameObject(40, 40, 20, 20);
        this.objs.addObject(checkpoint);

        //map
        level = new Level();

        MapProperties prop = level.map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

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
        Camera cam = CameraHelper.instance.camera;
        // The camera dimensions, halved
        float cameraHalfWidth = cam.viewportWidth * .5f;
        float cameraHalfHeight = cam.viewportHeight * .5f;

        // Move camera after player as normal
        CameraHelper.instance.camera.position.set(controller.character.currentPosition.x,controller.character.currentPosition.y,0);

        float cameraLeft = cam.position.x - cameraHalfWidth;
        float cameraRight = cam.position.x + cameraHalfWidth;
        float cameraBottom = cam.position.y - cameraHalfHeight;
        float cameraTop = cam.position.y + cameraHalfHeight;

        // Horizontal axis
        if(mapPixelWidth < cam.viewportWidth)
        {
            cam.position.x = mapPixelWidth / 2;
        }
        else if(cameraLeft <= 0)
        {
            cam.position.x = 0 + cameraHalfWidth;
        }
        else if(cameraRight >= mapPixelWidth)
        {
            cam.position.x = mapPixelWidth - cameraHalfWidth;
        }

        // Vertical axis
        if(mapPixelHeight < cam.viewportHeight)
        {
            cam.position.y = mapPixelHeight / 2;
        }
        else if(cameraBottom <= 0)
        {
            cam.position.y = 0 + cameraHalfHeight;
        }
        else if(cameraTop >= mapPixelHeight)
        {
            cam.position.y = mapPixelHeight - cameraHalfHeight;
        }

    	for (GameObject obj: objs.getObjects()) {
    		obj.update(deltaTime);
            resetIfOutsideOfMap(obj);
    	}

        for (GameObject obj: objs.getObjects()) {
            for (GameObject collusionObj: objs.getObjects()) {
                if (obj.bounds.contains(collusionObj.bounds)) {
                    obj.isCollusion(collusionObj);
                }

                if(false)/* TODO collusion detection */
                {
                    obj.solveCollusion();
                }
            }
        }
    }

    private void resetIfOutsideOfMap(GameObject obj)
    {
        if (obj.currentPosition.x < 0) {
            System.out.println("x: " + obj.currentPosition.x + "  " + mapPixelWidth);
            obj.currentPosition.x = 5;  /* TODO sinnvollen Wert finden! */
        }

        if (obj.currentPosition.x > mapPixelWidth) {
            System.out.println("x: " + obj.currentPosition.x + "  " + mapPixelWidth);
            obj.currentPosition.x = mapPixelWidth - 5;  /* TODO sinnvollen Wert finden! */
        }

        if (obj.currentPosition.y < 0) {
            System.out.println("y: " + obj.currentPosition.y + "  " + mapPixelHeight);
            obj.currentPosition.y = 5;  /* TODO sinnvollen Wert finden! */
        }

        if (obj.currentPosition.y > mapPixelHeight) {
            System.out.println("y: " + obj.currentPosition.y + "  " + mapPixelHeight);
            obj.currentPosition.y = mapPixelHeight - 5;  /* TODO sinnvollen Wert finden! */
        }
    }
}
