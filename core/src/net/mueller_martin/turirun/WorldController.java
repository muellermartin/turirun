package net.mueller_martin.turirun;

import java.io.IOException;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.gameobjects.DynamicGameObject;
import net.mueller_martin.turirun.CharacterController;
import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.network.TurirunNetwork.Register;

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
        System.out.println("INIT!");
        GameObject playerObj = new GameObject(10, 10 ,5 ,5);
        this.objs.addObject(playerObj);
        controller.setPlayerObj(playerObj);
        //map
        level = new Level();

        MapProperties prop = level.map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

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
        if (obj.currentPosition.x > mapPixelWidth || obj.currentPosition.x < mapPixelWidth) {
            obj.solveCollusion();
        }

        if (obj.currentPosition.y > mapPixelHeight || obj.currentPosition.y < mapPixelHeight) {
            obj.solveCollusion();
        }
    }
}
