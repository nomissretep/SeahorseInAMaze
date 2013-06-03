package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	ServerContext context;
	Socket s;
	public Client(String hostname, int port) throws UnknownHostException, IOException{
		s = new Socket(hostname, port);
		context = new ServerContext(s);
	}
	
	public void run() throws IOException{
		context.login();
	}

}
