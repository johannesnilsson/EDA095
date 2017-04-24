import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerMain {

	public static void main(String[] args) {
		try {
			ServerSocket sSocket = new ServerSocket(3000);

			while (true) {
				System.out.println("Waiting for connection...");

				Socket s = sSocket.accept(); // blocking until connection is
												// made, return a socket.

				System.out.println("New connection with \nIP: " + s.getInetAddress());

				InputStream is = s.getInputStream();
				OutputStream os = s.getOutputStream();

				String str = "";
				byte[] temp = new byte[1000];
				while (is.read(temp) != -1) {

					// convert byte to string
					str = new String(temp, StandardCharsets.UTF_8);

					// print the string to screen
					System.out.println(str);

					// write to output socket
					os.write(str.getBytes());

					// create new byte to "delete" old data.
					temp = new byte[1000];
				}

				// close the socket connection
				s.close();
				System.out.println("Connection closed...");

			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
