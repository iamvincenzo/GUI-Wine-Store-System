package assignement3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import assignement3.communication.Customer;
import assignement3.communication.Order;
import assignement3.communication.RequestClose;
import assignement3.communication.Response;
import assignement3.communication.Wine;
import assignement3.communication.People;

import java.util.List;

/**
 *
 * The class {@code WineStoreServerThread} is used to handles the communication
 * between client and server thread.
 *
 **/

public class WineStoreServerThread implements Runnable {

	
	/**
	 * Class fields.
	 * 
	 * socket - It is the socket used by server thread to communicates with the client.
	 * SlEEPTIME - It is a constant that defines the rest time for each thread.
	 * monitor - It is the shared resource between server threads. It is used to access database that is the shared resource.
	 */

	private Socket socket;
	private static final long SLEEPTIME = 200;
	private static MonitorDB monitor = new MonitorDB();

	
	/**
	 * Class constructor.
	 * 
	 * @param c it is the client socket.
	 **/

	public WineStoreServerThread(final Socket c) { this.socket = c; }

	
	/** {@inheritDoc} **/
	@Override
	public void run() {

		ObjectInputStream is = null; // definition of an object input stream used to receive messages (objects) from clients
		
		ObjectOutputStream os = null; // definition of an object output stream used to send messages (objects) to clients

		try {
			is = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream())); // creation of an object input stream from the stream passed as argument
		}

		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String id_thread = String.valueOf(this.hashCode()); // thread id
		
		while (true) { 

			try { // when a thread is created by the server, it tries to communicate with the client
				
				Object i = is.readObject(); // thread waits for a request
				
				if (i instanceof People && ((People) i).getRequest().equals("login")) { // thread receives a request from the customer for login
	
					People rq = (People) i;
	
					Thread.sleep(SLEEPTIME); // between one operation and another there is a certain delay so, to ensure correct synchronization, the thread falls asleep for a while
	
					if (os == null) { // if object output stream has been never used
						
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream())); // creation of an object output stream
					}
	
					int value = monitor.login(rq.getEmail(), rq.getPassword(), rq.getRole(), id_thread);
					
					Response rs = new Response(value); // creation of the response to be sent to the client
					rs.setResponse("login"); // type of response: login
	
					os.writeObject(rs); // server thread sends response to the client
					os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
					if(rs.getResp() == 0) { // if wrong login then server closes the socket.
						
						this.socket.close();
						return;
					}
				}
				
				else if (i instanceof Customer && ((Customer) i).getRequest().equals("customerRegistration")) { // server thread receives from unregistered user a request for registration
	
					Customer rq = (Customer) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					int value = monitor.registration(rq.getName(), rq.getSurname(), rq.getEmail(), rq.getPassword(), rq.getAddress(), id_thread); 
	
					Response rs = new Response(value);
					rs.setResponse("registration");
					
					os.writeObject(rs);
					os.flush();
	
					this.socket.close(); // server closes the socket after user success registration [registration failed]
					return;
				}
	
				// server thread receives by unregistered user a request to search a wine	
					// server thread receives by administrator a request to view winestore's wines
						// server thread receives by employee a request to view wines he has ordered
							// server thread receives by employee a request to view finished wine
				
				else if (i instanceof Wine && (((Wine) i).getRequest().equals("search") || 
						((Wine) i).getRequest().equals("adminViewWine") || 
							((Wine) i).getRequest().equals("employeeViewOrderedWine") || 
								((Wine) i).getRequest().equals("employeeViewBuyFinishedWine"))) {
	
					Wine rq = (Wine) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					List<Wine> value = monitor.searchWine(rq.getName(), rq.getProducer(), rq.getYear(), id_thread, rq);					
					
					Response rs;
					
					if (value != null) { // if there are wine searched by user
						
						rs = new Response(value, (byte) 0); // it sends the wines as response
						rs.setResponse("search");
					}
	
					else {
						rs = new Response(0);						
						rs.setResponse("searchFail");
					}

					os.writeObject(rs);
					os.flush();
				}	
				
				else if (i instanceof Order && ((Order) i).getRequest().equals("customerBuy")) { // server thread receives by customer a request to buy wine
					
					Order rq = (Order) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					int value = monitor.buyWine(rq.getCodCustomer(), rq.getCodWine(), rq.getNumBottles(), rq.getDate(), rq.getTot(), id_thread);
					
					Response rs = new Response(value);
					rs.setResponse("buy");
					
					os.writeObject(rs);
					os.flush();
				}
				
				else if (i instanceof Order && ((Order) i).getRequest().equals("customerSubscribeNotification")) { // server thread receives a request by customer for subscribe a notification
					
					Order rq = (Order) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					int value = monitor.subscribeNotification(rq.getCodCustomer(), rq.getCodWine(), rq.getNumBottles(), id_thread);
						
					Response rs = new Response(value);
					rs.setResponse("notification");
					
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by customer a request to view orders history
					// server thread receives by customer a request to view orders history by date or id or both
				
				else if (i instanceof Order && ((Order) i).getRequest().equals("customerViewBySearch")) { 
	
					Order rq = (Order) i;

					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					List<Order> value = monitor.historyViewAll(rq.getIdC(), id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value);
						rs.setResponse("historyOrder");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("historyFail");
					}

					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by customer a request to view notification of availability that the system sends to the customer
					// server thread receives by customer a request to view notification of availability by date or wine name or both
				
				else if (i instanceof Order && ((Order) i).getRequest().equals("customerViewNotificationBySearch")) { 
	
					Order rq = (Order) i;
	
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					List<Order> value = monitor.notifyViewAll(rq.getIdC(), id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value);
						rs.setResponse("customerNotifyView");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("notifyFail");
					}

					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by customer a request to delete notification of availability that has been satisfied
				
				else if(i instanceof Order && ((Order) i).getRequest().equals("customerDeleteNotification")) {
					
					Order rq = (Order) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					List<Order> value = monitor.deleteNotification(id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value);
						rs.setResponse("customerDeleteNotification");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("failCustomerDeleteNotification");
					}

					os.writeObject(rs);
					os.flush();
				}
				
				if (i instanceof People && ((People) i).getRequest().equals("customerViewData")) { // server thread receives a request from the customer to view its information 
					
					People rq = (People) i;
	
					Thread.sleep(SLEEPTIME); 
					
					if (os == null) { 
						
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					List<People> value = monitor.returnCustomerInfo(rq, id_thread); 
					
					Response rs = new Response(value, (byte) 0, (byte) 0); // creation of the response to be sent to the client
					rs.setResponse("customerViewData"); 
	
					os.writeObject(rs); 
					os.flush(); 
				}
				
				if (i instanceof People && ((People) i).getRequest().equals("customerUpdateCustomer")) { // server thread receives a request from customer to update its data
					
					People rq = (People) i;
	
					Thread.sleep(SLEEPTIME); 
	
					if (os == null) { 
						
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					int value = monitor.customerUpdateCustomer(id_thread, rq); 
					
					Response rs = new Response(value); 
					rs.setResponse("customerUpdateCustomer"); 
	
					os.writeObject(rs); 
					os.flush(); 
				}
				
				// server thread receives by employee a request to view orders to delivery by id or date or both
					// server thread receives by administrator a request to view all orders
			
				else if(i instanceof Order && (((Order) i).getRequest().equals("employeeViewDeliveryBySearch") || 
					((Order) i).getRequest().equals("adminViewBySearch"))) {
				
					Order rq = (Order) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					List<Order> value = monitor.orderViewBySearch(id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value);
						rs.setResponse("adminEmployeeViewBySearch");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("failAdminEmployeeViewBySearch");
					}
	
					os.writeObject(rs);
					os.flush();
				}
				
				else if(i instanceof Order && (((Order) i).getRequest().equals("shipAll") || 
						((Order) i).getRequest().equals("shipSelected")))  { // server thread receives by employee a request for delivery wines to the customers
					
					Order rq = (Order) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) { 
						os = new ObjectOutputStream(new	BufferedOutputStream(this.socket.getOutputStream())); 
					}
					
					String value = monitor.shipOrders(id_thread, rq);
					
					Response rs;
					
					if (!value.isEmpty()) {
						rs = new Response(value);
						rs.setResponse("shipAllOK");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("failShipAll");
					}

					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by employee a request to buy.
					// 1) bottles of wine that are already in the system which bottles are 0.
					// 2) bottles of wine that are already in the system but also for the buying of new.

				else if (i instanceof Wine && (((Wine) i).getRequest().equals("employeeUpdateFinishedWine")  || 
						((Wine) i).getRequest().equals("employeeUpdateAlreadyExistWine"))) { 
					
					Wine rq = (Wine) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					List<Wine> value = monitor.buyFinishedWine(id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value, (byte) 0);
						if(rq.getRequest().equals("employeeUpdateFinishedWine")) rs.setResponse("employeeUpdateFinishedWine");
						if(rq.getRequest().equals("employeeUpdateAlreadyExistWine")) rs.setResponse("employeeUpdateAlreadyExistWine");
					}
	
					else {
						rs = new Response(0);
						if(rq.getRequest().equals("employeeUpdateFinishedWine")) rs.setResponse("failEmployeeUpdateFinishedWine");
						if(rq.getRequest().equals("employeeUpdateAlreadyExistWine")) rs.setResponse("failEmployeeUpdateAlreadyExistWine");
					}
	
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by employee a request to reload bottles
				// both finished bottles (finished wines) or new ordered (by employee) bottles
			
				else if(i instanceof Wine && ((Wine) i).getRequest().equals("employeeUpdateWine")) {
					
					Wine rq = (Wine) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					List<Wine> value = monitor.employeeUpdateWine(id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value, (byte) 0);
						rs.setResponse("employeeUpdateWine");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("failEmployeeUpdateWine");
					}
	
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by administrator a request to view people inside database
					// customers & employees
				
				else if (i instanceof People && (((People) i).getRequest().equals("adminViewCustomer") || 
						((People) i).getRequest().equals("adminViewEmployee"))) { 
					
					People rq = (People) i;
					
					Thread.sleep(SLEEPTIME);
	
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
	
					List<People> value = monitor.viewPeople(id_thread, rq);
					
					Response rs;
					
					if (value != null) {
						rs = new Response(value, (byte) 0, (byte) 0);
						rs.setResponse("viewPeople");
					}
	
					else {
						rs = new Response(0);
						rs.setResponse("failViewPeople");
					}

					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by administrator a request to add people into the system
					// customers & employees
				
				else if (i instanceof People && (((People) i).getRequest().equals("adminAddCustomer") || 
						((People) i).getRequest().equals("adminAddEmployee"))) {
					
					People rq = (People) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					int value = monitor.adminAddPeople(id_thread, rq);
					
					Response rs;
					
					rs = new Response(value);
					rs.setResponse("adminAddPeople");
					
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by administrator a request to remove people from the system
					// customers & employees
				
				else if (i instanceof People && (((People) i).getRequest().equals("adminDeleteCustomer") || 
						((People) i).getRequest().equals("adminDeleteEmployee"))) {
					
					People rq = (People) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					int value = monitor.adminDeletePeople(id_thread, rq);
					
					Response rs = new Response(value);
					rs.setResponse("adminDeletePeople");
	
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by administrator a request to update people data
				// customers & employees
			
				else if (i instanceof People && (((People) i).getRequest().equals("adminUpdateCustomer") || 
						((People) i).getRequest().equals("adminUpdateEmployee"))) {
					
					People rq = (People) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					int value = monitor.adminUpdatePeople(id_thread, rq);
					
					Response rs = new Response(value);
					rs.setResponse("adminUpdatePeople");
	
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by administrator a request to add wine into the system
					// server thread receives by employee a request to buy a new wine that is not already into the system
				
				else if(i instanceof Wine && (((Wine) i).getRequest().equals("adminAddWine") || 
						((Wine) i).getRequest().equals("employeeBuyNewWine"))) {
					
					Wine rq = (Wine) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					int value = monitor.adminEmployeeAddWine(id_thread, rq);
					
					Response rs = new Response(value);
					
					if(rq.getRequest().equals("adminAddWine")) rs.setResponse("adminAddWine");
					if(rq.getRequest().equals("employeeBuyNewWine")) rs.setResponse("employeeBuyNewWine");
					
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread receives by administrator a request to remove a wine from the system
				
				else if (i instanceof Wine && (((Wine) i).getRequest().equals("adminDeleteWine"))) {
					
					Wine rq = (Wine) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					int value = monitor.adminDeleteWine(id_thread, rq);
					
					Response rs = new Response(value);
					rs.setResponse("adminDeleteWine");
					
					os.writeObject(rs);
					os.flush();
				}
				
				// server thread  receives by administrator a request to update wine system date
				
				else if (i instanceof Wine && ((Wine) i).getRequest().equals("adminUpdateWine")) {
					
					Wine rq = (Wine) i;
					
					Thread.sleep(SLEEPTIME);
					
					if (os == null) {
						os = new ObjectOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
					}
					
					int value = monitor.adminUpdateWine(id_thread, rq);
					
					Response rs = new Response(value);
					rs.setResponse("adminUpdateWine");
	
					os.writeObject(rs);
					os.flush();
				}
				
				else if (i instanceof RequestClose) { // thread receives by client a request for closing socket
					
					RequestClose rq = (RequestClose) i;
					
					System.out.println("\n-----------");		
					System.out.println("|   LOG   |");
					System.out.println("-----------");	
					  
					System.out.format("thread %s receives: (request for close socket) from its client%n", id_thread);
					  
					Thread.sleep(SLEEPTIME);
					
					if (os == null) { 
						os = new ObjectOutputStream(new	BufferedOutputStream(this.socket.getOutputStream())); 
					}
					
					if(rq.getV()) {
						Response rs = new Response(1); // creation of the response to be sent to the client
						rs.setResponse("close");
						
						os.writeObject(rs);
						os.flush();	
						
						System.out.format("thread %s sends: ok to its client%n", id_thread);
						this.socket.close(); // server closes the socket 
						return;
					}
				}
			}
			
			catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
