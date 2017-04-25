package tcp5;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {

		try {

			if (args.length == 2) {
				InetAddress temp = InetAddress.getByName(args[0]);
				System.out.println(temp.getHostAddress() + " " + Integer.parseInt(args[1]));
				Socket s = new Socket(temp, Integer.parseInt(args[1]));

				new Thread(new ReadThread(s)).start();
				new Thread(new WriteThread(s)).start();

			} else {
				System.err.println("Usage: java ChatClient machine port");
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
