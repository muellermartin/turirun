package net.mueller_martin.turirun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.mueller_martin.turirun.Constants;
import net.mueller_martin.turirun.Turirun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.VIEWPORTWIDTH;
		config.height= Constants.VIEWPORTHEIGHT;
		new LwjglApplication(new Turirun(), config);
	}
}
