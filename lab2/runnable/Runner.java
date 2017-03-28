package runnable;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class Runner implements Runnable {

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

			// Download the file
			String path = "DownloadedFile/" + fileToGet.getFile().substring(fileToGet.getFile().lastIndexOf('/'));

			try (BufferedInputStream is = new BufferedInputStream(fileToGet.openStream());
					OutputStream os = new FileOutputStream(path)) {
				byte[] buffer = new byte[2048];
				int numberOfBytesRead;
				while ((numberOfBytesRead = is.read(buffer)) != -1) {
					os.write(buffer, 0, numberOfBytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		System.out.println("thread myNumber is:" + myNumber + "AND I DIED NOW WITH");

	}
}
