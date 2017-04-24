package tcp3;

import java.util.Random;

public class readThread implements Runnable{
	private Mailbox mailbox;
	
	public readThread(Mailbox m){
		mailbox = m;
	}

	@Override
	public void run() {
		while(true){
			try{
				Random rand = new Random();
				
				System.out.println(mailbox.removeMessage());
				Thread.sleep(rand.nextInt(3)*1000);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
