package client;

import java.io.IOException;
import java.net.UnknownHostException;

import spieler.DecisionKI;
import spieler.ISpieler;
import spieler.RekursiveDecisionKI;
import spieler.Spieler;

public class Main {
	public static final String defaultHostname = "localhost";
	public static final int defaultPort = 5123;

	public static void main(String args[]) throws UnknownHostException,
			IOException {
		String hostname;
		int port;
		ISpieler ki;
		
		if(args.length >= 1) {
			ki = tryToLoadSpieler(args[0]);
			if(ki == null) return;
		} else {
			showUsage();
			return;
		}
		
		if (args.length >= 2) {
			hostname = args[1];
		} else {
			System.out.println("Using Default-Hostname: localhost");
			hostname = "localhost";
		}
		if(args.length == 3) {
			try {
				port = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				showUsage();
				return;
			}
		} else if(args.length > 3) {
			System.out.println("Too many arguments!");
			showUsage();
			return;
		} else {
			System.out.println("Using Default-Port: 5123");
			port = 5123;
		}
			
		Client client = new Client(hostname, port);
		client.run(ki);
	}

	public static void showUsage() {
		System.out.format("USAGE: KI-Name [HOSTNAME=%s] [PORT=%d]\n", defaultHostname,
				defaultPort);
	}
	public static ISpieler tryToLoadSpieler(String spielername) {
		ClassLoader cl = Main.class.getClassLoader();
		Class<?> spielerInterface;
		try {
			spielerInterface = cl.loadClass("spieler.ISpieler");
		} catch (ClassNotFoundException e1) {
			throw new RuntimeException(e1);
		}
		
		try {
			Class<?> clazz = cl.loadClass("spieler."+spielername);
			if(clazz != null) {
				if(spielerInterface.isAssignableFrom(clazz)) {
					try {
						return (ISpieler) clazz.newInstance();
					} catch (InstantiationException e) {
						System.out.println("Coudn't instantiate spieler."+spielername);
						return null;
					} catch (IllegalAccessException e) {
						System.out.println("Coudn't access spieler."+spielername);
						return null;
					}
				} else {
					System.out.println("KI '"+spielername+"' does not implement ISpieler");
					return null;
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Coudn't find Class: spieler."+spielername);
			return null;
		}
		return null;
	}

}
