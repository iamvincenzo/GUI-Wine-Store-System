package assignement3.communication;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * The class {@code Order} is used to represents order entity.
 * 
 */

public class Order implements Serializable {
	
	/**
	 * Class fields.
	 * 
	 * idO - It is the id of the order. (used for delivery orders by employee)
	 * codCustomer - It is the code of customer associated with order. (used for orders by customers)
	 * codWine - It is is the code of wine associated with order. (used for orders by customers)
	 * numBottles - It is the number of bottles of the ordered wine. (used for orders by customers, employee)
	 * date - It is the date of the order. (used for orders by customers)
	 * tot - It is the total money spent by the customer. (used for orders by customers)
	 * 
	 * nameW - It is the wine name. (used for delivery orders by employee)
	 * producerW - It is the wine producer. (used for delivery orders by employee)
	 * yearW - It is the wine year. (used for delivery orders by employee)
	 * nameC - It is the customer name. (used for delivery orders by employee)
	 * addressC - It is the customer address. (used for delivery orders by employee)
	 * l - It is a list used to show the orders of the clients to the employee. (used for delivery orders by employee)
	 * 
	 * idC - It is the customer id. (used by the customer to view the order history)
	 * completed - It is a flag that indicates if a order is completed. (used by the customer to view the order history)
	 * 
	 * addressC - It is the customer address.
	 * 
	 * request - It is a string that specifies the type of request that client makes to the server.
	 * 
	 * serialVersionUID - It is the versionUID
	 */
	
	private int idO;
	private int codCustomer;
	private int codWine;
	private int numBottles;
	private Timestamp date;
	private float tot;
	
	private String emailC; 
	private String nameW;
	private String producerW;
	private String yearW;
	private String nameC;
	private String addressC;
	
	private int idC;
	private byte completed;
	
	private String request;
	
	private String stringCompleted;

	private static final long serialVersionUID = 1L;
	
										// BUY -> REQUEST //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by customer to make a request for buy wine to the server.
	 * 
	 * @param codC It is the code of customer associated with order.
	 * @param codW It is is the code of wine associated with order.
	 * @param nB It is the number of bottles of the ordered wine.
	 * @param d It is the order timestamp.
	 * @param tot It is the total amount of money that customer have to pay.
	 */
	
	public Order(final int codC, final int codW, final int nB, final Timestamp d, final float tot) {
		this.setCodCustomer(codC);
		this.setCodWine(codW);
		this.setNumBottles(nB);
		this.setDate(d);
		this.setTot(tot);
	}
	
	
	/**
	 * This method is used to get the code of customer associated with the order.
	 *  
	 * @return It returns the code of the customer.
	 */
	
	public int getCodCustomer() { return codCustomer; }
	
	
	/**
	 * This method is used to set the code of customer associated with the order.
	 * 
	 * @param codCustomer It is the code of customer associated with order.
	 */
	
	public void setCodCustomer(int codCustomer) { this.codCustomer = codCustomer; }
	
	
	/**
	 * This method is used to get the code of wine associated with the order.
	 * 
	 * @return It returns the code of the wine.
	 */
	
	public int getCodWine() { return codWine; }
	
	
	/**
	 * This method is used to set the code of wine associated with the order.
	 * 
	 * @param codWine It is is the code of wine associated with order.
	 */
	
	public void setCodWine(int codWine) { this.codWine = codWine; }
	
	
	/**
	 * This method is used to get the number of bottles associated with the order.
	 * 
	 * @return It returns the number of bottles.
	 */
	
	public int getNumBottles() { return numBottles;	}
	
	
	/**
	 * This method is used to set the number of bottles associated with the order.
	 *  
	 * @param numBottles It is the number of bottles of the ordered wine.
	 */
	
	public void setNumBottles(int numBottles) {	this.numBottles = numBottles; }
	
	
	/**
	 * This method is used to get the date associated with the order.
	 * 
	 * @return It returns the date associated with the order.
	 */
	
	public Timestamp getDate() { return date; }
	
	
	/**
	 * This method is used set the date associated with the order.
	 * 
	 * @param date It is the date of the order
	 */
	
	public void setDate(Timestamp date) { this.date = date; }
	
	
	/**
	 * This method is used to get the amount of money that customer have to pay.
	 * 
	 * @return It returns the amount of money that customer have to pay.
	 */

	public float getTot() {	return tot;	}
	
	
	/**
	 * This method is used to set the amount of money that customer have to pay.
	 * 
	 * @param tot It is the amount of money to set.
	 */

	public void setTot(float tot) { this.tot = tot;	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
										// VIEW ORDERS | SHIP ORDERS -> REQUEST //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by employee to make a view orders to ship request to the server.
	 * 
	 * Class constructor used by employee to make a ship all orders request to the server.
	 * 
	 * Class constructor used by administrator to make a orders view request.
	 */
	 
	public Order() {}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
										// ORDERS TO SHIP -> RESPONSE //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by employee to make a ship selected order request to the server.
	 * 
	 * @param idO It is the order id.
	 * @param nameC It is the customer name.
	 * @param addressC It is the customer address.
	 * @param nameW It is the wine name.
	 * @param producerW It is the wine producer.
	 * @param yearW It is the wine year.
	 * @param nB It is the number of bottles of wine.
	 */
	
	public Order(final int idO, final String nameC, final String addressC, final String nameW, final String producerW, final String yearW, final int nB) {
		this.setIdO(idO);
		this.setNameC(nameC);
		this.setAddressC(addressC);
		this.setNameW(nameW);
		this.setProducerW(producerW);
		this.setYearW(yearW);
		this.setNumBottles(nB);
	}
	
	
	/**
	 * This method is used to get the id of the order.
	 * 
	 * @return It returns the id of the order.
	 */
	
	public int getIdO() { return idO;	}
	
	
	/**
	 * This method is used to set the id of the order.
	 * 
	 * @param idO It is the id of the order.
	 */

	public void setIdO(int idO) {	this.idO = idO; }
	
	
	/**
	 * This method is used to get the name of the customer.
	 * 
	 * @return It returns the name of the customer.
	 */
	
	public String getNameC() { return nameC; }
	
	
	/**
	 * This method is used to set the name of the customer.
	 * 
	 * @param nameC It is the name of the customer.
	 */
	
	public void setNameC(String nameC) { this.nameC = nameC; }
	
	
	/**
	 * This method is used to get the address of the customer.
	 * 
	 * @return It returns the address of the customer.
	 */
	
	public String getAddressC() { return addressC; }
	
	
	/**
	 * This method is used to set the address of the customer.
	 * 
	 * @param addressC It is the address of the customer.
	 */
	
	public void setAddressC(String addressC) { this.addressC = addressC; }
	
	
	/**
	 * This method is used to get the name of the wine.
	 * 
	 * @return It returns the name of the wine.
	 */

	public String getNameW() { return nameW; }
	
	
	/**
	 * This method is used to set the name of the wine.
	 * 
	 * @param nameW It is the name of the wine.
	 */

	public void setNameW(String nameW) { this.nameW = nameW; }
	
	
	/**
	 * This method is used to get the producer of the wine.
	 * 
	 * @return It returns the producer of the wine.
	 */

	public String getProducerW() { return producerW; }
	
	
	/**
	 * This method is used to set the producer of the wine.
	 * 
	 * @param producerW It is the producer of the wine.
	 */

	public void setProducerW(String producerW) { this.producerW = producerW; }
	
	
	/**
	 * This method is used to get the year of the wine.
	 * 
	 * @return It returns the year of the wine.
	 */
	
	public String getYearW() { return yearW; }
	
	
	/**
	 * This method is used to set the year of the wine.
	 *  
	 * @param yearW It is the year of the wine.
	 */

	public void setYearW(String yearW) { this.yearW = yearW; }
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////


									 	// HISTORY | NOTIFICATION -> REQUEST //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 *
	 * Class constructor used by customer to make a request to view the order history request to the server.
	 * 
	 * Class constructor used by customer to make a request to view all the notifications of availability of wines request to the server.
	 * 
	 * availability that the system sends to the customer.
	 * 
	 * @param idC It is the customer id.
	 */
	
	public Order(final int idC) { this.setIdC(idC); }
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
									
										// HISTORY -> RESPONSE //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by monitorDB to make a response for history of orders to server thread.
	 * 
	 * @param idO It is the order id.
	 * @param emailC It is the customer email.
	 * @param nameW It is the wine name.
	 * @param producerW It is the wine producer.
	 * @param yearW It is the wine year.
	 * @param numBottlesO It is the number of bottles.
	 * @param completed It is a flag that indicates if a order is completed.
	 * @param dateO It is the date of the order.
	 * @param tot It is the amount of money that customer paid.
	 */
	
	public Order(final int idO, final String emailC, final String nameW, final String producerW, final String yearW, final int numBottlesO, final byte completed, final Timestamp dateO, final float tot) {
		this.setIdO(idO);
		this.setEmailC(emailC);
		this.setNameW(nameW);
		this.setProducerW(producerW);
		this.setYearW(yearW);
		this.setNumBottles(numBottlesO);
		this.setCompleted(completed);
		this.setDate(dateO);
		this.setTot(tot);
	}
	
	
	/**
	 * This method is used to get the customer id.
	 * 
	 * @return It returns the customer id.
	 */
	
	public int getIdC() { return this.idC; }
	
	
	/**
	 * This method is used to set the customer id.
	 * 
	 * @param idC It it the customer id to set.
	 */
	
	public void setIdC(int idC) { this.idC = idC; }
	
	
	/**
	 * This method is used to get the customer email.
	 * 
	 * @return It returns the customer email.
	 */
	
	public String getEmailC() { return emailC; }

	
	/**
	 * This method is used to set the customer email.
	 * 
	 * @param emailC It is the email to set.
	 */
	
	public void setEmailC(String emailC) { this.emailC = emailC; }
	
	
	/**
	 * This method is used to get the order completed flag.
	 * 
	 * @return It returns the value of the flag.
	 */
	
	public byte getCompleted() { return this.completed; }
	
	
	/**
	 * This method is used to set the order completed flag.
	 * 
	 * @param completed It is the value of the flag to set.
	 */
	
	public void setCompleted(byte completed) { this.completed = completed; }
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
										// NOTIFY -> REQUEST - RESPONSE //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by customer to make a delete notification request to the server.
	 * 
	 * @param idO It is the order id.
	 * @param nameW It is the wine name.
	 * @param producerW It is the wine producer.
	 * @param yearW It is the wine year.
	 * @param numBottles It is the wine number of bottles.
	 * @param date It is the date of wine availability.
	 */
	
	public Order(final int idO, final String nameW, final String producerW, final String yearW, final int numBottles, final Timestamp date){
		this.setIdO(idO);
		this.setNameW(nameW);
		this.setProducerW(producerW);
		this.setYearW(yearW);
		this.setNumBottles(numBottles);
		this.setDate(date);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
										// NOTIFICATION -> REQUEST //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by customer to make a request for subscribe notification to the server.
	 * 
	 * @param codC It is the customer id.
	 * @param codW It is the wine id.
	 * @param nB It is the number of bottles desired by customer.
	 */
	
	public Order(final int codC, final int codW, final int nB) {
		this.setCodCustomer(codC);
		this.setCodWine(codW);
		this.setNumBottles(nB);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
					
							// Methods used to get - set the type of request //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * This method is used to get the type of request of the instance of the class Order.
	 * 
	 * @return It returns the type of request.
	 */

	public String getRequest() { return request; }
	
	
	/**
	 * This method is used to set the type of request of the instance of the class Order.
	 * 
	 * @param request It is the type of request to set.
	 */

	public void setRequest(String request) { this.request = request; }


	/**
	 * This method is used to get the state of the order.
	 * 
	 * @return It returns the state of the order.
	 */
	
	public String getStringCompleted() { return stringCompleted; }

	
	/**
	 * This method is used to set the order state.
	 * 
	 * @param stringCompleted It is the state of order to set.
	 */

	public void setStringCompleted(String stringCompleted) { this.stringCompleted = stringCompleted; }
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
