package net.mueller_martin.turirun.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.Constants;
import net.mueller_martin.turirun.MusicBox;
import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.utils.CollusionDirections;
import net.mueller_martin.turirun.network.TurirunNetwork.CheckpointCheck;

import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.gameobjects.CharacterObject;

import com.esotericsoftware.kryonet.Client;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class CheckpointGameObject extends GameObject {
    public boolean active = false;
    private boolean playCameraSound = true;

    public float timer = 0.0f;
    public boolean checked = false;

    public Client client;
    public int checkpointID = 0;

    public static float MAX_TIMER = 5.0f;

    public  ShapeRenderer  shapeRenderer;

    public CheckpointGameObject (float x, float y, float width, float height) {
        super(x, y, AssetOrganizer.instance.stone.stone);
        this.size = new Vector2(width, height);
        this.bounds = new TheTrueRectangle(x, y,size.x ,size.y );
        this.shapeRenderer = new ShapeRenderer();
    }


    @Override
    public void isCollusion(GameObject otherObject , CollusionDirections.CollusionDirectionsTypes type)
    {
        if(otherObject instanceof TouriCharacterObject) {
                this.active = true;
        }
    }


    public void update(float deltaTime)
    {
        if(!checked)
        {
            // Steht ein Spieler auf dem Feld
            if (this.active)
            {


                this.timer += deltaTime;
                if(playCameraSound)
                {
                    MusicBox.instance.playSound(Constants.AUDIO_CAMERASOUND);
                    playCameraSound = false;
                }

            }
            else
                this.timer = 0.0f;

            if (this.timer > MAX_TIMER) {
                this.timer = 0.0f;
                checked = true;
                System.out.println("TIMER BAAAAAAM");
                CheckpointCheck msg = new CheckpointCheck();
                msg.id = this.checkpointID;
                client.sendTCP(msg);

                playCameraSound = true;
            }

            // Reset für nächsten Test
            this.active = false;

        }
    }

    public void draw(SpriteBatch batch)
    {
/*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(this.currentPosition.x, this.currentPosition.y, this.size.x, this.size.y);
        shapeRenderer.end();
*/
        if (this.active && !checked)
        {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
            shapeRenderer.setColor(new Color(150, 150, 150, 0.5f));
            shapeRenderer.circle(this.currentPosition.x + this.size.x / 2, this.currentPosition.y + this.size.y / 2, this.size.x * ((MAX_TIMER - this.timer) / MAX_TIMER));
            shapeRenderer.end();
        }
    }
}
