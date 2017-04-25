package tcp4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
	private Socket s;
	private Mailbox m;
	private static int name;
	private int myName;
	private ConnectionHandler cHandler;

	public RequestHandler(Socket s, Mailbox m, ConnectionHandler cHandler) {
		this.s = s;
		this.m = m;
		myName = ++name;
		this.cHandler = cHandler;
	}

	@Override
	public void run() {
		try {

			// add connection to vector
			cHandler.addConnection(s);
			System.out.println("New connection with \nIP: " + s.getInetAddress());

			String welcomeMesssage = "Commands available: \n" + "(1) Send broadcast message - M: your message \n"
					+ "(2) Send echo message - E: your message \n" + "(3) Disconnect - Q: \r\n";

			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();

			InputStreamReader is2 = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(is2);

			String data = "";

			os.write(welcomeMesssage.getBytes());

			boolean breakConnection = false;

			// System.out.println("hejsan texten e här:" +bf.readLine());
			data = bf.readLine();

			while (!breakConnection && (data) != null) {
			//	System.out.println("kom vi hit");
				String[] temp = data.split(":");
				System.out.println(data);
			//	System.out.println(temp[0]);
				switch (temp[0]) {

				case "M":
					m.addMessage(data, "" + myName);
					break;

				case "Q":
					os.write(("CLOSING THE CONNECTION!" + "\r\n").getBytes());
					breakConnection = true;
					break;

				case "E":
					os.write((data + "\r\n").getBytes());
					break;
				default:
					os.write(("Lol invalid command command... \r\n").getBytes());
					break;
				}
				data = bf.readLine();
			//	System.out.println("hej:" + data);
			}

			// close the socket connection
			cHandler.removeConnection(s);
			s.close();
			System.out.println("Connection closed...");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
