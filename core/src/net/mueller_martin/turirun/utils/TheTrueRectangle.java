package net.mueller_martin.turirun.utils;

import com.badlogic.gdx.math.Rectangle;

public class TheTrueRectangle extends Rectangle {

	public TheTrueRectangle (float x, float y, float width, float height) {
		super(x,y,width,height);
	}

	public CollusionDirections.CollusionDirectionsTypes intersection(Rectangle other)
	{

		float w = 0.5f * (this.width + other.width);
		float h = 0.5f * (this.height + other.height);
		float dx = (this.x + this.width/2.f) - (other.x + other.width/2.f);
		float dy = (this.y + this.height/2.f) - (other.y + other.height/2.f);

		if (Math.abs(dx) <= w && Math.abs(dy) <= h)
		{
    		/* collision! */
			float wy = w * dy;
			float hx = h * dx;

			if (wy > hx)
			{
				if (wy > -hx)
				{
					return CollusionDirections.CollusionDirectionsTypes.TOP;
				}
				else
				{
					return CollusionDirections.CollusionDirectionsTypes.LEFT;
				}
			}
			else
			{
				if (wy > -hx)
				{
					return CollusionDirections.CollusionDirectionsTypes.RIGHT;
				}

				else
				{
					return CollusionDirections.CollusionDirectionsTypes.BOTTOM;
				}
			}
		}
		else
		{
			return CollusionDirections.CollusionDirectionsTypes.NONE;
		}
	}

	public boolean isthiswesome() {
		return true;
	}

}