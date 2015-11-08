package net.mueller_martin.turirun.gameobjects;

import com.badlogic.gdx.Gdx;
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
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = ani.getKeyFrame(stateTime, true);
		batch.begin();
		batch.setProjectionMatrix(CameraHelper.instance.camera.combined);
		batch.draw(currentFrame, currentPosition.x + xOffsetTexture, currentPosition.y + yOffsetTexture, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());

		String nameText = this.username;
		this.layout.setText(this.font, nameText);
		font.draw(batch, nameText, this.currentPosition.x + this.size.x / 2 - this.layout.width / 2, this.currentPosition.y + this.size.y + this.layout.height + 10);
		batch.end();
    }
}
