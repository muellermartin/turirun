package net.mueller_martin.turirun.utils;

import com.badlogic.gdx.math.Rectangle;

public class TheTrueRectangle extends Rectangle {

	public TheTrueRectangle (float x, float y, float width, float height) {
		super(x,y,width,height);
	}

	public boolean intersection(Rectangle other) {
	    return this.x < other.x + other.width &&
	       this.x + this.width > other.x &&
	       this.y < other.y + other.height &&
	       this.height + this.y > other.y;
	}

}