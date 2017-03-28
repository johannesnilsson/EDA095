package Threads;

import java.net.URL;
import java.util.ArrayList;

public class Runner extends Thread {

	private int myNumber;
	private PDFDownloader downloader;

	public Runner(int nbr, PDFDownloader downloader) {
		myNumber = nbr;
		this.downloader = downloader;
	}

	public void run() {
		// myNumber++;
		while (downloader.hasFilesToDownload()) {
			System.out.println("thread myNumber is:" + myNumber);
			URL fileToGet = downloader.getNextFileToDownload();
			downloader.downloadOneFile(fileToGet);
		}

		System.out.println("thread myNumber is:" + myNumber + "AND I DIED NOW WITH");

	}
}
