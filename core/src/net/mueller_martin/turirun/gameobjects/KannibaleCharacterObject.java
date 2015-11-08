package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.mueller_martin.turirun.CameraHelper;

/**
 * Created by Dorothea on 07.11.2015.
 */
public class KannibaleCharacterObject extends CharacterObject
{
    public KannibaleCharacterObject (float x, float y)
    {
        super(x,y);


    }

    public void draw(SpriteBatch batch) {
		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(texture, currentPosition.x + xOffsetTexture, currentPosition.y + yOffsetTexture, texture.getRegionWidth(), texture.getRegionHeight());

		String nameText = this.username;
		this.layout.setText(this.font, nameText);
		font.draw(batch, nameText, this.currentPosition.x + this.size.x / 2 - this.layout.width / 2, this.currentPosition.y + this.size.y + this.layout.height + 10);
		batch.end();
    }
}
