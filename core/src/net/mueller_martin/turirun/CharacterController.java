package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.mueller_martin.turirun.gameobjects.GameObject;

public class CharacterController {

	public GameObject character;

	public CharacterController() {
	}

	public void update(float deltaTime) {
		// No GameObject?
		if (this.character == null) {
			System.out.println("Character: null");
			return;
		}

		this.character.lastPosition = this.character.currentPosition;
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			this.character.currentPosition.add(-1,0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.character.currentPosition.add(+1,0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			this.character.currentPosition.add(0,1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			this.character.currentPosition.add(0,-1);
		}
	}

	public void setPlayerObj(GameObject character) {
		this.character = character;
	}
}