package net.mueller_martin.turirun;

import java.io.IOException;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.gameobjects.CharacterObject;
import net.mueller_martin.turirun.gameobjects.DynamicGameObject;
import net.mueller_martin.turirun.CharacterController;
import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.network.TurirunNetwork.Register;

import net.mueller_martin.turirun.gameobjects.CheckpointGameObject;

public class WorldController {
    public final static String TAG = WorldController.class.getName();
    public int mapPixelWidth =  300;
    public int mapPixelHeight = 300;
    Client client;

    public Turirun game;
    public ObjectController objs;
    public Level level;

    public CharacterController controller;

    public WorldController(Turirun game) {
    	this.game = game;
    	this.objs = new ObjectController();
    	this.client = new Client();

        // Create Character Input Controller
        controller = new CharacterController();

    	this.init();
    }

    // Start Game
    public void init() {

        GameObject playerObj = new GameObject(10, 10 ,50 ,50);

        this.objs.addObject(playerObj);
        controller.setPlayerObj(playerObj);

        // Spawn Checkpoint
        CheckpointGameObject checkpoint = new CheckpointGameObject(300, 300, 200, 200);
        this.objs.addObject(checkpoint);

        //map
        level = new Level();

        MapProperties prop = level.map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        System.out.println("mapWidth: " + mapWidth + ", " + "mapHeight: " + mapHeight);

        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        System.out.println("tilePixelWidth: " + tilePixelWidth + ", " + "tilePixelHeight: " + tilePixelHeight);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        System.out.println("mapPixelWidth: " + mapPixelWidth + ", " + "mapPixelHeight: " + mapPixelHeight);

		this.client.start();

		// For consistency, the classes to be sent over the network are registered by the same method for both the client and server
		TurirunNetwork.register(client);

		client.addListener(new ThreadedListener(new Listener() {
			public void connected(Connection connection) {
			}

			public void received(Connection connection) {
			}

			public void disconnected(Connection connection) {
			}
		}));

		try {
			// Block for max. 3000ms
			client.connect(3000, "127.0.0.1", TurirunNetwork.tcpPort, TurirunNetwork.udpPort);
			// Server communication after connection can go here, or in Listener#connected()
		}
		catch (IOException e) {
			Gdx.app.error("Could not connect to server", e.getMessage());
		}

		Register register = new Register();

		register.nick = "Foo";
		register.type = 1;

		client.sendTCP(register);
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
            }
        }
    }

    private void resetIfOutsideOfMap(GameObject obj)
    {
        if (obj.currentPosition.x < 0) {
            System.out.println("x: " + obj.currentPosition.x + "  " + mapPixelWidth);
            obj.currentPosition.x = 1;
        }

        if (obj.currentPosition.x + obj.size.x > mapPixelWidth) {
            System.out.println("x: " + obj.currentPosition.x + "  " + mapPixelWidth);
            obj.currentPosition.x = mapPixelWidth - obj.size.x + 1;
        }

        if (obj.currentPosition.y < 0) {
            System.out.println("y: " + obj.currentPosition.y + "  " + mapPixelHeight);
            obj.currentPosition.y = 1;
        }

        if (obj.currentPosition.y + obj.size.y> mapPixelHeight) {
            System.out.println("y: " + obj.currentPosition.y + "  " + mapPixelHeight);
            obj.currentPosition.y = mapPixelHeight - obj.size.y;
        }
    }
}
