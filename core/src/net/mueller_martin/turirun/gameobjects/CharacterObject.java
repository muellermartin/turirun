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

	public void isCollusion(GameObject otherObject) {
		//if(otherObject instanceof WallGameObject)
		//{
			System.out.println("I HIT A WALL");
			this.currentPosition = this.lastPosition;
			this.bounds.setPosition(this.currentPosition.x, this.currentPosition.y);
		//}
	}
}
