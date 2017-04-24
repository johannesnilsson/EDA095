package tcp4;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class BroadcastThread implements Runnable{
	private Mailbox mailbox;
	private ConnectionHandler cHandler;
	
	public BroadcastThread(Mailbox m, ConnectionHandler cHandler){
		mailbox = m;
		this.cHandler = cHandler;
	}

	@Override
	public void run() {
		Vector<Socket> connections;
		while(true){
			connections = cHandler.getConnections();
			String message = mailbox.removeMessage() +"\n\r";
			for(int i =0; i < connections.size(); i++){
			
				try {
					connections.get(i).getOutputStream().write(message.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
