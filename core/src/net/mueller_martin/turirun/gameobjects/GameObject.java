package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.Constants;
import net.mueller_martin.turirun.utils.CollusionDirections;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by DM on 06.11.15.
 */
public class GameObject {
	public Vector2 lastPosition;
	public Vector2 currentPosition;
	public Vector2 size;
	public TheTrueRectangle bounds;
	public int id = 0;
	public TextureRegion texture;
	float stateTime;
	int runState;
	Animation a;


	private ShapeRenderer shapeRenderer;

	public GameObject (float x, float y, TextureRegion t) {
		this.texture = t;
		this.currentPosition = new Vector2(x, y);
		this.lastPosition = new Vector2(x,y);
		stateTime = 0;
		this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight());
		this.bounds = new TheTrueRectangle(x, y,size.x ,size.y );
		//this.shapeRenderer = new ShapeRenderer();



	//	stateTime += Gdx.graphics.getDeltaTime();           // #15
	//	currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
	//	spriteBatch.begin();
	//	spriteBatch.draw(currentFrame, 50, 50);             // #17
	//	spriteBatch.end();


	}

	public void update(float deltaTime)
	{
		this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);
	}

	public void draw(SpriteBatch batch) {
		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(texture, currentPosition.x, currentPosition.y, bounds.width,bounds.height);
		batch.end();

	}

	public void isCollusion(GameObject otherObject, CollusionDirections.CollusionDirectionsTypes type) {

	}
	public void setState(int i)
	{
		runState = i;
		switch(runState)
		{
			case Constants.PLAYER_RUN_BOTTOM:
			//	a = AssetOrganizer.instance.turiRunDown;
				break;

			case Constants.PLAYER_RUN_TOP:
			//	a = AssetOrganizer.instance.turiRunTop;
				break;

		}
	}
}
