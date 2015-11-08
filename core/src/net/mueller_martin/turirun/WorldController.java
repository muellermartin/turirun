package net.mueller_martin.turirun;

import java.io.IOException;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import net.mueller_martin.turirun.gameobjects.*;
import net.mueller_martin.turirun.gameobjects.CharacterObject.Direction;
import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.network.TurirunNetwork.Register;
import net.mueller_martin.turirun.network.TurirunNetwork.AddCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.UpdateCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.MoveCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.RemoveCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.HitCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.DeadCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.AssignCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.CheckpointCheck;
import net.mueller_martin.turirun.utils.CollusionDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class WorldController {
    public final static String TAG = WorldController.class.getName();
    public int mapPixelWidth =  300;
    public int mapPixelHeight = 300;
    private int tilePixelWidth = 0;
    private  int tilePixelHeight = 0;
    Client client;

    public Turirun game;
    public ObjectController objs;
    public Level level;
    public int checkpointCount = 0;
    public int checkpointsNeeded = 0;

    public int playingTouries = 0;
    public int deadTouries = 0;
    public float timer = 0.0f;
    public float MAX_TIMER = 0;

    public float timerCheckpoint = 0.0f;
    public float MAX_TIMER_CHECKPOINT = 0.0f;

    public float deltaTimeUpdate = 0;

    private boolean[][] freeCells;
    private float mapCellWidth = 0;
    private float mapCellHeight = 0;

    public CharacterController controller;

    public static List<Object> events;

    public WorldController(Turirun game) {
    	this.game = game;
    	this.objs = new ObjectController();
    	this.client = new Client();

        // Create Character Input Controller
        controller = new CharacterController(this.objs, this.client);

        // Events
        WorldController.events = Collections.synchronizedList(new ArrayList<Object>());

    	this.init();
    }

    // Start Game
    public void init() {
        //map size
        level = new Level();

        MapProperties prop = level.map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        System.out.println("mapWidth: " + mapWidth + ", " + "mapHeight: " + mapHeight);

        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);

        System.out.println("tilePixelWidth: " + tilePixelWidth + ", " + "tilePixelHeight: " + tilePixelHeight);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        System.out.println("mapPixelWidth: " + mapPixelWidth + ", " + "mapPixelHeight: " + mapPixelHeight);

        // set bounding boxes for tilemap sprites
        // stones
        TiledMapTileLayer layer = (TiledMapTileLayer) level.map.getLayers().get("stones");
        System.out.println("Layer: " + layer);

        freeCells = new boolean[layer.getWidth()][layer.getHeight()];
        mapCellWidth = layer.getWidth();
        mapCellHeight = layer.getHeight();
        for(int x = 0; x < layer.getWidth(); x++)
        {
            for(int y = 0; y < layer.getHeight(); y++)
            {
                freeCells[x][y] = true;
                if(layer.getCell(x, y) != null)
                {
                    // Spawn Walls
                    WallGameObject wall = new WallGameObject(x * tilePixelWidth + 25, y * tilePixelWidth  + 25, 100, 100);
                    this.objs.addObject(wall);

                    //safe blocked cell coordinates for random player position
                    freeCells[x][y] = false;
                }
            }
        }

        // bushs
        layer = (TiledMapTileLayer) level.map.getLayers().get("bushs");

        for(int x = 0; x < layer.getWidth(); x++)
        {
            for(int y = 0; y < layer.getHeight(); y++)
            {
                if(layer.getCell(x, y) != null)
                {
                    // Spawn Bush
                    BushGameObject bush = new BushGameObject(x * tilePixelWidth + 25, y*tilePixelWidth + 25, 90, 90);
                    this.objs.addObject(bush);
                }
            }
        }

        // checkpoints
        layer = (TiledMapTileLayer) level.map.getLayers().get("checkpoint");

        int ci = 0;
        for(int x = 0; x < layer.getWidth(); x++)
        {
            for(int y = 0; y < layer.getHeight(); y++)
            {
                if(layer.getCell(x, y) != null)
                {
                    // Spawn Checkpoint
                    CheckpointGameObject checkpoint = new CheckpointGameObject(x * tilePixelWidth, y * tilePixelWidth, 125, 125);
                    checkpoint.client = this.client;
                    checkpoint.checkpointID = ci;
                    this.objs.addObject(checkpoint);
                    checkpointsNeeded++;
                    ci++;
                }
            }
        }

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
			client.connect(3000, this.game.host, this.game.port, TurirunNetwork.udpPort);
			// Server communication after connection can go here, or in Listener#connected()
		}
		catch (IOException e) {
			Gdx.app.error("Could not connect to server", e.getMessage());

			// Create local player as fallback
			TouriCharacterObject playerObj = new TouriCharacterObject(10, 10);
			playerObj.setNick(game.nickname);
			objs.addObject(playerObj);
			controller.setPlayerObj(playerObj);
		}

		Register register = new Register();

		register.nick = this.game.nickname;
		register.type = 0;

		client.sendTCP(register);
    }

    public void draw(SpriteBatch batch) {
        level.render();
    	for (GameObject obj: objs.getObjects()) {
    		obj.draw(batch);
    	}
    }

    public void update(float deltaTime)
    {
        this.timer += deltaTime; // timer for endscreen
        this.timerCheckpoint += deltaTime; // timer for endscreen
        // Input Update
        controller.update(deltaTime);

        // Netzwerk Update
        this.updateEvents();

		if (controller.character != null) {
			// FIXME: last and current postition are always equal
			//if (controller.character.currentPosition.x != controller.character.lastPosition.x || controller.character.currentPosition.y != controller.character.lastPosition.y)
			deltaTimeUpdate += deltaTime;

            if (deltaTimeUpdate > 0.0005f){
				MoveCharacter move = new MoveCharacter();

				move.x = controller.character.currentPosition.x;
				move.y = controller.character.currentPosition.y;
				move.direction = controller.character.direction.getValue();
				move.idle = controller.character.idle;

				client.sendTCP(move);

                deltaTimeUpdate = 0;
			}
		}

        Camera cam = CameraHelper.instance.camera;
        // The camera dimensions, halved
        float cameraHalfWidth = cam.viewportWidth * .5f;
        float cameraHalfHeight = cam.viewportHeight * .5f;

		// Move camera after player as normal
        if (controller.character != null) {
    		int pos_x = (int)controller.character.currentPosition.x;
    		if (pos_x % 2 == 0)
    			pos_x++;
    		int pos_y = (int)controller.character.currentPosition.y;
    		if (pos_y % 2 == 0)
    			pos_y++;
    		CameraHelper.instance.camera.position.set(pos_x,pos_y,0);
        }

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
        checkpointCount = 0;
        deadTouries = 0;
        playingTouries = 0;
    	for (GameObject obj: objs.getObjects())
        {
    		obj.update(deltaTime);
            resetIfOutsideOfMap(obj);

            if (obj instanceof CheckpointGameObject && ((CheckpointGameObject) obj).checked)
            {
                checkpointCount++;
            }

            // check for playing Tourist
            if (obj instanceof TouriCharacterObject)
            {
                playingTouries++;
            }
            // check for dead players
            if (obj instanceof CharacterObject)
            {
                if(((CharacterObject) obj).isDead)
                {
                    deadTouries++;
                }
            }
    	}

        //System.out.println(" timer: " +  timer);
        checkDeadTouries(this.timer > MAX_TIMER && MAX_TIMER != 0);
        //System.out.println(" MAX_TIMER: " +  MAX_TIMER);

        System.out.println(" timerCheckpoint: " +  timerCheckpoint);
        checkCheckpoints(this.timerCheckpoint > MAX_TIMER_CHECKPOINT && MAX_TIMER_CHECKPOINT != 0);
        System.out.println(" MAX_TIMER_CHECKPOINT: " +  MAX_TIMER_CHECKPOINT);


        // check for collusion
        for (GameObject obj: objs.getObjects()) {
            for (GameObject collusionObj: objs.getObjects()) {
                if (obj == collusionObj)
                    continue;

                CollusionDirections.CollusionDirectionsTypes col = obj.bounds.intersection(collusionObj.bounds);
                if (col != CollusionDirections.CollusionDirectionsTypes.NONE)
                {
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

    private void checkCheckpoints(boolean switchScreen)
    {
        System.out.println("switchScreen: " + switchScreen);
        if (checkpointCount == checkpointsNeeded)
        {
            System.out.println("Tourists won!");
            if(MAX_TIMER_CHECKPOINT == 0)
            {
                MAX_TIMER_CHECKPOINT = 15.0f;
            }
            if(switchScreen)
            {
                // Set string for game over screen
                this.game.winner = "Tourists";
                this.game.screenManager.setScreenState(Constants.GAMEOVERSCREEN);
            }
        }
    }

    private void checkDeadTouries(boolean switchScreen)
    {
        System.out.println("switchScreen: " + switchScreen);
         if (deadTouries == playingTouries && deadTouries > 0)
        {
            System.out.println("Cannibals won!");
            if(MAX_TIMER == 0)
            {
                MAX_TIMER = 15.0f;
            }
            if(switchScreen)
            {
                // Set string for game over screen
                this.game.winner = "Cannibals";
                this.game.screenManager.setScreenState(Constants.GAMEOVERSCREEN);
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
                    CharacterObject newPlayer;

                    if (msg.character.type == 1)
                    {
                        newPlayer = new TouriCharacterObject(msg.character.x, msg.character.y);
                    }
                    else {
                        newPlayer = new KannibaleCharacterObject(msg.character.x, msg.character.y);
                    }

                    newPlayer.setNick(msg.character.nick);
                    objs.addObject(msg.character.id, newPlayer);

                    //System.out.println("New Character #"+msg.character.id);

                    del.add(event);
                    continue;
                }
                if (event instanceof AssignCharacter) {
                    AssignCharacter msg = (AssignCharacter)event;

                    CharacterObject playerObj;

                    if(msg.type == 1)
                    {
                        Vector2 vec = getRandomPosition();
                        playerObj = new TouriCharacterObject(10, 10);// new TouriCharacterObject(vec.x, vec.y);
                    }
                    else
                    {
                        Vector2 vec = getRandomPosition();
                        playerObj = new KannibaleCharacterObject(10, 10);// new KannibaleCharacterObject(vec.x, vec.y);
                    }

                    playerObj.setNick(game.nickname);
                    objs.addObject(msg.id, playerObj);
                    controller.setPlayerObj(playerObj);

                    //System.out.println("Set owen Character");

                    del.add(event);
                    continue;
                }
                // Update Player
                if (event instanceof UpdateCharacter) {
                    UpdateCharacter msg = (UpdateCharacter)event;
                    CharacterObject player = (CharacterObject)objs.getObject(msg.id);
                    if (player != null) {
                        player.currentPosition = new Vector2(msg.x, msg.y);
                        player.direction = Direction.values()[msg.direction];
                        player.idle = msg.idle;
                    }
                    del.add(event);
                    continue;
                }
                // Remove Player
                if (event instanceof RemoveCharacter) {
                    RemoveCharacter msg = (RemoveCharacter)event;
                    CharacterObject player = (CharacterObject)objs.getObject(msg.id);
                    if (player != null) {
                        objs.removeObject(player);
                        //System.out.println("Remove Player #"+msg.id);
                    }
                    del.add(event);
                    continue;
                }
                // HitCharacter
                if (event instanceof HitCharacter) {
                    HitCharacter msg = (HitCharacter)event;
                    CharacterObject player = (CharacterObject)objs.getObject(msg.id);
                    if (player != null) {
                        //System.out.println("Player HIT "+msg.id);
                    }
                    del.add(event);
                    continue;
                }
                // Dead Character
                if (event instanceof DeadCharacter) {
                    DeadCharacter msg = (DeadCharacter)event;
                    CharacterObject player = (CharacterObject)objs.getObject(msg.id);
                    if (player != null) {
                        player.isDead = true;
                    }
                    del.add(event);
                    continue;
                }
                // Checkpoint Checked
                if (event instanceof CheckpointCheck) {
                    CheckpointCheck msg = (CheckpointCheck)event;

                    for(GameObject obj : objs.getObjects()) {
                        if (obj instanceof CheckpointGameObject) {
                            CheckpointGameObject cObj = (CheckpointGameObject)obj;
                            if (cObj.checkpointID == obj.id) {
                                cObj.checked = true;
                            }
                        }
                    }
                }
            }
        }

        for (Object event : del) {
            WorldController.events.remove(event);
        }

    }

    private Vector2 getRandomPosition()
    {
        boolean foundPosition = false;
        int x = 10;
        int y = 10;

        while (!foundPosition)
        {
            x = (int) (Math.random() * mapCellWidth);
            y = (int) (Math.random() * mapCellWidth);
            if(freeCells[x][y])
            {
                System.out.println("Position: " + x + ", " + y);
                foundPosition = true;
                freeCells[x][y] = false;
            }
        }

        return new Vector2(x * tilePixelWidth, y * tilePixelHeight);
    }

}
