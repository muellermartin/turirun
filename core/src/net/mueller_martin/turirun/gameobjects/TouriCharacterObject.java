package net.mueller_martin.turirun.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.utils.CollusionDirections;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class TouriCharacterObject extends CharacterObject
{
    public boolean invisible = false;

    public float timer = 0.0f;
    public boolean cooldown = false;

    public static float MAX_TIMER = 5.0f;

    public  ShapeRenderer  shapeRenderer;

    public TouriCharacterObject (float x, float y)
    {
        super(x,y);
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void isCollusion(GameObject otherObject , CollusionDirections.CollusionDirectionsTypes type)
    {
        if(otherObject instanceof BushGameObject)
        {
            System.out.println("Busch!");
            this.invisible = true;
        }
    }


    public void update(float deltaTime)
    {
        if(!cooldown)
        {
            // Steht der Spieler im Busch
            if (this.invisible)
                this.timer += deltaTime;
            else
                this.timer = 0.0f;

            if (this.timer > MAX_TIMER) {
                this.timer = 0.0f;
                cooldown = true;
                System.out.println("Wieder sichtbar!");
            }

            // Reset für nächsten Test
            this.invisible = false;
        }
    }

    public void draw(SpriteBatch batch) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(this.currentPosition.x, this.currentPosition.y, this.size.x, this.size.y);
        shapeRenderer.end();

        if (!this.invisible)
        {

            batch.begin();
            batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
            batch.draw(texture, currentPosition.x + super.xOffsetTexture, currentPosition.y + super.yOffsetTexture, texture.getRegionWidth(), texture.getRegionHeight());
            batch.end();
        }
    }
}
