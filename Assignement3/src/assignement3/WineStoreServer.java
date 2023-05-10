package assignement3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * The class {@code WineStoreServer} defines a server that waits for a message, 
 * generates a thread that manage the communication with the client and then 
 * sends an answer.
 *
 */

public class WineStoreServer {
	
	
	/**
	 * Class fields.
	 * 
	 * COREPOOL
	 * MAXPOOL
	 * IDLETIME
	 * SPORT
	 * socket
	 * pool
	 */
	
	private static final int COREPOOL = 5; // defines the number of threads assigned to the pool
	private static final int MAXPOOL = 100; // defines the max number of threads contained into the thread pool (100 thread totali)
	private static final long IDLETIME = 5000; // defines the time in milliseconds before a thread is put on the sleep list
	private static final int SPORT = 4444; // server port

	private ServerSocket socket; // server socket definition
	private ThreadPoolExecutor pool; // definition of a thread pool 

	
	/**
	 * Class constructor.
	 *
	 * @throws IOException if the creation of the server socket fails.
	 **/
	
	public WineStoreServer() throws IOException {
		this.socket = new ServerSocket(SPORT); // initialization (creation) of the server socket
	}

	
	/**
	 * 
	 * This method is used to run the server code.
	 *
	 **/
	
	private void run() {
		
		System.out.println("----------------------------------------------------------------");
		System.out.println("|                  SERVER IS RUNNING ON 4444                   |");
		System.out.println("----------------------------------------------------------------");
		
		 // ThreadPool creation
		
		this.pool = new ThreadPoolExecutor(COREPOOL, MAXPOOL, IDLETIME, TimeUnit.MILLISECONDS, 
				new LinkedBlockingQueue<Runnable>()); 

		// the server always runs these instructions
		
		while (true) {
			
			try {
				Socket s = this.socket.accept(); // the server accepts clients connections (The method blocks until a connection is made)

				this.pool.execute(new WineStoreServerThread(s)); // the server creates a thread to manage user requests
			} 
			catch (Exception e) {
				break;
			}
		}

		this.pool.shutdown();
	}

	/**
	 * 
	 * This method is used to get the server pool.
	 *
	 * @return the thread pool.
	 *
	 **/
	
	public ThreadPoolExecutor getPool() { // method used to get the server thread pool
		return this.pool;
	}

	/**
	 * 
	 * This method is used to close the server execution.
	 *
	 **/
	
	public void close() { // method used to close the server socket
		
		try {
			this.socket.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * Starts the execution of the server.
	 *
	 * @param args the method does not requires arguments.
	 *
	 * @throws IOException if the execution fails.
	 **/
	
	public static void main(final String[] args) throws IOException {
		new WineStoreServer().run(); 
	}
}
