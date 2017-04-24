package tcp1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServerTCP1 {

	public static void main(String[] args) {
		try (ServerSocket sSocket = new ServerSocket(3000)) {

			while (true) {
				System.out.println("Waiting for connection...");

				Socket s = sSocket.accept(); // blocking until connection is
												// made, return a socket.

				System.out.println("New connection with \nIP: " + s.getInetAddress());

				InputStream is = s.getInputStream();
				OutputStream os = s.getOutputStream();

				InputStreamReader is2 = new InputStreamReader(is);
				BufferedReader bf = new BufferedReader(is2);

				String data = "";
				while ((data = bf.readLine()) != null) {
					os.write((data + "\n\r").getBytes());
					System.out.println(data);
				}

				// close the socket connection
				s.close();
				System.out.println("Connection closed...");

			}
		} catch (SocketException t) {
			System.out.println("Error with connection: Broken socket on client side.");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
