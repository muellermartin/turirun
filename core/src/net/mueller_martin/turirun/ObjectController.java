package net.mueller_martin.turirun;

import java.util.HashMap;
import java.util.ArrayList;
import net.mueller_martin.turirun.gameobjects.GameObject;

/**
 * Created by DM on 06.11.15.
 */
public class ObjectController {

	private HashMap<Integer,GameObject> objs_map = new HashMap<Integer,GameObject>();
	private ArrayList<GameObject> objs = new ArrayList<GameObject>();

	public void addObject(GameObject obj) {
		objs.add(obj);
		System.out.println(objs.size());
	}

	public void addObject(int id, GameObject obj) {
		obj.id = id;

		objs_map.put(id, obj);
		objs.add(obj);

		System.out.println(objs.size());
	}

	public void removeObject(GameObject obj) {
		objs_map.remove(obj.id);
		objs.remove(obj);
	}

	public ArrayList<GameObject> getObjects() {
		return objs;
	}

	public GameObject getObject(int id) {
		return objs_map.get(id);
	}
}
