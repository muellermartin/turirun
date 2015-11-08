package net.mueller_martin.turirun.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import net.mueller_martin.turirun.AssetOrganizer;
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

    public TouriCharacterObject (float x, float y)
    {
        super(x,y);
    }

    @Override
    public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type)
    {
        super.isCollusion(otherObject, type);

        if(otherObject instanceof BushGameObject)
        {
            this.invisible = true;
        }
    }

    public void update(float deltaTime)
    {
        this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);

        // Reset für nächsten Test
        this.invisible = false;
    }

    public void draw(SpriteBatch batch)
    {
        if (!this.invisible)
        {
            batch.begin();
            batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
            batch.draw(texture, currentPosition.x + super.xOffsetTexture, currentPosition.y + super.yOffsetTexture, texture.getRegionWidth(), texture.getRegionHeight());

            String nameText = this.username;
            this.layout.setText(this.font, nameText);
            font.draw(batch, nameText, this.currentPosition.x + this.size.x / 2 - this.layout.width / 2, this.currentPosition.y + this.size.y + this.layout.height + 10);
            batch.end();
        }
    }
}
