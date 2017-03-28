package runnable;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Enter a web-address which you want to Download PDF's from:");
		// Get the web-address from user
		Scanner scan = new Scanner(System.in);
		String target = scan.nextLine();
		
		// Press 1 to use standard link.
		if(target.equals("1")) target = "http://cs229.stanford.edu/materials.html";
		scan.close();

		PDFDownloader downloader = new PDFDownloader(target);

		// Start the threads.
		for (int i = 0; i < 5; i++) {
			// note the difference, here we explicitly say that it is a THREAD.
			(new Thread(new Runner(i,downloader))).start();
		//	(new Runner(i, downloader)).run();;
		}
	}
}
