package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.utils.CollusionDirections;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by DM on 06.11.15.
 */
public class CharacterObject extends GameObject {
	public String username = "Gast";

	public CharacterObject (float x, float y, float width, float height) {
		super(x,y,AssetOrganizer.instance.player.player);
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
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.begin();
		batch.draw(texture, currentPosition.x, currentPosition.y, bounds.width,bounds.height);
		batch.end();
		/*
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(this.currentPosition.x, this.currentPosition.y, this.size.x, this.size.y);
		shapeRenderer.end();
		 */
	}
}
