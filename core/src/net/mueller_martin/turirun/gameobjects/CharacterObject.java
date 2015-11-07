package net.mueller_martin.turirun.gameobjects;

import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.utils.CollusionDirections;

/**
 * Created by DM on 06.11.15.
 */
public class CharacterObject extends GameObject {
	public String username = "Gast";

	public CharacterObject (float x, float y, float width, float height) {
		super(x,y,width,height);
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
}
