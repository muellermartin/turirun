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
import net.mueller_martin.turirun.network.TurirunNetwork.HitCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.DeadCharacter;
import net.mueller_martin.turirun.network.TurirunNetwork.AssignCharacter;

public class TurirunServer {
	static Server server;
	// Will hold all the players
	static HashSet<CharacterConnection> characters = new HashSet<CharacterConnection>();

	public static int next_uuid = 0;

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
						AssignCharacter type = new AssignCharacter();

						type.type = assignCharacterType();

						character = new Character();
						character.id = TurirunServer.next_uuid;
						character.nick = register.nick;
						character.type = type.type;
						// TO DO: Set random position for each player within world bounds (not in items etc.)
						character.x = 0;
						character.y = 0;

						TurirunServer.next_uuid++;

						connection.sendTCP(type);

						connection.character = character;

						AddCharacter newCharacter = new AddCharacter();
						newCharacter.character = character;

						// Add existing characters to new logged in connection
						for (CharacterConnection other : characters) {
							AddCharacter addCharacter = new AddCharacter();

							addCharacter.character = other.character;

							// Send new Client old Client
							connection.sendTCP(addCharacter);
							// Send other Client the new Client
							other.sendTCP(newCharacter);
						}

						characters.add(connection);

						System.out.println("New player registered: " + character.nick);
						System.out.println("Assigned type: " + character.type);
					}

					if (obj instanceof MoveCharacter) {
						// Ignore if not registered
						if (character == null)
							return;

						MoveCharacter move = (MoveCharacter)obj;

						character.x = move.x;
						character.y = move.y;
						character.direction = move.direction;

						//System.out.println("Receiving position of character " + character.id + ": " + character.x + " / " + character.y);

						UpdateCharacter update = new UpdateCharacter();

						update.id = character.id;
						update.x = character.x;
						update.y = character.y;
						update.direction = character.direction;

						for (CharacterConnection other : characters) {
							if (other.character != character)
								other.sendTCP(update);
						}
					}

					if (obj instanceof HitCharacter) {
						// Ignore if not registered
						if (character == null)
							return;

						HitCharacter hit = (HitCharacter)obj;
						for (CharacterConnection other : characters) {
							if (other.character != character)
								other.sendTCP(hit);
						}
					}
					if (obj instanceof DeadCharacter) {
						// Ignore if not registered
						if (character == null)
							return;

						DeadCharacter hit = (DeadCharacter)obj;
						server.sendToAllTCP(hit);
					}
				}

				public void disconnected(Connection c) {
					CharacterConnection connection = (CharacterConnection) c;

					if (connection.character != null) {
						characters.remove(connection);

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

	private static int assignCharacterType() {
		return characters.size() % 2 + 1;
	}

	static class CharacterConnection extends Connection {
		public Character character;
	}
}
