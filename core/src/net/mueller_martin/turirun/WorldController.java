package net.mueller_martin.turirun;

import java.io.IOException;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import net.mueller_martin.turirun.gameobjects.*;
import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.network.TurirunNetwork.Register;
import net.mueller_martin.turirun.network.TurirunNetwork.AddCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.UpdateCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.MoveCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.RemoveCharacter;
import net.mueller_martin.turirun.utils.CollusionDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Collections.*;

public class WorldController {
    public final static String TAG = WorldController.class.getName();
    public int mapPixelWidth =  300;
    public int mapPixelHeight = 300;
    Client client;

    public Turirun game;
    public ObjectController objs;
    public Level level;
    public int checkpointCount = 0;
    private int checkpointsNeeded = 1;

    public CharacterController controller;

    public static List<Object> events;

    public WorldController(Turirun game) {
    	this.game = game;
    	this.objs = new ObjectController();
    	this.client = new Client();

        // Create Character Input Controller
        controller = new CharacterController();

        // Events
        WorldController.events = Collections.synchronizedList(new ArrayList<Object>());

    	this.init();
    }

    // Start Game
    public void init() {
        CharacterObject playerObj = new CharacterObject(10, 10, 50, 50);
        this.objs.addObject(playerObj);
        controller.setPlayerObj(playerObj);

        // Spawn Checkpoint
        CheckpointGameObject checkpoint = new CheckpointGameObject(300, 300, 200, 200);
        this.objs.addObject(checkpoint);


        // Spawn Walls
        WallGameObject wall = new WallGameObject(100, 300, 80, 80);
        this.objs.addObject(wall);

        WallGameObject wall2 = new WallGameObject(100, 380, 80, 80);
        this.objs.addObject(wall2);

        WallGameObject wall3 = new WallGameObject(120, 340, 80, 80);
        this.objs.addObject(wall3);

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

			public void received(Connection connection, Object object) {
                WorldController.events.add(object);
			}

			public void disconnected(Connection connection) {
			}
		}));

		try {
			// Block for max. 3000ms // 172.18.12.25
			client.connect(3000, "172.18.12.25", TurirunNetwork.tcpPort, TurirunNetwork.udpPort);
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

        // Netzwerk Update
        this.updateEvents();

		if (client != null) {
			// FIXME: last and current postition are always equal
			//if (controller.character.currentPosition.x != controller.character.lastPosition.x || controller.character.currentPosition.y != controller.character.lastPosition.y)
			{
				MoveCharacter move = new MoveCharacter();

				move.x = controller.character.currentPosition.x;
				move.y = controller.character.currentPosition.y;

				client.sendTCP(move);
			}
		}

        Camera cam = CameraHelper.instance.camera;
        // The camera dimensions, halved
        float cameraHalfWidth = cam.viewportWidth * .5f;
        float cameraHalfHeight = cam.viewportHeight * .5f;

        // Move camera after player as normal
        int pos_x = (int)controller.character.currentPosition.x;
        if (pos_x % 2 == 0)
            pos_x++;
        int pos_y = (int)controller.character.currentPosition.y;
        if (pos_y % 2 == 0)
            pos_y++;
        CameraHelper.instance.camera.position.set(pos_x,pos_y,0);

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

        // update objects
    	for (GameObject obj: objs.getObjects()) {
    		obj.update(deltaTime);
            resetIfOutsideOfMap(obj);
            checkCheckpoints(obj);
    	}

        // check for collusion
        for (GameObject obj: objs.getObjects()) {
            for (GameObject collusionObj: objs.getObjects()) {
                if (obj == collusionObj)
                    continue;

                CollusionDirections.CollusionDirectionsTypes col = obj.bounds.intersection(collusionObj.bounds);
                if (col != CollusionDirections.CollusionDirectionsTypes.NONE) {
                    obj.isCollusion(collusionObj, col);
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


    private void checkCheckpoints(GameObject obj) {
        if (obj instanceof CheckpointGameObject && ((CheckpointGameObject) obj).checked) {
            checkpointCount++;

            if (checkpointCount == checkpointsNeeded) {
                // TODO Tourist won!
                System.out.println("Tourist won!");
            }
        }
    }

    private void updateEvents() {
        ArrayList<Object> del = new ArrayList<Object>();

        synchronized (WorldController.events) {
            for (Object event : WorldController.events) {
                // Add Player
                if (event instanceof AddCharacter) {
                    AddCharacter msg = (AddCharacter)event;

                    CharacterObject newPlayer = new CharacterObject(msg.character.x, msg.character.y, 50, 50);
                    objs.addObject(msg.character.id, newPlayer);

                    del.add(event);
                    continue;
                }
                // Update Player
                if (event instanceof UpdateCharacter) {
                    UpdateCharacter msg = (UpdateCharacter)event;
                    CharacterObject player = (CharacterObject)objs.getObject(msg.id);
                    if (player != null) {
                        player.lastPosition = new Vector2(msg.x, msg.y);
                        player.currentPosition = new Vector2(msg.x, msg.y);
                    }
                }
                // Remove Player
                if (event instanceof RemoveCharacter) {
                    RemoveCharacter msg = (RemoveCharacter)event;
                    CharacterObject player = (CharacterObject)objs.getObject(msg.id);
                    if (player != null) {
                        objs.removeObject(player);
                    }
                }
            }
        }

        for (Object event : del) {
            WorldController.events.remove(event);
        }

    }
}
