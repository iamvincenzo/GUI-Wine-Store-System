package assignement3.communication;

import java.io.Serializable;

/**
 *
 * The class {@code Wine} is used to represents wine entity.
 *
 */

public class Wine implements Serializable {
	
	/**
	 * Class fields.
	 * 
	 * id - It is the wine's id.
	 * name - It is the wine's name.
	 * producer - It is wine's producer.
	 * year - It is wine's year.
	 * technicalNotes - They are wine's technicalNotes.
	 * vines - It is a list that contains wine's vines.
	 * numBottles - It is the number of bottles.
	 * price - It is the wine price.
	 * 
	 * request - It is the type of request.
	 */
	
	private int id;
	private String name;
	private String producer;
	private String year;
	private String technicalNotes;
	private String vines;
	private int numBottles;
	private float price;
	
	private String request;
	
	private static final long serialVersionUID = 1L;
	
		
											// SEARCH WINE -> REQUEST //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	* Class constructor used by unregistered user to make request for search wine to the server.
	* 
	* Class constructor used by customer to make request for search wine to the server.
	* 
	* @param n It is the wine name.
	* @param p It is the wine producer.
	* @param y It is the wine year.
	*/
	
	public Wine(final String n, final String p, final String y) {
		this.setName(n);
		this.setProducer(p);
		this.setYear(y);
	}
	
	
											// UPDATE WINE -> REQUEST //
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	/**
	 * 
	 * Class constructor used by employee to make a request to buy finished wine to the server.
	 * 
	 * Class constructor used by employee to make a request to buy new wine to the server.
	 * 
	 * Class constructor used by employee to make a request to buy an existing wine to the server.
	 * 
	 * Class constructor used by employee to make a request to reload bottles of a selected wine to the server.
	 * 
	 * Class constructor used by administrator to make a request to add wine to the server.
	 * 
	 * Class constructor used by administrator to make a request to delete wine to the server.
	 * 
	 * Class constructor used by administrator to make a request to modify wine to the server.
	 * 
	 * @param id It is the wine id.
	 * @param name It is the wines's name.
	 * @param producer It is the wines's producer.
	 * @param year It is the wines's year.
	 * @param technicalNotes They are the wines's technical notes.
	 * @param vines It is a list that contains wines's vines.
	 * @param numBottles It is the number of bottles available.
	 * @param price It is the wine price.
	 */
	
	public Wine(final int id, final String name, final String producer, final String year, final String technicalNotes, final String vines, final int numBottles, final float price) {
		this.setId(id);
		this.setName(name);
		this.setProducer(producer);
		this.setYear(year);
		this.setTechnicalNotes(technicalNotes);
		this.setVines(vines);
		this.setNumBottles(numBottles);
		this.setPrice(price);
	}
	
	
	/**
	 *
	 * Class constructor used by unregistered user to make a request to view wines to the server.
	 * 
	 * Class constructor used by customer to make a view wines request to the server.
	 * 
	 * Class constructor used by employee to make a view finished wines request to the server.
	 * 
	 * Class constructor used by employee to view wines (stored into database) request to the server.
	 * 
	 * Class constructor used by employee to view wines he bought request to the server.
	 *	
	 * Class constructor used by administrator to make a request to view wines to the server.
	 * 	
	 */
	
	public Wine() {}


	/**
	 * This method is used to get the wine id.
	 * 
	 * @return It returns the wine id.
	 */

	public int getId() { return id;	}
	
	
	/**
	 * This method is used to set the wine id.
	 * 
	 * @param id It is the wine id.
	 */

	public void setId(int id) { this.id = id; }
	
	
	/**
	 * This method is used to get the wines's name.
	 * 
	 * @return It returns the wines's name.
	 */

	public String getName() { return name; }
	
	
	/**
	 * This method is used to get the wines's producer.
	 * 
	 * @return It returns the wines's producer.
	 */

	public String getProducer() { return producer; }
	
	
	/**
	 * This method is used to get the wines's year.
	 * 
	 * @return It returns the wines's year.
	 */

	public String getYear() { return year; }
	
	
	/**
	 * This method is used to get the wines's technical notes.
	 * 
	 * @return It returns the wines's technical notes.
	 */

	public String getTechnicalNotes() { return technicalNotes; }
	
	
	/**
	 * This method is used to get the list that contains wines's vines.
	 * 
	 * @return It returns the list that contains wines's vines.
	 */

	public String getVines() { return vines; }
	
	
	/**
	 * This method is used to get the number of wines's bottles.
	 * 
	 * @return It returns the number of wines's bottles.
	 */

	public int getNumBottles() { return numBottles;	}
	
	
	/**
	 * This method is used to get the price of the wine.
	 * 
	 * @return It returns the price of the wine.
	 */
	
	public float getPrice() { return price; }
	
	
	/**
	 * This method is used to set the wines's name.
	 * 
	 * @param name is the wines's name.
	 */

	public void setName(String name) { this.name = name; }
	
	
	/**
	 * This method is used to set the wines's producer.
	 * 
	 * @param producer is the wines's producer.
	 */

	public void setProducer(String producer) { this.producer = producer; }
	
	
	/**
	 * This method is used to set the wines's year.
	 * 
	 * @param year is the wines's year.
	 */

	public void setYear(String year) { this.year = year; }
	
	
	/**
	 * This method is used to set the wines's technical notes.
	 * 
	 * @param technicalNotes is the wines's technical notes.
	 */

	public void setTechnicalNotes(String technicalNotes) { this.technicalNotes = technicalNotes; }
	
	
	/**
	 * This method is used to set the wines's vines list.
	 * 
	 * @param vines is the wines's vines list.
	 */

	public void setVines(String vines) { this.vines = vines; }
	
	
	/**
	 * This method is used to set the number of wines's bottles.
	 * 
	 * @param numBottles is the number of wines's bottles.
	 */

	public void setNumBottles(int numBottles) { this.numBottles = numBottles; }
	
	
	/**
	 * This method is used to set the price of wine.
	 * 
	 * @param price It is the price to set.
	 */
	
	public void setPrice(float price) { this.price = price; }

	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * This method is used to get the type of request.
	 * 
	 * @return It returns the type of request.
	 */
	
	public String getRequest() { return request; }

	
	/**
	 * This method is used to set the type of client request.
	 * 
	 * @param request It is the type of request to set.
	 */

	public void setRequest(String request) { this.request = request; }
}
