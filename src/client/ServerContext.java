package client;


import generated.LoginMessageType;
import generated.MazeCom;
import generated.MazeComType;
import generated.ObjectFactory;

import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.JAXBException;

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
}
