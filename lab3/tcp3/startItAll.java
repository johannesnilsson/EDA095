package tcp3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class startItAll {
	
	public static void main (String [] args){
		Mailbox m = new Mailbox();
	
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for(int i = 0; i < 10; i++){
			pool.submit(new nameThread(m));
		}
		pool.submit(new readThread(m));

		
	}

}
