import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Spider {

	private LinkedList<String> remainingURL;
	private HashMap<String, Boolean> traversedURL;
	private HashMap<String, Boolean> mailTo;
	private static int MAX_ITERATIONS = 1000;

	public Spider(String startURL) {
		remainingURL = new LinkedList<String>();
		traversedURL = new HashMap<String, Boolean>();
		mailTo = new HashMap<String, Boolean>();
		remainingURL.add(startURL);
	}

	public synchronized boolean done() {
		return traversedURL.size() > MAX_ITERATIONS;
	}

	public synchronized String getFromRemaninig() {
		try {
			while (remainingURL.isEmpty())
				wait();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String nextURL = remainingURL.pop();
		traversedURL.put(nextURL, true);
		return nextURL;
	}

	public synchronized void addToRemaining(LinkedList<String> newLinks) {
		String linkToAdd = "";
		for (int i = 0; i < newLinks.size(); i++) {
			linkToAdd = newLinks.pop();

			if (!traversedURL.containsKey(linkToAdd)) {
				remainingURL.add(linkToAdd);
			}

		}
		notifyAll();
	}

	public synchronized void addToMail(LinkedList<String> newMails) {
		for (int i = 0; i < newMails.size(); i++) {
			mailTo.put(newMails.pop(), true);
		}
	}

	public synchronized void print() {
		while (traversedURL.size() < 1000)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		for (String s : traversedURL.keySet()) {
			System.out.println(s.toString());
		}

		for (String s : mailTo.keySet()) {
			System.out.println(s.toString());
		}

	}

}
