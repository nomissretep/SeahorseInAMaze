package client;

import java.io.IOException;
import java.net.UnknownHostException;

import spieler.TestKI;

public class Main {
	public static final String defaultHostname = "localhost";
	public static final int defaultPort = 5123;
	public static void main(String args[]) throws UnknownHostException,
			IOException {
		String hostname;
		int port;
		if (args.length == 2) {
			hostname = args[0];
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				showUsage();
				return;
			}
		} else if(args.length == 1){
			if(args[0].startsWith("-")) {
				showUsage();
				return;
			}
			hostname = args[0];
			port = defaultPort;
			System.out.format("Using default-Port: %d\n", port);
		} else if(args.length == 0){
			hostname = defaultHostname;
			port = defaultPort;
			showUsage();
			System.out.format("Using defaults: %s %d\n", hostname, port);
		} else {
			showUsage();
			return;
		}
		Client client = new Client(hostname, port);
		client.run(new TestKI());
	}
	
	public static void showUsage() {
		System.out.format("USAGE: [HOSTNAME=%s] [PORT=%d]\n", defaultHostname, defaultPort);
	}

}
