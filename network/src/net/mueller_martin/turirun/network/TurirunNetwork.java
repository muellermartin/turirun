package net.mueller_martin.turirun.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class TurirunNetwork {
	public static final int tcpPort = 1337;
	public static final int udpPort = 7331;

	// This registers objects that are going to be sent over the network
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		kryo.register(Register.class);
		kryo.register(Character.class);
		kryo.register(AddCharacter.class);
		kryo.register(RemoveCharacter.class);
		kryo.register(UpdateCharacter.class);
		kryo.register(MoveCharacter.class);
	}

	public static class Register {
		public String nick;
		public int type;
	}

	public static class Character {
		public String nick;
		public int type, id, x, y;
	};

	public static class AddCharacter {
		public Character character;
	}

	public static class RemoveCharacter {
		public int id;
	}

	public static class UpdateCharacter {
		public int id, x, y;
	}

	public static class MoveCharacter {
		public int x, y;
	}
}
