package net.mueller_martin.turirun.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.mueller_martin.turirun.network.TurirunNetwork;
import net.mueller_martin.turirun.network.TurirunNetwork.Character;
import net.mueller_martin.turirun.network.TurirunNetwork.Register;

public class TurirunServer {
	public static void main(String[] args) {
		try {
			Server server = new Server() {
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
					}
				}

				public void disconnected(Connection c) {
					CharacterConnection connection = (CharacterConnection) c;

					if (connection.character != null) {
						// TO DO: Remove client and broadcast to other clients
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
