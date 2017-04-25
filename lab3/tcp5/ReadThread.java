package tcp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread implements Runnable{
	private Socket s;
	
	public ReadThread(Socket s){
		this.s = s;
	}

	@Override
	public void run() {
	
			try {
				InputStreamReader is = new InputStreamReader(s.getInputStream());
				BufferedReader bf = new BufferedReader(is);
				
				String temp ="";
				while((temp = bf.readLine()) != null){
					System.out.println(temp);
				}
				//s.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}

}
