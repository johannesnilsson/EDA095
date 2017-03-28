package lab1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFDownloader {

	public static void main(String[] args) throws IOException {
		System.out.println("Enter a web-address which you want to Download PDF's from:");
		// Get the web-address from user
		Scanner scan = new Scanner(System.in);
		String target = scan.nextLine();
		
		System.out.println("Enter dir to save PDF's: ");
		String dir = scan.nextLine();
		scan.close();

		// Parse all the PDF links from the web-address
		ArrayList<URL> urlsToDownload = parseHTMLforPDF(target);

		System.out.println("Found " + urlsToDownload.size() + " PDF's to download.");

		// Download all the pdfs
		downloadPDFs(urlsToDownload, dir);

	}
 
	private static ArrayList<URL> parseHTMLforPDF(String target) {
		String dataHTML = "";

		String baseAddressHTML = parseWebAddress(target);

		URL urlToHTML = null;
		try {
			urlToHTML = new URL(target);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(urlToHTML.openStream()))) {

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				dataHTML += inputLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String pattern = "<a\\s+(?:[^>]*?\\s+)?href=\"([^\"]*\\.pdf)\"";
		Pattern myPattern = Pattern.compile(pattern);
		Matcher matcher = myPattern.matcher(dataHTML);

		ArrayList<URL> URLs = new ArrayList<URL>();

		while (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String matchedString = matcher.group(1);
				if (!matchedString.contains("http")) {
					matchedString = baseAddressHTML + matchedString;
				}
				try {
					URLs.add(new URL(matchedString));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}

		return URLs;
	}

	private static String parseWebAddress(String target) {
		String pattern = "(http|https):\\/?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/[\\w\\-\\.]+[^#?\\s]+).*?(#[\\w\\-]+)?";
		Pattern webPattern = Pattern.compile(pattern);
		Matcher webMatcher = webPattern.matcher(target);

		String protocol = "";
		String host = "";
		//String file = "";

		while (webMatcher.find()) {
			for (int i = 1; i <= webMatcher.groupCount(); i++) {
				protocol = webMatcher.group(1);
				host = webMatcher.group(2);
				//file = webMatcher.group(3);
			}
		}
		return protocol + "://" + host + "/";
	}

	private static void downloadPDFs(ArrayList<URL> pdfsToDownload, String dir) {
		for (URL url : pdfsToDownload) {

			int index = pdfsToDownload.indexOf(url);
			int size = pdfsToDownload.size();
			System.out.print("Downloading... " + Math.round(((double) index / size * 100)) + " %\r");

			String path = dir + url.getFile().substring(url.getFile().lastIndexOf('/'));

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

			if (index == size - 1) {
				System.out.print("Downloading... 100 %");
			}

		}
	}

}
