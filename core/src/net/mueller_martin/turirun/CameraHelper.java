package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;

import turbomine.manager.Constants;


public class CameraHelper
{
	public final static String TAG = CameraHelper.class.getName();
	public static final CameraHelper instance = new CameraHelper();
	public OrthographicCamera camera;
	float width;
	float height;
	long tmpTime;
	public CameraHelper() 
	{
		init();
	}
	public void init()
	{
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1, height/width);
		camera.position.set(width/2,height/2,0);
		camera.viewportWidth = Constants.VIEWPORTWIDTH;
		camera.viewportHeight = Constants.VIEWPORTHEIGHT;
		Gdx.app.log(TAG, "init(): "+Constants.VIEWPORTWIDTH+" "+Constants.VIEWPORTHEIGHT);

	}
	public void setDimension(float w,float h)
	{
		width = w;
		height = h;
		camera.update();
		Gdx.app.log(TAG, "setDimenstion() width: "+width+" height: "+height);
	}
	public void update()
	{
		camera.update();
	}
	

	public void cameraFollowingZoom(float faktor)
	{
		if(faktor < 0.4)
		{
			faktor = 0.4f;
		}else if(faktor > 1.0f)
		{
			faktor = 1.0f;
		}
		final float x = Constants.VIEWPORTWIDTH*faktor;
		final float y = Constants.VIEWPORTHEIGHT*faktor;

			if(camera.viewportWidth >= x && camera.viewportHeight >= y)
			{
				camera.viewportWidth = camera.viewportWidth-4.5f;
				camera.viewportHeight = camera.viewportHeight-2.5f;
			}else if(camera.viewportWidth <= x && camera.viewportHeight <= y)
			{
				camera.viewportWidth = camera.viewportWidth+4.5f;
				camera.viewportHeight = camera.viewportHeight+2.5f;
			}
		
	}

}
