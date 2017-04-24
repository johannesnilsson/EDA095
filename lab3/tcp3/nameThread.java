package tcp3;

import java.util.Random;

public class nameThread implements Runnable {
	private Mailbox mailbox;
	static private int name = 0;
	private int myName = 0;

	public nameThread(Mailbox m) {
		mailbox = m;
		myName = ++name;
		// System.out.print(name);
	}

	@Override
	public void run() {
		try {
			Random rand = new Random();
			for (int i = 0; i < 5; i++) {
				mailbox.addMessage("my name is: " + myName);
				//System.out.println("my name is: " + myName);
				Thread.sleep(rand.nextInt(5)*1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
