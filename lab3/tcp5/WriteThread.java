package tcp5;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread implements Runnable {
	private Socket s;

	public WriteThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		Scanner myScanner = null;
		myScanner = new Scanner(System.in);

		while (true) {

			try {
				OutputStream os = s.getOutputStream();

				String input;
				input = myScanner.nextLine();
				if (input.toLowerCase().equals("q:")) {
					System.out.println("Exiting program");
					break;

				}
				// System.out.println(input);
				os.write((input + " \r\n").getBytes());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Shutdown properly
		myScanner.close();
		System.exit(1);
	}

}
