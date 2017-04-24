package tcp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable{
	private Socket s;
	
	public RequestHandler(Socket s){
		this.s = s;
	}

	@Override
	public void run() {
		try{
			System.out.println("New connection with \nIP: " + s.getInetAddress());

			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();

			InputStreamReader is2 = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(is2);

			String data = "";
			while ((data = bf.readLine()) != null) {
				os.write((data + "\n\r").getBytes());
				System.out.println(data);
			}

			// close the socket connection
			s.close();
			System.out.println("Connection closed...");
			
		} catch(IOException e){
			e.printStackTrace();
		}

		
	}

}
