package tcp4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) {

		try (ServerSocket sSocket = new ServerSocket(3000)) {
			// defines how many connections we can handle
			ExecutorService pool = Executors.newFixedThreadPool(10);
			
			Mailbox mailbox = new Mailbox();
			ConnectionHandler cHandler = new ConnectionHandler();

			while (true) {
				System.out.println("Waiting for connection...");

				Socket s = sSocket.accept(); // blocking until connection is
												// made, return a socket.
				
				
				pool.submit(new RequestHandler(s,mailbox,cHandler));
				
				pool.submit(new BroadcastThread(mailbox,cHandler));

			}
//		} catch (SocketException t) {
//			System.out.println("Error with connection: Broken socket on client side.");

		}
		catch (IOException e) {

			e.printStackTrace();
		}

	}
}
