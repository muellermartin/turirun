package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.utils.CollusionDirections;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by DM on 06.11.15.
 */
public class CharacterObject extends GameObject {
	public String username = "Gast";
	private ShapeRenderer shapeRenderer;
	public boolean isDead = false;

	public CharacterObject (float x, float y, float width, float height)
	{
		super(x,y,AssetOrganizer.instance.player.player);
		this.size = new Vector2(125, 205); // texture.getRegionHeight()
		this.bounds = new TheTrueRectangle(x + 200, y + 200, size.x , size.y);
		this.shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type) {
		if(otherObject instanceof WallGameObject)
		{
			switch(type)
			{
				case TOP: this.currentPosition.y = otherObject.currentPosition.y + otherObject.size.y + 1;
					break;
				case BOTTOM: this.currentPosition.y = otherObject.currentPosition.y - this.size.y - 1;
					break;
				case RIGHT: this.currentPosition.x = otherObject.currentPosition.x + otherObject.size.x + 1;
					break;
				case LEFT: this.currentPosition.x = otherObject.currentPosition.x - this.size.x - 1;
					break;
				case NONE:
					break;
			}
		}

		if(otherObject instanceof CharacterObject)
		{
			//TODO Wenn verschedene Spielertpen (Kannibale und Touri) töte Touri
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(this.bounds.x, this.bounds.y, this.bounds.getWidth(), this.bounds.getHeight());
		shapeRenderer.end();

		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(texture, currentPosition.x, currentPosition.y, texture.getRegionWidth(), texture.getRegionHeight());
		batch.end();
	}
}
