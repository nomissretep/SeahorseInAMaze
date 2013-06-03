package client;

import generated.MazeCom;
import generated.WinMessageType;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

//import spieler.SimpleKI;
//import spieler.Spieler;


public class Client {
	private ServerContext context;
	public boolean cont;
	//private Spieler spieler;

	public Client(String hostname, int port) throws UnknownHostException, IOException{
		Socket s = new Socket(hostname, port);
		context = new ServerContext(s);

//		spieler = new SimpleKI();//andere KIs oder menschliche Nutzer hier einstellbar
	}

	public void run() {
		try {
			context.login(""/* spieler.getName()*/);
		} catch (IOException e) {
			System.out.println("Login fehlgeschlagen");
			e.printStackTrace();
		} 

		boolean sent = false;// gibt an, ob die letzte Nachricht ein awaitMove(true) oder ein Accept(false) war

		while (cont) {// Der Ablauf des Programms
			MazeCom mc = context.receive();
			MazeCom answer = new MazeCom();

			switch (mc.getMcType()) {

			case AWAITMOVE:
				//answer.setMoveMessage(spieler.doTurn(mc.getAwaitMoveMessage().getBoard()));
				try{
					context.send(answer);
				}catch(IOException e){
					System.out.println("Verbindungsfehler beim Senden: ");
					e.printStackTrace();
					cont =false;
				}catch(JAXBException e){
					System.out.println("Fehler beim Marshallen");
					e.printStackTrace();
					cont = false;
				}
				sent = true;
				break;
			case WIN:
				WinMessageType winner = mc.getWinMessage();
				System.out.println(winner.toString() + "hat das Spiel Gewonnen");
				cont = false;
				break;
			case ACCEPT://TODO
				switch (mc.getAcceptMessage().getErrorCode()) {
				case NOERROR:
					if (sent) {
						sent = false;
						break;
					}
				case AWAIT_LOGIN: //u.U. login wiederholen
					;
				case AWAIT_MOVE:
					;
				case ERROR:
					;
				case ILLEGAL_MOVE: // hier alternativen zug auswaehlen
					;
				case TIMEOUT:
					;
				case TOO_MANY_TRIES:
					;
				default:
					;

				}
			case DISCONNECT:
				;
			case LOGIN:
				;
			case LOGINREPLY:
				;
			case MOVE:
				;
			default:
				System.out.println("Ungueltiger NachrichtenTyp:");
				System.out.println(mc.getMcType());
				cont = false;
			}
		}

	}
	}
