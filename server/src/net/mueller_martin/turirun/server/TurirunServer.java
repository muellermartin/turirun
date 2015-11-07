package net.mueller_martin.turirun.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;

public class TurirunServer {
	public static void main(String[] args) {
		try {
			Server server = new Server();

			server.start();
			server.bind(1337, 7331);
		}
		catch (IOException e) {
			// TO DO: Fail
		}
	}
}
