package net.mueller_martin.turirun;

import java.util.HashMap;
import java.util.ArrayList;
import net.mueller_martin.turirun.gameobjects.GameObject;

/**
 * Created by DM on 06.11.15.
 */
public class ObjectController {

	private static int next_id = 0;
	private HashMap<Integer,GameObject> objs_map = new HashMap<Integer,GameObject>();
	private ArrayList<GameObject> objs = new ArrayList<GameObject>();

	public void addObject(GameObject obj) {
		this.next_id++;
		
		int id = next_id;
		obj.id = next_id;

		objs_map.put(id, obj);
		objs.add(obj);
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
