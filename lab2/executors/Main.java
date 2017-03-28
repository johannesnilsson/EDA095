package executors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Enter a web-address which you want to Download PDF's from:");
		// Get the web-address from user
		Scanner scan = new Scanner(System.in);
		String target = scan.nextLine();
		scan.close();
		// Press 1 to use standard link.
		if (target.equals("1"))
			target = "http://cs229.stanford.edu/materials.html";

		PDFDownloader downloader = new PDFDownloader(target);
		ArrayList<URL> urls = downloader.getUrls();

		// Create pool of Threads with the size equal to url amount
		ExecutorService executorService = Executors.newFixedThreadPool(urls.size());

		// For each URL "create new" thread.
		for (int i = 0; i < urls.size(); i++) {
			executorService.submit(new Runner(urls.get(i)));
		}
		// shutdown the executor
		executorService.shutdown();

	}
}
