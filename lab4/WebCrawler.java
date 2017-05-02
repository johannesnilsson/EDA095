import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	static LinkedList<String> urls = new LinkedList<String>();
	static ArrayList<String> allUrls = new ArrayList<String>();
	static LinkedList<String> mails = new LinkedList<String>();

	// strign is the link, boolean is visited.
	static HashMap<String, Boolean> myHash = new HashMap<String, Boolean>();

	public static void main(String[] args) {

		// temporary link

		// mylink = "http://www.simplehtmlguide.com/livedemo.php?e=frames";
		String mylink = "http://cs.lth.se/eda095/";

		parse(mylink);

		// System.out.println(myHash.size());
		// System.out.println(urls.size());
		int length = 0;
		while (urls.size() > 1 && length < 1000) {
			String linkToVisit = urls.pop();
			if (linkToVisit != null) {
				//System.out.println(myHash.size());
				if (!myHash.containsKey(linkToVisit)) {
					if (checkValidUrl(linkToVisit)) {
						myHash.put(linkToVisit, true);
						allUrls.add(linkToVisit);
					 System.out.println(linkToVisit);
						parse(linkToVisit);
						length++;
					}

				}
			}

		}

		 System.out.println(myHash.size());

	}

	public static void parse(String mylink) {
		URL url;
		InputStream is = null;

		try {
			url = new URL(mylink);
			URLConnection conn = url.openConnection();
		//	 System.out.println(conn.getContentType());
//			if (!conn.getContentType().startsWith("text/html")){
//				return;
//			}
			is = url.openStream();
			Document doc = Jsoup.parse(is, "UTF-8", mylink);

			Elements frame = doc.getElementsByTag("frame");
			Elements links = doc.getElementsByTag("a");
			for (Element link : links) {
				String linkAbsHref = link.attr("abs:href");
				String frameText = frame.attr("src");
				// System.out.println("abshref: " + linkAbsHref);

				if (!frameText.isEmpty()) {
				//	System.out.println("FOUND LINK INSIDE FRAME!");
					urls.add(frameText);
				}

				if (!linkAbsHref.isEmpty()) {
					if (linkAbsHref.startsWith("mailto:")) {
						mails.add(linkAbsHref);
					} else {
						urls.add(linkAbsHref);

					}

				}
			}
			is.close();
			// System.out.println("list size: " + urls.size());

			for (String s : mails) {
				// System.out.println(s);
			}

		}

		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Checke if link works.
	public static boolean checkValidUrl(String link) {
		URL url;
		URLConnection conn = null;
		try {
			url = new URL(link);
			conn = url.openConnection();
			InputStream is = url.openStream();
			is.close();
			conn.getContentType().startsWith("text/html");

		} catch (Exception e) {
			return false;
		}

		if(!conn.getContentType().startsWith("text/html")) return false;
		
		return true;
	}
}
