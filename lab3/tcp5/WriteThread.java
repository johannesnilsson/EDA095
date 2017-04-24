package tcp5;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread implements Runnable {
	private Socket s;
	private String sep;

	public WriteThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {

		//try (PrintWriter myPW = new PrintWriter(s.getOutputStream())) {
		try (OutputStream os = s.getOutputStream()) {
			
				Scanner myScanner = new Scanner(System.in);
				String input;
				System.out.println("Mannen: ");
				input = myScanner.nextLine();
				System.out.println("Mannen: " + input);
				os.write(input.getBytes());
			
		}catch (Exception e) {

		}

	}

}
