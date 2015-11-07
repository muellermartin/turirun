package net.mueller_martin.turirun;

import java.util.HashMap;
import java.util.ArrayList;
import net.mueller_martin.turirun.gameobjects.GameObject;

/**
 * Created by DM on 06.11.15.
 */
public class ObjectController {
	private ArrayList<GameObject> objs = new ArrayList<GameObject>();

	public void addObject(GameObject obj) {
		objs.add(obj);
	}

	public void removeObject(GameObject obj) {
		objs.remove(obj);
	}

	public ArrayList<GameObject> getObjects() {
		return objs;
	}
}
