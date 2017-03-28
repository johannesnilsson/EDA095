package Threads;

import java.net.URL;
import java.util.ArrayList;

public class Runner extends Thread {

	private int myNumber;

	public Runner(int nbr) {
		myNumber = nbr;
	}

	public void run() {
		// myNumber++;
		while (PDFDownloader.hasFilesToDownload()) {
			System.out.println("thread myNumber is:" + myNumber);
			URL fileToGet = PDFDownloader.getNextFileToDownload();
			PDFDownloader.downloadOneFile(fileToGet);
		}

		System.out.println("thread myNumber is:" + myNumber + "AND I DIED NOW WITH");

	}
}
