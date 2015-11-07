package net.mueller_martin.turirun.gameobjects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.utils.CollusionDirections;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class WallGameObject extends GameObject
{

    public  ShapeRenderer  shapeRenderer;

    public WallGameObject (float x, float y, float width, float height) {
        super(x, y, AssetOrganizer.instance.stone.stone);
        this.size = new Vector2(width, height);
        this.bounds = new TheTrueRectangle(x, y, width, height);
        this.shapeRenderer = new ShapeRenderer();

    }

    public void update(float deltaTime)
    {

    }

    public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type) {

    }

    @Override
    public void draw(SpriteBatch batch)
    {
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(this.currentPosition.x, this.currentPosition.y, this.size.x, this.size.y);
        shapeRenderer.end();
        */

    }
}
