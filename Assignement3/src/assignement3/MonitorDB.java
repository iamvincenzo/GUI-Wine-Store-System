package assignement3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import assignement3.communication.Order;
import assignement3.communication.People;
import assignement3.communication.Wine;

import java.util.LinkedList;

/**
 *
 * The class {@code MonitorDB} is used to manage the concurrency access to the
 * shared resource: database.
 * 
 */

public class MonitorDB {
	
	
	/**
	 * Class fields.
	 * 
	 * DB - It is the string to connect to the database. 
	 */
	
	private String DB = "jdbc:mysql://localhost:3306/winestore_dipalma_299636_fraello_299647?user=root&password=root&serverTimezone=Europe/Rome";
	
	
	/**
	 * This method is used to check that the user is registered.
	 * 
	 * @param usr It is the people email.
	 * @param pwd It is the people password.
	 * @param role It is the people role.
	 * @param id_thread It is the thread id.
	 * @return It returns the people id (login success) or zero (login failed).
	 */

	protected synchronized int login(final String usr, final String pwd, final String role, final String id_thread) {

		String queryLogIn = "SELECT * FROM " + role + ";";
		char c = role.toUpperCase().charAt(0);

		if (role.equalsIgnoreCase("customer")) {
			
			// the flag Activated_C is used to indicates if account is active.
				// Administrator can't remove a user that made purchases because foreign key constraints
					// so when he clicks on remove he disables customer account
			
			// login can be made only by users whose account is active

			queryLogIn = "SELECT * FROM " + role + " WHERE Activated_C = 1"; 
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: %s, %s (request login) from its client%n", id_thread, usr, pwd);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(queryLogIn);

			while (myRs.next()) {

				if (myRs.getString("Email_" + c).equals(usr) && myRs.getString("Password_" + c).equals(pwd)) { // if credential supplied by user are correct the user can login

					System.out.format("thread %s sends: ok to its client%n", id_thread);
					return myRs.getInt("ID_" + c); // it returns the customer database id (positive number)
				}
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail login to its client%n", id_thread);
		return 0; // it returns zero if login fail
	}
	
	
	/**
	 * This method is used to register unregistered users into the database.
	 * It stores data entered by unregistered users.
	 * 
	 * @param n It is the user name.
	 * @param s It is the user surname.
	 * @param e It is the user email.
	 * @param p It is the user password.
	 * @param a It is the user address.
	 * @param id_thread It is the thread id.
	 * @return It returns zero if user's already registered, one if registration success.
	 */

	protected synchronized int registration(final String n, final String s, final String e, final String p, final String a, final String id_thread) {

		String notRegistered = "SELECT Email_C, Activated_C FROM customer WHERE Email_C = " + "'" + e + "'" + ";";

		String insert = "INSERT INTO customer VALUES (null, '" + n + "', '" + s + "','" + e + "','" + p + "','" + a
				+ "', 1);";

		String updateFlagActived = "UPDATE customer SET Activated_C = 1, Name_C = '" + n 
								+ "' ,Surname_C = '" + s 
								+ "' ,Password_C = '" + p 
								+ "' ,Address_C = '" + a 
								+ "' WHERE Email_C = '" + e + "';";

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: %s (request registration) from its client%n", id_thread, e);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(notRegistered);

			while (myRs.next()) {
				
				if (myRs.getString("Email_C").equals(e) && (myRs.getByte("Activated_C") == (byte) 1)) { // user is already registered and its account is active (administrator doesn't remove customer from the system)

					System.out.format("thread %s sends: fail registration to its client%n", id_thread);
					return 0;
				}

				else if (myRs.getString("Email_C").equals(e) && (myRs.getByte("Activated_C") == (byte) 0)) { // user is already registered and its account is disabled
					
					myStmt.executeUpdate(updateFlagActived); // account reactivation

					System.out.format("thread %s sends: ok to its client%n", id_thread);
					return 1;
				}
			}

			myStmt.executeUpdate(insert); // user isn't register: account creation
			
			System.out.format("thread %s sends: ok to its client%n", id_thread);
			return 1;
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		return 0;
	}
	
	
	/**
	 * This method is used to search one or more wines into the database.
	 * This method is used to search finished wines stored into database.
	 * This method is used to show wines contained into system database to employee.
	 * This method is used to show to administrator wines contained into system database.
	 * 
	 * @param n It is the wine name.
	 * @param p It is the wine producer.
	 * @param y It is the wine year.
	 * @param id_thread It is the thread id.
	 * @param rq It is the wine object.
	 * @return It returns a list that contains the wine searched by customers or administrator or employee.
	 */

	protected synchronized List<Wine> searchWine(final String n, final String p, final String y, final String id_thread, final Wine rq) {

		String search = "";
		
		if(rq.getRequest().equals("search")) {
		
			search = "SELECT * "
					+ "FROM wine "
					+ "WHERE Name_W = COALESCE(nullif('" + rq.getName() + "', ''), Name_W) AND "
					+ "Producer_W = COALESCE(nullif('" + rq.getProducer() + "', ''), Producer_W) AND "
					+ "Year_W = COALESCE(nullif('" + rq.getYear() + "', ''), Year_W);";
					
		}

		if (rq.getRequest().equals("adminViewWine")) {
			
			search = "SELECT * FROM wine;";
		}

		else if (rq.getRequest().equals("employeeViewOrderedWine")) {
			
			search = "SELECT * "
				   + "FROM orderedwineemployee "
				   + "WHERE Uploaded_W_E = 0"; // select only uncharged wines. This tables contains wines bought by employee
		}
		
		else if(rq.getRequest().equals("employeeViewBuyFinishedWine")) {
			
			// the flag Visualized_W is used to show the employee the notification until he 
				// purchase the bottles of that wine. Once purchased, this flag is set to one. 
					// When it is set to one, the employee is no longer notified of the lack
			
			search = "SELECT * "
				   + "FROM wine "
				   + "WHERE NumBottles_W = 0 "
				   + "AND Visualized_W = 0"; // this query select only finished wines.
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request search wine) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(search);

			List<Wine> l = new LinkedList<>();

			if (rq.getRequest().equals("search")) { // customer request

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W"), myRs.getString("Name_W"), myRs.getString("Producer_W"),
							myRs.getString("Year_W"), myRs.getString("TechnicalNotes_W"), myRs.getString("Vines_W"),
							myRs.getInt("NumBottles_W"), myRs.getFloat("Price_W")));
				}
			}

			else if (rq.getRequest().equals("adminViewWine") || 
					rq.getRequest().equals("employeeViewBuyFinishedWine")) { // employee or administrator request

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W"), myRs.getString("Name_W"), myRs.getString("Producer_W"),
							myRs.getString("Year_W"), myRs.getString("TechnicalNotes_W"), myRs.getString("Vines_W"),
							myRs.getInt("NumBottles_W"), myRs.getFloat("Price_W")));
				}
			}

			else if (rq.getRequest().equals("employeeViewOrderedWine")) { // employee request (ordered wine). It is used to view wines that have to be reloaded

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W_E"), myRs.getString("Name_W_E"), myRs.getString("Producer_W_E"),
							myRs.getString("Year_W_E"), myRs.getString("TechnicalNotes_W_E"),
							myRs.getString("Vines_W_E"), myRs.getInt("NumBottles_W_E"), myRs.getFloat("Price_W_E")));
				}
			}
			
			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail search wine to its client%n", id_thread);
		return null;
	}

	
	/**
	 * This method is used to decreases the number of bottles of a specific wine
	 * inserted into the database after a customer purchase. It is also used to save
	 * the customer order inside database.
	 * This method also tracks if the wine runs out.
	 * 
	 * @param codC It is the customer id.
	 * @param codW It is the wine id.
	 * @param nB It is the number of bottles desired by customer.
	 * @param d It is the timestamp that indicates the date-time of purchase.
	 * @param tot It is the amount of money paid by customer.
	 * @param id_thread It is the thread id.
	 * @return It returns one if the order was successful or zero if the order was unsuccessful.
	 */

	protected synchronized int buyWine(final int codC, final int codW, final int nB, final Timestamp d, final float tot,
			final String id_thread) {

		String search = "SELECT * FROM wine WHERE ID_W = " + codW + ";";
		
		String updateW = "UPDATE wine SET NumBottles_W = NumBottles_W - " + nB + " WHERE ID_W = " + codW + ";";
		
		String insertOrd = "INSERT INTO orders(CodCustomer_O, CodWine_O, NumBOttles_O, Data_O, Tot_O) "
						 + "VALUES ('" + codC + "', '" + codW + "', '" + nB + "', '" + d + "', '" + tot + "');";

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request buy wine) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(search);

			while (myRs.next()) {

				if (myRs.getInt("NumBottles_W") - nB == 0) { 
					
					// if customer purchase all bottles than the flag Visualized_W is set
						// to zero so that the system notify employee that wine is finished

					updateW = "UPDATE wine SET NumBottles_W = NumBottles_W - " + nB + ", Visualized_W = 0  WHERE ID_W = " + codW + ";";
				}

				else {
					
					updateW = "UPDATE wine SET NumBottles_W = NumBottles_W - " + nB + " WHERE ID_W = " + codW + ";";
				}

				myStmt.executeUpdate(updateW);
				myStmt.executeUpdate(insertOrd);
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return 1;
			}
		} 
		
		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail buy wine to its client%n", id_thread);
		return 0;
	}

	
	/**
	 * This method is used to save the notification subscribed by customer into the
	 * database.
	 * 
	 * @param codC It is the customer id.
	 * @param codW It is the wine id.
	 * @param nB It is the number of bottles desired by customer.
	 * @param id_thread It is the thread id.
	 * @return It returns one if the notification was successful subscribed or zero if the notification was unsuccessful.
	 */

	protected synchronized int subscribeNotification(final int codC, final int codW, final int nB, final String id_thread) {

		String insertNotification = "INSERT INTO notification(CodCustomer_N, CodWine_N, NumBottles_N) "
								  + "VALUES ('" + codC + "', '" + codW + "', '" + nB + "');";

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request subscribe notification) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(insertNotification);

			System.out.format("thread %s sends: ok to its client%n", id_thread);
			return 1;
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail subscribe notification to its client%n", id_thread);
		return 0;
	}
	
	
	/**
	 * This method is used to show to the customer his order history list. 
	 * 
	 * @param codC It is the customer id.
	 * @param id_thread It is the thread id.
	 * @param rq It is the Order object.
	 * @return It returns a list that contains orders.
	 */

	protected synchronized List<Order> historyViewAll(final int codC, final String id_thread, final Order rq) {

		// this query allows to validate condition only of fields that has been compiled
			// for example search only for date or only for name or both
		
		String selectAllHistory = "SELECT ID_O, Name_W, Producer_W, Year_W, NumBOttles_O, Completed_O, Data_O, Tot_O "
				+ "FROM orders, wine "
				+ "WHERE CodCustomer_O = " + codC + " "
				+ "AND CodWine_O = ID_W "
				+ "AND Name_W = COALESCE(nullif('" + rq.getNameW() + "', ''), Name_W) "
				+ "AND DATE(Data_O) = COALESCE(nullif('" + rq.getDate() + "', 'null'), Data_O);";   
		
		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for history client order) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(selectAllHistory);

			List<Order> l = new LinkedList<>();


			while (myRs.next()) {

				l.add(new Order(myRs.getInt("ID_O"), null, myRs.getString("Name_W"), myRs.getString("Producer_W"),
						myRs.getString("Year_W"), myRs.getInt("NumBOttles_O"), myRs.getByte("Completed_O"),
						myRs.getTimestamp("Data_O"), myRs.getFloat("Tot_O")));
			}

			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail history to its client%n", id_thread);
		return null;
	}

	
	/**
	 * This method is used to show to the customer his list of notification that the
	 * system sent to him when a wine is available.
	 * 
	 * @param codC It is the customer id.
	 * @param id_thread It is the thread id.
	 * @param rq It is the order info.
	 * @return It returns a list that contains all notification of wines availability.
	 */

	protected synchronized List<Order> notifyViewAll(final int codC, final String id_thread, final Order rq) { 
			
		// the flag FlagSent_N is used to notify user that its request for notification has been satisfied
			// when user subscribe a notification, by default the value of the flag is zero.
				// when employee reload bottles if bottles are enough than the flag will be set to one.
		
		// this query allows to validate condition only of fields that has been compiled
			// for example search only for date or only for name or both
		
		String selectAllNotify = "SELECT ID_N, Name_W, Producer_W, Year_W, NumBottles_N, Date_N "
						+ "FROM notification, wine "
						+ "WHERE CodCustomer_N = " + codC + " "
						+ "AND CodWine_N = ID_W "
						+ "AND Name_W = COALESCE(nullif('" + rq.getNameW() + "', ''), Name_W) " // null
						+ "AND DATE(Date_N) = COALESCE(nullif('" + rq.getDate() + "', 'null'), Date_N) "
						+ "AND FlagSent_N = 1;";   

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");
		
		System.out.format("thread %s receives: (request for notification client list) from its client%n", id_thread, codC);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(selectAllNotify);

			List<Order> l = new LinkedList<>();

			while (myRs.next()) {
				
				l.add(new Order(myRs.getInt("ID_N"), myRs.getString("Name_W"), myRs.getString("Producer_W"),
						myRs.getString("Year_W"), myRs.getInt("NumBottles_N"), myRs.getTimestamp("Date_N")));
			}

			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail notification to its client%n", id_thread);
		return null;
	}
	
	
	/**
	 * This method is used to delete a request for notification (availability of wine)
	 * from the database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the notification to remove.
	 * @return It returns the list that contains the remaining request for notification.
	 */

	protected synchronized List<Order> deleteNotification(final String id_thread, final Order rq) {

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for delete notification (customer) list) from its client%n",
				id_thread);

		String deleteNotification = "DELETE FROM notification WHERE ID_N = " + rq.getIdO() + ";";

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(deleteNotification);

			List<Order> l = new LinkedList<>();
			
			rq.setRequest("customerNotifyView");		
			
			l = notifyViewAll(rq.getIdC(), id_thread, rq);

			if (l != null) {

				if (!l.isEmpty()) {
					System.out.format("thread %s sends: ok to its client%n", id_thread);
					return l;
				}
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: last delete notification finished wine to its client%n", id_thread);
		return null;
	}
	
	
	/**
	 * This method is used to returns to the customer its account data.
	 * So it can eventually modify its data.
	 * 
	 * @param rq It is the customer to show the credentials to.
	 * @param id_thread It is the thread id.
	 * @return It returns customer data.
	 */
	
	protected synchronized List<People> returnCustomerInfo(final People rq, final String id_thread) {
		
		String queryViewInfo = "SELECT * FROM customer WHERE ID_C = " + rq.getId() + " AND Activated_C = 1;"; // the account has to be active

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: request for view customer info from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();
			
			List<People> l = new LinkedList<>();

			ResultSet myRs = myStmt.executeQuery(queryViewInfo);

			while (myRs.next()) {
				
				l.add(new People(myRs.getInt("ID_C"), myRs.getString("Name_C"), myRs.getString("Surname_C"),
						myRs.getString("Email_C"), myRs.getString("Password_C"), myRs.getString("Address_C")));
			}
			
			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail view credential to its client%n", id_thread);
		return null;
	}
	
	
	/**
	 * This method is used to modify the customer data stored into the database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the customer that indicates data to modify.
	 * @return It returns an integer number that indicates the outcome of the operation.
	 */
	
	protected synchronized int customerUpdateCustomer(final String id_thread, final People rq) {
		
		// this query allows to update only fields that has been compiled

		String updatePeople = "UPDATE customer SET Name_C = COALESCE(nullif('" + rq.getName() + "', ''), Name_C), "
					+ "Surname_C = COALESCE(nullif('" + rq.getSurname() + "', ''), Surname_C), "
					+ "Email_C = COALESCE(nullif('" + rq.getEmail() + "', ''), Email_C), "
					+ "Password_C = COALESCE(nullif('" + rq.getPassword() + "', ''), Password_C), "
					+ "Address_C = COALESCE(nullif('" + rq.getAddress() + "', ''), Address_C)" + "WHERE ID_C ="
					+ rq.getId() + ";";


		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for update customer data (customer) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(updatePeople);
			return 1;
		}

		catch (Exception exc) {
			exc.printStackTrace();
			System.out.format("thread %s sends: fail update people to its client%n", id_thread);
			return 0;
		}
	}
	
	
	/**
	 * This method is used by employee to view orders to delivery by searching for date 
	 * or customer id or both or none.
	 * This method is also used by administrator to view all orders history stored
	 * into system database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the order to search.
	 * @return It returns a list that contains all orders that satisfy conditions.
	 */
	
	protected synchronized List<Order> orderViewBySearch(final String id_thread, final Order rq) {
		
		String selectAllNotify = "";
		
		if(rq.getRequest().equals("employeeViewDeliveryBySearch")) {
			
			// this query allows to validate condition only of fields that has been compiled
				// for example search only for date or only for id or both	
			
			selectAllNotify = "SELECT ID_O, Name_C, Address_C, Email_C, Name_W, Producer_W, Year_W, NumBOttles_O, Completed_O, Data_O, Tot_O "
					+ "FROM orders, wine, customer "
					+ "WHERE CodCustomer_O = ID_C "
					+ "AND CodWine_O = ID_W "
					+ "AND Completed_O = 0 "
					+ "AND ID_C = COALESCE(nullif(" + rq.getIdC() + ", -1), ID_C) "
					+ "AND DATE(Data_O) = COALESCE(nullif('" + rq.getDate() + "', 'null'), Data_O);";
		}
		
		if(rq.getRequest().equals("adminViewBySearch")) {
			
			// this query allows to validate condition only of fields that has been compiled
				// for example search only for date or only for id or both
		
			selectAllNotify = "SELECT ID_O, Name_C, Address_C, Email_C, Name_W, Producer_W, Year_W, NumBOttles_O, Completed_O, Data_O, Tot_O "
							+ "FROM orders, wine, customer "
							+ "WHERE CodCustomer_O = ID_C "
							+ "AND CodWine_O = ID_W "
							+ "AND ID_C = COALESCE(nullif(" + rq.getIdC() + ", -1), ID_C) "
							+ "AND DATE(Data_O) = COALESCE(nullif('" + rq.getDate() + "', 'null'), Data_O);";
		}
		
		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for view by search client list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(selectAllNotify);

			List<Order> l = new LinkedList<>();
			
			if(rq.getRequest().equals("employeeViewDeliveryBySearch")) {

				while (myRs.next()) {
	
					l.add(new Order(myRs.getInt("ID_O"),  myRs.getString("Name_C"), 
							myRs.getString("Address_C"), myRs.getString("Name_W"), 
							myRs.getString("Producer_W"), myRs.getString("Year_W"),
							myRs.getInt("NumBOttles_O")));
				}
			}
			
			else if(rq.getRequest().equals("adminViewBySearch")) {
				
				while (myRs.next()) {
					
					l.add(new Order(myRs.getInt("ID_O"), myRs.getString("Email_C"), myRs.getString("Name_W"),
							myRs.getString("Producer_W"), myRs.getString("Year_W"), myRs.getInt("NumBOttles_O"),
							myRs.getByte("Completed_O"), myRs.getTimestamp("Data_O"), myRs.getFloat("Tot_O")));
				}
			}

			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail notification to its client%n", id_thread);
		return null;
	}
	

	/**
	 * This method is used to change the value of the flag that indicates if an
	 * order has been shipped.
	 * It is used to ship orders.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the order/orders to ship.
	 * @return It returns a message with all ship information.
	 */

	protected synchronized String shipOrders(final String id_thread, final Order rq) { 
		
		String updateDeliveryAll = null;
		String selectOrders = null;
		
		if(rq.getRequest().equals("shipAll")) {
			
			updateDeliveryAll = "UPDATE orders SET Completed_O = 1 WHERE Completed_O = 0;"; // update only orders that have not yet shipped
		
			selectOrders = "SELECT ID_O, Name_C, Address_C , Name_W, Producer_W, Year_W, Data_O "
					+ "FROM  orders, customer, wine "
					+ "WHERE ID_C = CodCustomer_O "
					+ "AND ID_W = CodWine_O "
					+ "AND Completed_O = 0"; // si selezionano quelli con Completed_O a zero perchè altrimenti verrebbero selezionati TUTTI gli ordini della tabella se mettessi uno
		}
		
		else if(rq.getRequest().equals("shipSelected")) {
			
			updateDeliveryAll = "UPDATE orders SET Completed_O = 1 WHERE Completed_O = 0 AND ID_O =  " + rq.getIdO() + ";"; // update only orders that have not yet shipped

			selectOrders = "SELECT ID_O, Name_C, Address_C , Name_W, Producer_W, Year_W, Data_O "
					+ "FROM  orders, customer, wine "
					+ "WHERE ID_C = CodCustomer_O "
					+ "AND ID_W = CodWine_O "
					+ "AND ID_O = " + rq.getIdO() + " "
					+ "AND Completed_O = 1"; // si seleziona solamente l'ordine spedito
		}

		String tot = "";
		String res0 = "\nThe order: ";
		String res1 = " has been shipped to ";
		String res2 = " at the address ";
		String res3 = " on: ";

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for delivery orders) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();
			
			// si mettono questi due controlli sul tipo di richiesta perchè nel caso di send all
				// prima si selezionano tutti gli ordini e poi si aggiorna lo stato dell'ordine
					// altrimenti la query di selezione non ritornerebbe nessun risultato in quanto
						// il flag Completed_O è e stato aggiornato a priori
			
			// nel caso di send selected invece si devono ritornare solo gli ordini che sono effettivamente 
				// stati spediti quindi, non quelli che hanno il flag Completed_O = 0
			
			if(rq.getRequest().equals("shipSelected")) myStmt.executeUpdate(updateDeliveryAll); // the order of the operation is different for send selected and send all

			ResultSet myRs = myStmt.executeQuery(selectOrders);

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			while (myRs.next()) {
				
				// creation of a series of messages that will be show to the employee

				tot += (res0 + myRs.getString("ID_O") + " [" + myRs.getString("Name_W") + ", "
						+ myRs.getString("Producer_W") + ", " + myRs.getString("Year_W") + "] " + res1
						+ myRs.getString("Name_C") + res2 + myRs.getString("Address_C") + res3 + timestamp + "\n");
			}

			if(rq.getRequest().equals("shipAll")) myStmt.executeUpdate(updateDeliveryAll);

			System.out.format("thread %s sends: ok delivery all orders to its client%n", id_thread);
			return tot;
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail delivery all orders to its client%n", id_thread);
		return null;
	}
	

	/**
	 * This method is used to insert wines into the table that contains all wines bought by employee.
	 * Once wines arrives into the store employee can reload wines by queryng this table.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the Wine to buy.
	 * @return It returns a list that contains the remaining wines to buy.
	 */
	
	protected synchronized List<Wine> buyFinishedWine(final String id_thread, final Wine rq) {

		String insertWine = "INSERT INTO orderedwineemployee(Name_W_E, Producer_W_E, Year_W_E, TechnicalNotes_W_E, "
				         + "Vines_W_E, NumBottles_W_E, Price_W_E) " + " "
						 + "VALUES('" + rq.getName() + "', '" + rq.getProducer()
				         + "', '" + rq.getYear() + "', '" + rq.getTechnicalNotes() + "', '" + rq.getVines() + "', "
				         + rq.getNumBottles() + ", " + (rq.getPrice() * 0.8) + ");"; // by default employee buys bottles at a lower price than selling price

		String selectWine = "";

		String updateWine = "";

		if (rq.getRequest().equals("employeeUpdateFinishedWine")) {

			selectWine = "SELECT * "
					   + "FROM wine "
					   + "WHERE NumBottles_W = 0 "
					   + "AND Visualized_W = 0";

			updateWine = "UPDATE wine "
					   + "SET Visualized_W = 1 " // this make sure that notification of lack is view only once
					   + "WHERE ID_W = " + rq.getId() + ";";
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for buy finished wine (employee)) from its client%n",
				id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			if (rq.getRequest().equals("employeeUpdateFinishedWine")) {
				
				myStmt.executeUpdate(updateWine); // updating flag
			}

			myStmt.executeUpdate(insertWine); // insert wine bought by employee into the table of wines bought by employee

			ResultSet myRs = null;

			if (rq.getRequest().equals("employeeUpdateFinishedWine")) {
				
				myRs = myStmt.executeQuery(selectWine); // select the remaining finished wines to buy
			}

			List<Wine> l = new LinkedList<>();

			if (rq.getRequest().equals("employeeUpdateFinishedWine")) {

				while (myRs.next()) {

					l.add(new Wine(myRs.getInt("ID_W"), myRs.getString("Name_W"), myRs.getString("Producer_W"),
							myRs.getString("Year_W"), myRs.getString("TechnicalNotes_W"), myRs.getString("Vines_W"),
							myRs.getInt("NumBottles_W"), myRs.getFloat("Price_W")));
				}

				if (!l.isEmpty()) {
					System.out.format("thread %s sends: ok to its client%n", id_thread);
					return l;
				}
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail buy finished wine wine to its client%n", id_thread);
		return null;
	}
	
	
	/**
	 * This method is used by employee to reload bottles of wines
	 * into the system database after employee purchase that wines. 
	 * This method is also used to tracks the availability of wines for customers who subscribed 
	 * a notification.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the wine to update.
	 * @return It return a list that contains the remaining wines to reload.
	 */
	
	protected synchronized List<Wine> employeeUpdateWine(final String id_thread, final Wine rq) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		// questa query è usata per estrarre il vino che eventualmente corrisponde a quello acquistato
				// (residente nella tabella dei vini acquistati dall'utente) poichè
					// non si possono avere duplicati nel sistema. Le bottiglie cricate si sommano a quelle esistenti dello
						// stesso vino

		String selectWine = "SELECT * FROM wine WHERE Name_W = " + "'" + rq.getName() + "' AND Producer_W = " + "'"
				+ rq.getProducer() + "' AND Year_W = " + "'" + rq.getYear() + "';";
		
		// questa query è usata per aggiornare il numero di bottiglie di un vino

		String updateWine = "UPDATE wine SET NumBottles_W = NumBottles_W + " + rq.getNumBottles() + " WHERE Name_W = "
				+ "'" + rq.getName() + "' AND Producer_W = " + "'" + rq.getProducer() + "' AND Year_W = " + "'"
				+ rq.getYear() + "';";
		
		// questa query è usata per inserire il vino nel sistema nel qual caso quello comprato non  esistesse ancora

		String insertWine = "INSERT INTO wine(Name_W, Producer_W, Year_W, TechnicalNotes_W, Vines_W, NumBottles_W, Price_W) "
				+ "VALUES('" + rq.getName() + "', '" + rq.getProducer() + "', '" + rq.getYear() + "', '"
				+ rq.getTechnicalNotes() + "', '" + rq.getVines() + "'," + rq.getNumBottles() + "," + rq.getPrice()
				+ ");";
		
		// questa query è usata per estrarre dalla tabella delle notifiche, tutte le richieste di notifica di disponibilità
			// per un determinato vino. Verranno estratte solo le notifiche per il vino che si ricarica e che hanno un numero
				// di bottiglie di richiesta minore o uguale a quelle ricaricate

		String selectNotification = "SELECT ID_N " + "FROM notification as w, wine "
				+ "WHERE CodWine_N in (SELECT ID_W " + "FROM wine as w1 " + "WHERE w.NumBottles_N <= w1.NumBottles_W "
				+ "AND Name_W = '" + rq.getName() + "'AND Producer_W = '" + rq.getProducer() + "' AND Year_W = '"
				+ rq.getYear() + "') " + "GROUP BY ID_N;";

		// si aggiorna il flag che indica che un vino è già stato caricato in modo tale che non venga
			// ripresentato all'impiegato come vino le cui bottiglie devono ancora essere caricate
		
		String updateFlagUploaded = "UPDATE orderedwineemployee SET Uploaded_W_E = 1 WHERE ID_W_E = " + rq.getId()
				+ ";";

		// si aggiorna il flag della notifica nel qual caso la richiesta di disponibilità soddifsi il vino e la quantità
			// cosicchè venga notificata all'utente la disponibilità
		
		String updateFlagNotification = "UPDATE notification SET FlagSent_N = 1, Date_N = " + "'" + timestamp
				+ "' WHERE ID_N = ";

		// alla fine si selezionano dalla tabella dei vini acquistati solo quelli che devono ancora essere caricati
		
		String search = "SELECT * FROM orderedwineemployee WHERE Uploaded_W_E = 0"; // select only uncharged wines

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for update wine (employee)) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			List<Wine> l = new LinkedList<>();
			List<Integer> integer = new LinkedList<>();

			ResultSet myRs = myStmt.executeQuery(selectWine); // controllo che il vino di cui si vuole fare l'update non
																// sia già presente nel negozio

			while (myRs.next()) {

				myStmt.executeUpdate(updateWine); // se il vino è già presente nel negozio allora aggiorno il numero di
													// bottiglie

				myStmt.executeUpdate(updateFlagUploaded); // se il vino è stato aggiornato allora si segna che questo è
															// stato caricato già nel db dei vini del negozio

				ResultSet myRs1 = myStmt.executeQuery(selectNotification); // estraggo da tutte le richieste di
																			// notifiche per un dato vino solo quelle il
																			// cui numero di bottiglie desiderate è
																			// inferiore o al più uguale al numero di
																			// bottiflie attuali nel negozio dopo che
																			// l'impiegato ha aggiornato i dati

				while (myRs1.next()) {

					integer.add(myRs1.getInt("ID_N"));
				}

				for (Integer v : integer) {
					myStmt.executeUpdate(updateFlagNotification + v.intValue() + ";"); // aggiorno il valore del flag
																						// cosicchè questa notifica
																						// possa essere visualizzata dai
																						// clienti che l'hanno
																						// sottoscritta
				}

				ResultSet myRs2 = myStmt.executeQuery(search);

				while (myRs2.next()) {

					l.add(new Wine(myRs2.getInt("ID_W_E"), myRs2.getString("Name_W_E"), myRs2.getString("Producer_W_E"),
							myRs2.getString("Year_W_E"), myRs2.getString("TechnicalNotes_W_E"),
							myRs2.getString("Vines_W_E"), myRs2.getInt("NumBottles_W_E"), myRs2.getFloat("Price_W_E")));
				}

				if (!l.isEmpty()) {
					System.out.format("thread %s sends: ok to its client%n", id_thread);
					return l;
				}

				else {
					System.out.format("thread %s sends: fail update wine (employee) to its client%n", id_thread);
					return null;
				}
			}

			myStmt.executeUpdate(insertWine); // se il vino non è ancora presente nel negozio allora lo si aggiunge come
												// nuovo vino

			myStmt.executeUpdate(updateFlagUploaded); // se il vino è stato aggiornato allora si segna che questo è
														// stato caricato già nel db dei vini del negozio

			ResultSet myRs3 = myStmt.executeQuery(search);

			while (myRs3.next()) {

				l.add(new Wine(myRs3.getInt("ID_W_E"), myRs3.getString("Name_W_E"), myRs3.getString("Producer_W_E"),
						myRs3.getString("Year_W_E"), myRs3.getString("TechnicalNotes_W_E"),
						myRs3.getString("Vines_W_E"), myRs3.getInt("NumBottles_W_E"), myRs3.getFloat("Price_W_E")));
			}

			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l; // viene ritornata la lista con i rimantenti vini da caricare
			}

			else {
				System.out.format("thread %s sends: fail update wine (employee) to its client%n", id_thread);
				return null;
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail update wine (employee) to its client%n", id_thread);
		return null;
	}
	
	
	/**
	 * This method is used to show to the administrator the list of people contained
	 * into the system database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is an object used to verify the type of request.
	 * @return It returns a list that contains all people inside database.
	 */

	protected synchronized List<People> viewPeople(final String id_thread, final People rq) { 

		String selectAllNotify = "";

		if (rq.getRequest().equals("adminViewCustomer")) {
			
			selectAllNotify = "SELECT ID_C, Name_C, Surname_C, Email_C, Password_C, Address_C "
							+ "FROM customer "
							+ "WHERE Activated_C = 1;"; // only customer with an active account will be show to the administrator
		} 
		
		else if (rq.getRequest().equals("adminViewEmployee")) {
			
			selectAllNotify = "SELECT ID_E, Name_E, Surname_E, Email_E, Password_E "
							+ "FROM employee;";
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for view people (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery(selectAllNotify);

			List<People> l = new LinkedList<>();

			if (rq.getRequest().equals("adminViewCustomer")) {

				while (myRs.next()) {

					l.add(new People(myRs.getInt("ID_C"), myRs.getString("Name_C"), myRs.getString("Surname_C"),
							myRs.getString("Email_C"), myRs.getString("Password_C"), myRs.getString("Address_C")));
				}
			}

			else if (rq.getRequest().equals("adminViewEmployee")) {

				while (myRs.next()) {

					l.add(new People(myRs.getInt("ID_E"), myRs.getString("Name_E"), myRs.getString("Surname_E"),
							myRs.getString("Email_E"), myRs.getString("Password_E"), null));
				}
			}

			if (!l.isEmpty()) {
				System.out.format("thread %s sends: ok to its client%n", id_thread);
				return l;
			}
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		System.out.format("thread %s sends: fail view people to its client%n", id_thread);
		return null;
	}

	
	/**
	 * This is a service method used by adminAddPeople to reactivate an account.
	 * 
	 * @param rq It is the People account to activate.
	 * @return It returns a flag that indicates the outcome of the operation.
	 */

	private synchronized int updateFlagActivated(final People rq) {

		String updateFlag = "UPDATE customer "
				          + "SET Activated_C = 1 "
				          + "WHERE Email_C = '" + rq.getEmail() + "' "
				          + "AND Activated_C = 0;";

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			int value = myStmt.executeUpdate(updateFlag);
			
			return value;
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		return 0;
	}
	
	
	/**
	 * This method is used to insert people into the system database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the people to insert into the database.
	 * @return It returns a flag that indicates the outcome of the operation.
	 */

	protected synchronized int adminAddPeople(final String id_thread, final People rq) {

		String insertPeople = "";

		if (rq.getRequest().equals("adminAddCustomer")) {

			insertPeople = "INSERT INTO customer(Name_C, Surname_C, Email_C, Password_C, Address_C) "
						 + "VALUES " + "('"	+ rq.getName() + "', '" + rq.getSurname() + "', '" + rq.getEmail() 
						             + "', '" + rq.getPassword() + "', '" + rq.getAddress() + "');";
		}

		else if (rq.getRequest().equals("adminAddEmployee")) {

			insertPeople = "INSERT INTO employee(Name_E, Surname_E, Email_E, Password_E) "
					     + "VALUES " + "('" + rq.getName() + "', '" + rq.getSurname() 
					     + "', '" + rq.getEmail() + "', '" + rq.getPassword() + "');";
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for add people (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(insertPeople);
			
			return 1;
		}

		catch (Exception exc) {
			//exc.printStackTrace();
			System.out.println("Duplicate entry");

			System.out.format("thread %s sends: fail add people to its client%n", id_thread);
			return this.updateFlagActivated(rq); // if administrator reactive an account then update flag
		}
	}

	
	/**
	 * 
	 * This is a service method used by adminDeletePeople to deactivate accounts if
	 * people have foreign key constraints.
	 * 
	 * @param rq It is the people.
	 */

	private synchronized int updateFlagDeactived(final People rq) {
		
		// flag usato per gestire l'eliminazione di utenti con vincoli di integrità referenziale

		String updateFlagActived = "UPDATE customer "
				                 + "SET Activated_C = 0 "
				                 + "WHERE Email_C = '" + rq.getEmail() + "' "
		                 		 + "AND Activated_C = 1;"; 

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			int value = myStmt.executeUpdate(updateFlagActived);
			
			return value;
		}

		catch (Exception exc) {
			exc.printStackTrace();
		}

		return 0;
	}
	
	
	/**
	 * This method is used to remove a person from the system database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the People object to delete from the database.
	 * @return It returns the updated list of people contained inside the database.
	 */

	protected synchronized int adminDeletePeople(final String id_thread, final People rq) {

		String deletePeople = "";

		if (rq.getRequest().equals("adminDeleteCustomer")) {

			deletePeople = "DELETE FROM customer "
					     + "WHERE ID_C = " + rq.getId() + ";";
		}

		else if (rq.getRequest().equals("adminDeleteEmployee")) {

			deletePeople = "DELETE FROM employee "
					     + "WHERE ID_E = " + rq.getId() + ";";
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for delete people (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(deletePeople);
			
			return 1;
		}

		catch (Exception exc) {
			//exc.printStackTrace();
			System.out.println("Foreign key constraints");
			System.out.format("thread %s sends: fail add people to its client%n", id_thread);
			
			return this.updateFlagDeactived(rq); // if there are foreign key constraints then administrator disable account
		}
	}

	
	/**
	 * This method is used to update people data stored into database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the people to update.
	 * @return It returns a flag that indicates the outcome of the operation.
	 */

	protected synchronized int adminUpdatePeople(final String id_thread, final People rq) {

		String updatePeople = "";

		if (rq.getRequest().equals("adminUpdateCustomer")) {
			
			// this query allows to validate condition only of fields that has been compiled

			updatePeople = "UPDATE customer SET Name_C = COALESCE(nullif('" + rq.getName() + "', ''), Name_C), "
					     + "Surname_C = COALESCE(nullif('" + rq.getSurname() + "', ''), Surname_C), "
					     + "Email_C = COALESCE(nullif('" + rq.getEmail() + "', ''), Email_C), "
					     + "Password_C = COALESCE(nullif('" + rq.getPassword() + "', ''), Password_C), "
					     + "Address_C = COALESCE(nullif('" + rq.getAddress() + "', ''), Address_C)" + "WHERE ID_C ="
					     + rq.getId() + ";";
		}

		else if (rq.getRequest().equals("adminUpdateEmployee")) {
			
			// this query allows to validate condition only of fields that has been compiled

			updatePeople = "UPDATE employee SET Name_E = COALESCE(nullif('" + rq.getName() + "', ''), Name_E), "
					+ "Surname_E = COALESCE(nullif('" + rq.getSurname() + "', ''), Surname_E), "
					+ "Email_E = COALESCE(nullif('" + rq.getEmail() + "', ''), Email_E), "
					+ "Password_E = COALESCE(nullif('" + rq.getPassword() + "', ''), Password_E) " + "WHERE ID_E ="
					+ rq.getId() + ";";
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for update people (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(updatePeople);

			return 1;
		}

		catch (Exception exc) {
			//exc.printStackTrace();
			System.out.println("Duplicate entry"); // you can't assign the same email to different people
		}

		System.out.format("thread %s sends: fail update people to its client%n", id_thread);
		return 0;
	}
	
		
	/**
	 * This method is used to insert a wine into the system database
	 * by employee or administrator.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the Wine object to insert into the database.
	 * @return It returns a flag that indicates the outcome of the operation.
	 */

	protected synchronized int adminEmployeeAddWine(final String id_thread, final Wine rq) {

		String insertWine = "";

		if (rq.getRequest().equals("adminAddWine")) {

			insertWine = "INSERT INTO wine(Name_W, Producer_W, Year_W, TechnicalNotes_W, Vines_W, NumBottles_W, Price_W) "
					   + "VALUES('" + rq.getName() + "', '" + rq.getProducer() + "', '" + rq.getYear() + "', '"
					   + rq.getTechnicalNotes() + "', '" + rq.getVines() + "'," + rq.getNumBottles() + "," 
					   + rq.getPrice() + ");";
		}

		else if (rq.getRequest().equals("employeeBuyNewWine")) {

			insertWine = "INSERT INTO orderedwineemployee(Name_W_E, Producer_W_E, Year_W_E, TechnicalNotes_W_E, Vines_W_E, NumBottles_W_E, Price_W_E) "
					   + "VALUES('" + rq.getName() + "', '" + rq.getProducer() + "', '" + rq.getYear() + "', '"
					   + rq.getTechnicalNotes() + "', '" + rq.getVines() + "'," + rq.getNumBottles() + "," + rq.getPrice()
					   + ");";
		}

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for add wine (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(insertWine);
			
			return 1;
		}

		catch (Exception exc) {
			//exc.printStackTrace();
			System.out.println("Duplicate entry"); // you can't insert a wine with the same [name, producer, year]
		}

		System.out.format("thread %s sends: fail add wine to its client%n", id_thread);
		return 0;
	}

	
	/**
	 * This method is used to delete a wine from the system database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the Wine object to delete from database.
	 * @return It returns a flag that indicates the outcome of the operation.
	 */

	protected synchronized int adminDeleteWine(final String id_thread, final Wine rq) {

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		String deleteWine = "DELETE FROM wine "
				          + "WHERE ID_W = " + rq.getId();


		System.out.format("thread %s receives: (request for delete wine (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(deleteWine);
			
			return 1;
		}

		catch (Exception exc) {
			//exc.printStackTrace();
			System.out.println("Foreign key constraint"); // you can't delete a wine purchased
		}

		System.out.format("thread %s sends: fail delete wine / view finished wine to its client%n", id_thread);
		return 0;
	}

		
	/**
	 * This method is used to modify wine data stored into system database.
	 * 
	 * @param id_thread It is the thread id.
	 * @param rq It is the wine to update.
	 * @return It returns a flag that indicates the outcome of the operation.
	 */

	protected synchronized int adminUpdateWine(final String id_thread, final Wine rq) {
		
		// this query allows to validate condition only of fields that has been compiled

		String updateWine = "UPDATE wine SET Name_W = COALESCE(nullif('" + rq.getName() + "', ''), Name_W), "
				+ "Producer_W = COALESCE(nullif('" + rq.getProducer() + "', ''), Producer_W), "
				+ "Year_W = COALESCE(nullif('" + rq.getYear() + "', ''), Year_W), "
				+ "TechnicalNotes_W = COALESCE(nullif('" + rq.getTechnicalNotes() + "', ''), TechnicalNotes_W), "
				+ "Vines_W = COALESCE(nullif('" + rq.getVines() + "', ''), Vines_W), "
				+ "NumBottles_W = COALESCE(nullif(" + rq.getNumBottles() + "," + -1 + "), NumBottles_W), "
				+ "Price_W = COALESCE(nullif(" + rq.getPrice() + "," + -1 + "), Price_W)" + " WHERE ID_W =" + rq.getId()
				+ ";";

		System.out.println("\n-----------");
		System.out.println("|   LOG   |");
		System.out.println("-----------");

		System.out.format("thread %s receives: (request for update wine (admin) list) from its client%n", id_thread);

		try {

			Connection myConn = DriverManager.getConnection(DB);

			Statement myStmt = myConn.createStatement();

			myStmt.executeUpdate(updateWine);
			
			return 1;
		}

		catch (Exception exc) {
			//exc.printStackTrace();
			System.out.println("Dupicate entry"); // you can't assign the same [name, producer, year] to different wines
		}

		System.out.format("thread %s sends: fail update wine to its client%n", id_thread);
		return 0;
	}
}

/*
 * Come gestiamo giacenza nulla e notifiche a employee:
 * 
 * 1) Nel metodo buy wine, controlliamo che se dopo l'acquisto dell'utente il
 * numero di bottiglie diventa nullo settiamo un flag Visualized_W = 0. Lo
 * settiamo a zero perchè questo flag viene usato dal metodo che notifica
 * all'impiegato la giacenza nulla se il numero di bottiglie è uguale a zero ed
 * inoltre il flag visualizzato è uguale a 0.
 * 
 * 2) L'impiegato quando compra il vino finito fa si che il flag visualizzato
 * venga settato a 1 cosicchè non gli compaia sempre la stessa notifica.
 * 
 * 
 * Come gestiamo notifiche sottoscritte dall'utente:
 * 
 * 
 * Quando le bottiglie sono di nuovo disponibili (quando vengono ricaricate
 * dall'impiegato nel metodo update), il flag della notifica sottoscritta
 * dall'utente viene settato a uno (inizialmente vale zero). Il cliente ogni
 * volta che fa l'accesso interroga il db per vedere se ci sono notifiche che ha
 * sottoscritto uguali a uno.
 */
