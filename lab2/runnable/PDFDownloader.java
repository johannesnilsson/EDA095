package runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFDownloader {

	private int nbrOfDownloads = 0;
	private ArrayList<URL> urlsToDownload;

	public PDFDownloader(String link) {
		urlsToDownload = new ArrayList<URL>();
		parseHTMLforPDF(link, urlsToDownload);
	}

	// Mutual exclusion check
	public synchronized URL getNextFileToDownload() {

		if (nbrOfDownloads == urlsToDownload.size()) {
			return null;
		} else {
			return urlsToDownload.get(nbrOfDownloads++);
		}
	}

	// Mutual exclusion check
	public synchronized boolean hasFilesToDownload() {
		return (nbrOfDownloads < urlsToDownload.size());
	}

	static void parseHTMLforPDF(String target, ArrayList<URL> URLs) {
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

	}

	private static String parseWebAddress(String target) {
		String pattern = "(http|https):\\/?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/[\\w\\-\\.]+[^#?\\s]+).*?(#[\\w\\-]+)?";
		Pattern webPattern = Pattern.compile(pattern);
		Matcher webMatcher = webPattern.matcher(target);

		String protocol = "";
		String host = "";
		// String file = "";

		while (webMatcher.find()) {
			for (int i = 1; i <= webMatcher.groupCount(); i++) {
				protocol = webMatcher.group(1);
				host = webMatcher.group(2);
				// file = webMatcher.group(3);
			}
		}
		return protocol + "://" + host + "/";
	}

}
