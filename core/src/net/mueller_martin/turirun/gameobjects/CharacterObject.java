package net.mueller_martin.turirun.gameobjects;

import net.mueller_martin.turirun.gameobjects.GameObject;
/**
 * Created by DM on 06.11.15.
 */
public class CharacterObject extends GameObject {
	public String username = "Gast";

	public CharacterObject (float x, float y, float width, float height) {
		super(x,y,width,height);
	}

	@Override
	public void isCollusion(GameObject otherObject) {
		if(otherObject instanceof WallGameObject)
		{
			System.out.println("I HIT A WALL");

			// object enters form left
			if(this.lastPosition.x < otherObject.currentPosition.x)
			{
				this.currentPosition.x = otherObject.currentPosition.x - this.size.x - 1;
			}

			// object enters form right
			if(this.lastPosition.x > otherObject.currentPosition.x + otherObject.size.x)
			{
				this.currentPosition.x = otherObject.currentPosition.x + otherObject.size.x + 1;
			}

			// object enters form top
			if(this.lastPosition.y > otherObject.currentPosition.y + otherObject.size.y)
			{
				this.currentPosition.y = otherObject.currentPosition.y + otherObject.size.y + 1;
			}

			// object enters form bottom
			if(this.lastPosition.y < otherObject.currentPosition.y)
			{
				this.currentPosition.y = otherObject.currentPosition.y - this.size.y - 1;
			}

		}

		if(otherObject instanceof CharacterObject)
		{
			//TODO Wenn verschedene Spielertpen (Kannibale und Touri) töte Touri
		}
	}
}
