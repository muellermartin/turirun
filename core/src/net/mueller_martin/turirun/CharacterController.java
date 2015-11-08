package net.mueller_martin.turirun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import net.mueller_martin.turirun.gameobjects.GameObject;
import net.mueller_martin.turirun.gameobjects.CharacterObject;
import net.mueller_martin.turirun.gameobjects.KannibaleCharacterObject;
import net.mueller_martin.turirun.gameobjects.TouriCharacterObject;
import com.badlogic.gdx.math.Vector2;
import net.mueller_martin.turirun.Constants;
import com.esotericsoftware.kryonet.Client;
import net.mueller_martin.turirun.network.TurirunNetwork.HitCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.DeadCharacter;

public class CharacterController {
	public final static String TAG = CharacterController.class.getName();
	public CharacterObject character;
	private int speed = 7;
	private ObjectController objs;
	private Client client;
	private boolean isTouri;

	public CharacterController(ObjectController objs, Client client) {
		this.objs = objs;
		this.client = client;
		this.isTouri = false;

		//prevent null animation

		//character.ani = AssetOrganizer.instance.turiRunTop.turiRunTop;
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
			if(isTouri)
			{
				character.ani = AssetOrganizer.instance.turiRunLeft.turiRunLeft;
			}
			else
			{
				character.ani = AssetOrganizer.instance.cannibalRunLeft.cannibalRunLeft;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			this.character.currentPosition.add(+speed, 0);
			if(isTouri)
			{
				character.ani = AssetOrganizer.instance.turiRunRight.turiRunRight;
			}
			else
			{
				character.ani = AssetOrganizer.instance.cannibalRunRight.cannibalRunRight;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(isTouri)
			{
				character.ani = AssetOrganizer.instance.turiRunTop.turiRunTop;
			}
			else
			{
				character.ani = AssetOrganizer.instance.cannibalRunTop.cannibalRunTop;
			}
			this.character.currentPosition.add(0,speed);
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			//Gdx.app.log(TAG,"DOWN");
			this.character.currentPosition.add(0,-speed);
			if(isTouri)
			{
				character.ani = AssetOrganizer.instance.turiRunDown.turiRunDown;
			}
			else
			{
				character.ani = AssetOrganizer.instance.cannibalRunDown.cannibalRunDown;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			this.tryBattle();
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

	public void checkCharacter() {
			if(this.character instanceof TouriCharacterObject)
			{
				isTouri = true;

			//	Gdx.app.log(TAG,"right did Work");
			}else
			{
			//	Gdx.app.log(TAG,"CRAP IT DIDN'T WORK OR I'M A CANNIBAL");
			}

	}
}
