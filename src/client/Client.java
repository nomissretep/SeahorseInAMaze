package client;

import generated.AwaitMoveMessageType;
import generated.MazeComType;
import generated.MoveMessageType;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import spieler.Spieler;
import client.types.GameHasEndedException;
import client.types.IllegalTurnException;
import client.types.RecievedWrongTypeException;

//import spieler.SimpleKI;
//import spieler.Spieler;

public class Client {
	private ServerContext context;
	public boolean cont;

	// private Spieler spieler;

	public Client(String hostname, int port) throws UnknownHostException,
			IOException {
		Socket s = new Socket(hostname, port);
		this.context = new ServerContext(s);

		// spieler = new SimpleKI();//andere KIs oder menschliche Nutzer hier
		// einstellbar
	}

	public void run(Spieler spieler) {
		try {
			this.context.login(""/* spieler.getName() */);
		} catch (IOException e) {
			System.out.println("Login fehlgeschlagen");
			e.printStackTrace();
		}

		try {

			while (true) {// Der Ablauf des Programms

				AwaitMoveMessageType request = this.context.waitForMyTurn();
				MoveMessageType myturn = spieler.doTurn(request.getBoard());
				while (true) {
					try {
						this.context.doMyTurn(myturn);
						break;
					} catch (IllegalTurnException e) {
						System.err.println("KI wanted to do a invalid Turn! "
								+ e.getMessage());
					}
				}
			}
		} catch (GameHasEndedException e) {
			System.out.println("The Game has ended Winner: "
					+ e.getWinMessage().getWinner().getId());
		} catch (RecievedWrongTypeException e) {
			if (e.getFailPacket().getMcType().equals(MazeComType.DISCONNECT)) {
				System.out.println("The Server does not like us. DISCONNECT: "
						+ e.getFailPacket().getDisconnectMessage().getName());
			} else {
				throw e;
			}
		}

	}
}
