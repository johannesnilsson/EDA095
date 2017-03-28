package executors;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class Runner implements Runnable {

	private URL url;

	public Runner(URL url) {
		this.url = url;
	}

	// @Override
	// public void execute(Runnable command) {
	public void run() {

		System.out.println("RUNNING 12313 HERE");

		// Download the file
		String path = "DownloadedFile/" + url.getFile().substring(url.getFile().lastIndexOf('/'));

		try (BufferedInputStream is = new BufferedInputStream(url.openStream());
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

}
