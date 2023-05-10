package assignement3.communication;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * The class {@code Response} represents the server response entity.
 *
 */

public class Response implements Serializable {
	
	/**
	 * Class fields.
	 * 
	 * listPeople - It is a list that contains employees or customers. It is used by server such a response for client.
	 * listOrder - It is a list that contains orders. It is used by server such a response for client.
	 * listSearch - It is a list that contains wines. It is used by server such a response for client.
	 * 
	 * resp - It is a value that represents the server response.
	 * 
	 * response - It is a string that represents the type of response that server thread sends to the client.
	 * 
	 * responseMDB - It is the MonitorDB response.
	 */

	private static final long serialVersionUID = 1L;
	
	private List<People> listPeople;
	private List<Order> listOrder;
	private List<Wine> listSearch;
	private String responseMDB;
	
	private int resp;
	
	private String response;
	
								// LOGIN | SEARCH WINE | SUB. NOTIFICTION -> RESPONSE //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by server thread to make a response for login request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for registration request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for search wine request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for close socket request to people.
	 * 
	 * Class constructor used by server thread to make a response for subscribe notification request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for buy wine request to the customer.
	 * 
	 * Class constructor used by server thread to make a response view history order request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for modify data request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for buy new wine request to the employee.
	 * 
	 * Class constructor used by server thread to make a response view customer history order to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for add people request to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for delete people request to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for modify people data request to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for add wine request to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for delete wine data request to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for modify wine data request to the administrator.
	 * 
	 * @param v It represents the value of response.
	 */
	
	public Response(final int v) { this.setResp(v); }
	
	
	/**
	 * This method is used to get the value of response.
	 * 
	 * @return It returns the value of response.
	 */

	public int getResp() { return resp;	}
	
	
	/**
	 * This method is used to set the value of response.
	 * 
	 * @param resp It is the value of response.
	 */

	public void setResp(int resp) {	this.resp = resp; }
	

								// DELIVERY | HISTORY | NOTIFICATION -> RESPONSE //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		
	/**
	 * Class constructor used by server thread to make a response view history order request to the customer.
	 * 
	 * Class constructor used by server thread to make a response for view notifications request that the system sends to the customer.
	 * 
	 * Class constructor used by server thread to make a response for orders to ship request to the employee.
	 * 
	 * Class constructor used by server thread to make a response for view finished wine request to the employee.
	 * 
	 * Class constructor used by server thread to make a response view customer history order to the administrator.
	 * 
	 * @param l It is the list that contains orders.
	 */
	
	public Response(final List<Order> l) { this.setRespListO(l); }
	
	
	/**
	 * This method is used to get the orders list.
	 * 
	 * @return It returns the order list.
	 */
	
	public List<Order> getRespListO() { return this.listOrder; }
	
	
	/**
	 * This method is used to set the orders list.
	 * 
	 * @param l It is the orders list to set.
	 */
	
	public void setRespListO(List<Order> l) { this.listOrder = l; }
	
									
										// SEARCH WINE -> RESPONSE //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by server thread to make a response for search wines request to unregistered users.
	 * 
	 * Class constructor used by server thread to make a response for search wines request to customers.
	 * 
	 * Class constructor used by server thread to make a response for buy finished wines request to the employee.
	 * 
	 * Class constructor used by server thread to make a response for view wines stored into system database request to the employee.
	 * 
	 * Class constructor used by server thread to make a response for reload bottles request to the employee.
	 * 
	 * Class constructor used by server thread to make a response for view wines stored into system database request to the administrator.
	 * 
	 * @param l It is a list that contains the result of search wines.
	 * @param c It is used to differentiate constructors.
	 */
	
	public Response(final List<Wine> l, final byte c) { this.setRespListS(l); }
	
	
	/**
	 * This method is used to get the wine list.
	 * 
	 * @return It returns the wine list.
	 */
	
	public List<Wine> getRespListS() { return this.listSearch; }
	
	
	/**
	 * This method is used to set the wine list.
	 * 
	 * @param l It is the wine list to set.
	 */
	
	public void setRespListS(List<Wine> l) { this.listSearch = l; }
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by server thread to make a response for view info request to the customer.
	 *  
	 * Class constructor used by server thread to make a response for view customer info request to the administrator.
	 * 
	 * Class constructor used by server thread to make a response for view employee info request to the administrator.
	 * 
	 * @param l It is a list that contains the result of search people.
	 * @param c It is used to differentiate constructors.
	 * @param d It is used to differentiate constructors.
	 */
	
	public Response(List<People> l, final byte c, final byte d) { this.setRespListP(l); }
	
	
	/**
	 * This method is used to get the people list.
	 * 
	 * @return It returns the people list.
	 */
	
	public List<People> getRespListP() { return listPeople;	}
	
	
	/**
	 * This method is used to set the people list.
	 * 
	 * @param listPeople It is the people list to set.
	 */

	public void setRespListP(List<People> listPeople) {	this.listPeople = listPeople; }
	
	
											// SHIP -> RESPONSE //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Class constructor used by server thread to make a response for ship selected order request to the employee.
	 * 	
	 * @param s It is the string that contains all data orders.
	 */
	
	public Response(String s) {	this.setResponseMDB(s); }
	
	
	/**
	 * This method is used to get the value of responseMDB.
	 * 
	 * @return It returns the value of responseMDB.
	 */
	
	public String getResponseMDB() { return responseMDB; }
	
	
	/**
	 * This method is used to set the value of responseMDB.
	 * 
	 * @param responseMDB It is the value to set.
	 */

	public void setResponseMDB(String responseMDB) { this.responseMDB = responseMDB; }
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * This method is used to get the type of response.
	 * 
	 * @return It returns the type of response.
	 */

	public String getResponse() { return response; }
	
	
	/**
	 * This method is used to set the type of response.
	 * 
	 * @param response It is the type of response to set.
	 */

	public void setResponse(String response) { this.response = response; }
}
