package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.mueller_martin.turirun.gameobjects.GameObject;
import com.badlogic.gdx.math.Vector2;

public class CharacterController {

	public GameObject character;
	private int speed = 7;

	public CharacterController()
	{

	}

	public void update(float deltaTime) {
		// No GameObject?
		if (this.character == null) {
			System.out.println("Character: null");
			return;
		}


		this.character.lastPosition = new Vector2(this.character.currentPosition.x, this.character.currentPosition.y);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			this.character.currentPosition.add(-speed,0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.character.currentPosition.add(+speed,0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			this.character.currentPosition.add(0,speed);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			this.character.currentPosition.add(0,-speed);
		}
	}

	public void setPlayerObj(GameObject character) {
		this.character = character;
	}
}
