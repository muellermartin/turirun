package net.mueller_martin.turirun.server;

import java.io.IOException;
import java.util.HashSet;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.network.TurirunNetwork.Character;
import net.mueller_martin.turirun.network.TurirunNetwork.Register;
import net.mueller_martin.turirun.network.TurirunNetwork.AddCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.RemoveCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.UpdateCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.MoveCharacter;

public class TurirunServer {
	static Server server;
	// Will hold all the players
	static HashSet<Character> characters = new HashSet<Character>();

	public static void main(String[] args) {
		try {
			server = new Server() {
				protected Connection newConnection() {
					// By providing our own connection implementation, we can store per connection state without a connection ID to state look up
					return new CharacterConnection();
				}
			};

			// For consistency, the classes to be sent over the network are registered by the same method for both the client and server
			TurirunNetwork.register(server);

			server.addListener(new Listener() {
				public void received(Connection c, Object obj) {
					// All connections for this server are actually CharacterConnections
					CharacterConnection connection = (CharacterConnection)c;
					Character character = connection.character;

					if (obj instanceof Register) {
						// Ignore if already registered
						if (character != null)
							return;

						Register register = (Register)obj;

						character = new Character();
						character.nick = register.nick;
						character.type = register.type;
						// TO DO: Set random position for each player within world bounds (not in items etc.)
						character.x = 0;
						character.y = 0;

						System.out.println("New player registered: " + character.nick);
					}

					if (obj instanceof MoveCharacter) {
						// Ignore if not registered
						if (character == null)
							return;

						MoveCharacter move = (MoveCharacter)obj;

						character.x = move.x;
						character.y = move.y;

						UpdateCharacter update = new UpdateCharacter();

						update.id = character.id;
						update.x = character.x;
						update.y = character.y;

						server.sendToAllTCP(update);
					}
				}

				public void disconnected(Connection c) {
					CharacterConnection connection = (CharacterConnection) c;

					if (connection.character != null) {
						characters.remove(connection.character);

						RemoveCharacter removeCharacter = new RemoveCharacter();

						removeCharacter.id = connection.character.id;

						server.sendToAllTCP(removeCharacter);
					}
				}
			});

			server.start();
			server.bind(TurirunNetwork.tcpPort, TurirunNetwork.udpPort);
		}
		catch (IOException e) {
			// TO DO: Fail
		}
	}

	static class CharacterConnection extends Connection {
		public Character character;
	}
}
