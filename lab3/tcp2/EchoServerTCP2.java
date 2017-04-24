package tcp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerTCP2 {

	public static void main(String[] args) {

		try (ServerSocket sSocket = new ServerSocket(3000)) {
			// defines how many connections we can handle
			ExecutorService pool = Executors.newFixedThreadPool(10);
			while (true) {
				System.out.println("Waiting for connection...");

				Socket s = sSocket.accept(); // blocking until connection is
												// made, return a socket.
				pool.submit(new RequestHandler(s));

			}
//		} catch (SocketException t) {
//			System.out.println("Error with connection: Broken socket on client side.");

		}
		catch (IOException e) {

			e.printStackTrace();
		}

	}
}
