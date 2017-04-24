package tcp4;

import java.net.Socket;
import java.util.Vector;

public class ConnectionHandler {
	private Vector<Socket> connections;

	// Class holding all connections
	ConnectionHandler(){
		connections = new Vector<Socket>();
	}
	
	public synchronized void addConnection(Socket socket){
		connections.add(socket);
		System.out.println("AMount of connections is: " +connections.size());
	}
	
	public synchronized void removeConnection(Socket socket){
		System.out.println("AMount of connections is: " +connections.size());
		connections.remove(socket);
		System.out.println("AMount of connections is: " +connections.size());
	}
	
	public synchronized Vector<Socket> getConnections(){
		return connections;
	}

}
