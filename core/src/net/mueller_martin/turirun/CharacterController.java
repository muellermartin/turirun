package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import com.esotericsoftware.kryonet.Client;

import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.gameobjects.CharacterObject;
import net.mueller_martin.turirun.gameobjects.KannibaleCharacterObject;
import net.mueller_martin.turirun.gameobjects.TouriCharacterObject;
import net.mueller_martin.turirun.Constants;
import net.mueller_martin.turirun.network.TurirunNetwork.HitCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.DeadCharacter;
import net.mueller_martin.turirun.gameobjects.CharacterObject.Direction;

public class CharacterController {
	public final static String TAG = CharacterController.class.getName();
	public CharacterObject character;
	private int speed = 200; //from 7 to 200 (now multiplying: speed * deltatime in update())
	private ObjectController objs;
	private Client client;

	public CharacterController(ObjectController objs, Client client) {
		this.objs = objs;
		this.client = client;
	}

	public void update(float deltaTime) {
		// No GameObject?
		if (this.character == null) {
			System.out.println("Character: null");
			return;
		}





		this.character.lastPosition = new Vector2(this.character.currentPosition.x, this.character.currentPosition.y);

		if (!this.character.isDead) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			{
				this.character.currentPosition.add(-speed*Gdx.graphics.getDeltaTime(),0);
				this.character.direction = Direction.LEFT;
				this.character.idle = false;
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				this.character.currentPosition.add(+speed*Gdx.graphics.getDeltaTime(), 0);
				this.character.direction = Direction.RIGHT;
				this.character.idle = false;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				this.character.currentPosition.add(0, speed*Gdx.graphics.getDeltaTime());
				this.character.direction = Direction.TOP;
				this.character.idle = false;
			} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				this.character.currentPosition.add(0,-speed*Gdx.graphics.getDeltaTime());
				this.character.direction = Direction.DOWN;
				this.character.idle = false;
			}
			else if(!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
			{
				this.character.idle = true;
			}

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				this.tryBattle();
			}
		}
	}

	public void setPlayerObj(CharacterObject character) {
		this.character = character;
	}

	public void tryBattle() {
		// No GameObject?
		if (this.character == null) {
			System.out.println("Character: null");
			return;
		}

		// Touri?
		if (this.character instanceof TouriCharacterObject) {
			System.out.println("Nur Kannibalen können batteln.");
			return;
		}

		// Search near by
		for (GameObject obj: objs.getObjects()) {
			if (obj == this.character)
				continue;

			if (obj instanceof CharacterObject) {
				// Center
				int x1 = (int)this.character.currentPosition.x - (int)(this.character.bounds.width/2);
				int y1 = (int)this.character.currentPosition.y - (int)(this.character.bounds.height/2);
				
				int x2 = (int)obj.currentPosition.x - (int)(obj.bounds.width/2);
				int y2 = (int)obj.currentPosition.y - (int)(obj.bounds.height/2);

				x1 -= x2;
				y1 -= y2;
    			
    			if (x1 * x1 + y1 * y1 <= Constants.BATTLE_RADIUS * Constants.BATTLE_RADIUS) {
    				// Battle
    				System.out.println("BATTLE");
					MusicBox.instance.playSound(Constants.AUDIO_CANNIBALEAT);
					DeadCharacter msg = new DeadCharacter();
					msg.id = obj.id;
					this.client.sendTCP(msg);
					((CharacterObject)obj).isDead = true;
    			} else {
    				System.out.println("NO BATTLE");
    			}
				HitCharacter msg = new HitCharacter();
				msg.id = this.character.id;
				this.client.sendTCP(msg);
			}
		}
	}
}
