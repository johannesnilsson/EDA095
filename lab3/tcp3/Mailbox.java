package tcp3;

public class Mailbox {
	
	String message ="";
	boolean noMessage = true;
	
	/**
	 * Add a message to the mailbox.
	 * @param s The message.
	 */
	public synchronized void addMessage(String s){
		// add message to string.
		message += " | " +s;
		noMessage = false;
		
		// notifies that a message is available to readThread
		notifyAll(); 
	}
	
	/**
	 * Method waits until there is a message to retrieve.
	 * @return The available message.
	 */
	public synchronized String removeMessage(){
		
		try{ 
			// we won't print empty messages with this condition
			// also, we dont need processor time until there actually is an message for us
			while(noMessage) wait();
			

		} catch (Exception e){
			e.printStackTrace();
		}
		// clear and retrieve the string holding message
		String temp = message;
		message = "";
		noMessage = true;
		return temp;

	}

}
