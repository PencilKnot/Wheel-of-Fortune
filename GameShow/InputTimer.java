//package GameShow;

import java.util.Scanner;
import java.util.concurrent.*;

public class InputTimer extends Thread {

	private Scanner in = new Scanner(System.in);
	// Create a BlockingQueue to store input
	private BlockingQueue<String> input = new LinkedBlockingQueue<>();

	@Override
	public void run(){
		// Run thread to continuously read and queue input
		while(true){
			try{
				input.add(in.nextLine());
			}
			catch (Exception e){
				// End thread if exception occurs
				return;
			}
		}
	}

	// Get input after a specific time (value, unit)
	public String getInput(int timeout, TimeUnit unit) {
		try{
			// Attempt to poll (retrieve head of queue) a line of input from the queue with the timeout
			return input.poll(timeout, unit);
		}
		catch(InterruptedException e) {
			// Return null if exception occurs
			return null;
		}
	}
	
	@Override
	// Close scanner and interrupt thread
	public void interrupt(){
		in.close();
		super.interrupt();
	}
}
