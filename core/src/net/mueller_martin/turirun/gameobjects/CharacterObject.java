package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import net.mueller_martin.turirun.AssetOrganizer;
import net.mueller_martin.turirun.CameraHelper;
import net.mueller_martin.turirun.utils.CollusionDirections;
import net.mueller_martin.turirun.utils.TheTrueRectangle;

/**
 * Created by DM on 06.11.15.
 */
public class CharacterObject extends GameObject {
	public String username = "Gast";
	private ShapeRenderer shapeRenderer;
	public boolean isDead = false;
	private BitmapFont font;
	private GlyphLayout layout;

	public int xOffsetTexture = 0;
	public int yOffsetTexture = 0;

	public CharacterObject (float x, float y)
	{
		super(x,y,AssetOrganizer.instance.player.player);
		this.size = new Vector2(texture.getRegionWidth(), texture.getRegionHeight()); // texture.getRegionHeight()
		this.bounds = new TheTrueRectangle(x, y, size.x , size.y);
		this.shapeRenderer = new ShapeRenderer();
		this.font = new BitmapFont();
		this.layout = new GlyphLayout();
	}

	@Override
	public void update(float deltaTime)
	{
		this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);
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
	public void draw(SpriteBatch batch)
	{
		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(CameraHelper.instance.camera.combined);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(this.bounds.x, this.bounds.y, this.bounds.getWidth(), this.bounds.getHeight());
		shapeRenderer.end();
		*/

		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(texture, currentPosition.x + xOffsetTexture, currentPosition.y + yOffsetTexture, texture.getRegionWidth(), texture.getRegionHeight());
		String nameText = this.username;
		this.layout.setText(this.font, nameText);

		font.draw(batch, nameText, this.currentPosition.x + this.size.x / 2 - this.layout.width / 2, this.currentPosition.y + this.size.y + this.layout.height + 10);
		batch.end();
	}

	public void setNick(String nick) {
		this.username = nick;
	}
}
