package assignement3.communication;

/**
 * 
 * The class {@code User} is used to represents customer entity. 
 * This class inherit from People class.
 *
 */

public class Customer extends People {
	
	private static final long serialVersionUID = 1L;
	

										// REGISTRATION -> REQUEST //
	////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Class constructor used by unregistered user to make request for registration to the server.
	 * 
	 * @param id It is the customer id.
	 * @param name It is the customer name.
	 * @param surname It is the customer surname.
	 * @param email It is the customer email.
	 * @param password It is the customer password.
	 * @param address It is the customer address.
	 */

	public Customer(final int id, final String name, final String surname, final String email, final String password, final String address) { 
		super(id, name, surname, email, password, address);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
										// VIEW CUSTOMERS -> REQUEST //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/** {@inheritDoc} **/
	
	public Customer() {}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
