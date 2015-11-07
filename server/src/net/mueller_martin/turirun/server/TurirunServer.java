package net.mueller_martin.turirun.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;

import net.mueller_martin.turirun.network.TurirunNetwork;

public class TurirunServer {
	public static void main(String[] args) {
		try {
			Server server = new Server();

			server.start();
			server.bind(TurirunNetwork.tcpPort, TurirunNetwork.udpPort);
		}
		catch (IOException e) {
			// TO DO: Fail
		}
	}
}
