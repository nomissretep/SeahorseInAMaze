package client;


import generated.AcceptMessageType;
import generated.AwaitMoveMessageType;
import generated.LoginMessageType;
import generated.MazeCom;
import generated.MazeComType;
import generated.MoveMessageType;
import generated.ObjectFactory;

import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.JAXBException;

import client.types.GameHasEndedException;
import client.types.IllegalTurnException;
import client.types.RecievedWrongTypeException;

import networking.XmlInStream;
import networking.XmlOutStream;

public class ServerContext {
	ObjectFactory obf = new ObjectFactory();
	Socket socket;
	XmlOutStream xmlout;
	XmlInStream xmlin;

	int id;
	public ServerContext(Socket s) throws IOException {
		this.socket = s;
		xmlout = new XmlOutStream(s.getOutputStream());
		xmlin = new XmlInStream(s.getInputStream());
	}

	public void login() throws IOException {
		this.login("Amazing Seahorse");
	}
	public void send(MazeCom mc) throws IOException, JAXBException{
		xmlout.write(mc);
	}
	public MazeCom receive(){
		return xmlin.readMazeCom();
	}
	
	public void login(String name) throws IOException{
		MazeCom request = obf.createMazeCom();
		request.setMcType(MazeComType.LOGIN);
		LoginMessageType loginMessage = obf.createLoginMessageType();
		loginMessage.setName(name);
		request.setLoginMessage(loginMessage);
		xmlout.write(request);
		MazeCom response = xmlin.readMazeCom();
		if(response.getMcType().equals(MazeComType.LOGINREPLY)) {
			id = response.getLoginReplyMessage().getNewID();
		} else if(response.getMcType().equals(MazeComType.DISCONNECT)) {
			System.out.println("Server disconnected me:");
			System.out.println(response.getDisconnectMessage().getErroCode().name());
		} else if(response.getMcType().equals(MazeComType.ACCEPT)) {
			if(!request.getAcceptMessage().isAccept()) {
				//Server does not accept me - try another name...
				this.login(name + "e");
			} else {
				System.out.println("This is now very weird.");
			}
		}
	}
	
	public AwaitMoveMessageType waitForMyTurn() throws GameHasEndedException {
		MazeCom packet = xmlin.readMazeCom();
		if (packet.getMcType().equals(MazeComType.AWAITMOVE)) {
			return packet.getAwaitMoveMessage();
		} else if(packet.getMcType().equals(MazeComType.WIN)) {
			throw new GameHasEndedException(packet.getWinMessage());
		} else {
			throw new RecievedWrongTypeException(packet);
		}
	}
	
	public void doMyTurn(MoveMessageType moveMessage) throws IllegalTurnException {
		MazeCom message = obf.createMazeCom();
		message.setId(id);
		message.setMcType(MazeComType.MOVE);
		message.setMoveMessage(moveMessage);
		xmlout.write(message);
		
		MazeCom response = xmlin.readMazeCom();
		if (response.getMcType().equals(MazeComType.ACCEPT)) {
			AcceptMessageType acceptMessage = response.getAcceptMessage();
			if (acceptMessage.isAccept()) {
				return;
			} else {
				throw new IllegalTurnException(acceptMessage.getErrorCode().name());
			}
		} else {
			throw new RecievedWrongTypeException(response);
		}
	}
}
