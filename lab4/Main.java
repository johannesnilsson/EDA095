
public class Main {

	public static void main(String[] args) {

		String myLink = "http://cs.lth.se/eda095/";
		Spider spider = new Spider(myLink);

		// start 10 threads
		for (int i = 0; i < 10; i++) {
			(new Thread(new Processor(spider))).start();
		}

	}

}
