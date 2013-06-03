package client;

import generated.LoginMessageType;
import generated.LoginReplyMessageType;
import generated.MazeCom;
import generated.ObjectFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import networking.UTFInputStream;
import networking.UTFOutputStream;

public class ServerContext {
	private Socket socket;
	private UTFInputStream utfin;
	private UTFOutputStream utfout;

	private JAXBContext jc;
	private ObjectFactory of;
	private javax.xml.bind.Marshaller m;
	private Unmarshaller um;

	public ServerContext() {
		try {
			of = new ObjectFactory();
			jc = JAXBContext.newInstance(MazeCom.class);
			m = jc.createMarshaller();
		} catch (JAXBException e) {
			System.out.println("Fehler beim Erstellen der JAXB-Dateien:");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int login(String hostname, int port, String name) throws IOException, JAXBException{
			socket = new Socket(hostname, port);
			utfout=new UTFOutputStream(socket.getOutputStream());
			utfin=new UTFInputStream(socket.getInputStream());
			MazeCom mc = new MazeCom();
			
			// Erstellen der Login- Nachricht und abschicken 
			LoginMessageType lmt= of.createLoginMessageType();
			lmt.setName(name); //TODO
			StringWriter sw = new StringWriter();
			mc.setLoginMessage(lmt);
			m.marshal(mc, sw);
			utfout.writeUTF8(sw.toString());
			
			StringReader sr = new StringReader(utfin.readUTF8());
			//empfangen der login-reply-message
			mc = (MazeCom) um.unmarshal(sr);
			LoginReplyMessageType lrt = mc.getLoginReplyMessage();
			int id = lrt.getNewID();//hier noch diverse Fehlermeldungen in der LoginreplyMessage beachten
			
			return id;//soll vom Server eine id bekommen, die dann benutzt wird
	}

	public void send(MazeCom mc, int id) throws IOException, JAXBException{
		StringWriter sw = new StringWriter();
		m.marshal(mc, sw);
		utfout.writeUTF8(sw.toString());
		
	}

	public MazeCom receive() {
		return new MazeCom();
	}

}
