import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor implements Runnable {

	private Spider spider;
	LinkedList<String> urls = new LinkedList<String>();
	LinkedList<String> emails = new LinkedList<String>();

	public static int nbr = 0;
	private int myNbr;

	public Processor(Spider spider) {
		this.spider = spider;
		myNbr = nbr++;

	}

	public void run() {

		String linkToParse = "";
		while (!spider.done()) {
			linkToParse = spider.getFromRemaninig(); // blocking method.
			try {
				parse(linkToParse);
			} catch (HttpStatusException e) {
				System.out.println("Page Returned code 404 Not Found");

			} catch (IOException e2) {
				// e2.printStackTrace();
			}
			spider.addToRemaining(urls);
			spider.addToMail(emails);
			urls.clear();
			emails.clear();
		}

		if (myNbr == 1) {
			System.out.println("I am nbr " + myNbr);
			spider.print();

		}
	}

	private void parse(String link) throws IOException {
		URL url = new URL(link);
		URLConnection conn = url.openConnection();

		if (conn.getContentType().startsWith("text/html")) {

			Document doc = Jsoup.connect(url.toString()).get();

			Elements links = doc.select("a[href]");

			Elements frames = doc.select("frame[src]");

			for (Element e : links) {
				String data = e.attr("abs:href");
				if (!data.isEmpty()) {
					if (data.startsWith("mailto")) {
						emails.add(data);
					} else {
						urls.add(data);
					}
				}
			}

			for (Element e2 : frames) {
				String data = e2.toString();
				System.out.println("ADDED BY FRAME ");
				if (!data.isEmpty()) {
					urls.add(data);
				}
			}
		}
	}

}
