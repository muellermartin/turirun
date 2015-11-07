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
		if (this.character != null)
			return;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			this.character.position.add(-1,0);
			System.out.print("LEFT KEY PRESSED!: ");
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.character.position.add(+1,0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			this.character.position.add(0,-1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			this.character.position.add(0,1);
		}
	}

	public void setPlayerObj(GameObject character) {
		this.character = character;
	}
}